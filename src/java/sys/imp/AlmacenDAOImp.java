
package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.AlmacenDAO;
import sys.model.Almacen;
import sys.util.HibernateUtil;

public class AlmacenDAOImp implements AlmacenDAO{

    @Override
    public List<Almacen> listarAlmacen() {
        List<Almacen> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Almacen ORDER BY sucursal.nombreSucursal ASC";
        try
        {
            Query query = session.createQuery(hql);
            lista = query.list();
            for (Almacen l : lista) {
                Hibernate.initialize(l.getSucursal());
            }
            t.commit();
            session.close();
        }
        catch(Exception e)
        {
            t.rollback();
        }
        return lista;
    }

    @Override
    public boolean nuevoAlmacen(Session session, Almacen almacen) {
        session.save(almacen);
        return true;
    }

    @Override
    public boolean actualizarAlmacen(Session session, Almacen almacen) {
        session.update(almacen);
        return true;
    }

    @Override
    public void eliminarAlmacen(Almacen almacen) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(almacen);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        }
        finally
        {
            if(session != null)
                session.close();
        }
    
    }

    @Override
    public Almacen buscarAlmacenPrincipal(Session session, Integer idSucursal) {
        String hql = "FROM Almacen WHERE sucursal.idSucursal = :idSucursal AND Principal = 1";
        Query q = session.createQuery(hql);
        q.setParameter("idSucursal", idSucursal);
            
        return (Almacen) q.uniqueResult();
    }

    @Override
    public List<Almacen> listarAlmacenPorSucursal(Session session, Integer idSucursal) {
        String hql = "FROM Almacen WHERE idSucursal = :idSucursal ORDER BY nombreAlmacen ASC";
        Query q = session.createQuery(hql);
        q.setParameter("idSucursal", idSucursal);
        return q.list();
    }

    @Override
    public Almacen buscarAlmacen(Session session, Integer idAlmacen) {
        String hql = "FROM Almacen WHERE idAlmacen = :idAlmacen";
        Query q = session.createQuery(hql);
        q.setParameter("idAlmacen", idAlmacen);
            
        return (Almacen) q.uniqueResult();
    }
    
}

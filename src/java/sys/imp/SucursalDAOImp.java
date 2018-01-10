
package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.SucursalDAO;
import sys.model.Sucursal;
import sys.util.HibernateUtil;

public class SucursalDAOImp implements SucursalDAO{

    @Override
    public List<Sucursal> listarSucursal() {
        List<Sucursal> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Sucursal ORDER BY nombreSucursal";
        try
        {
            lista = session.createQuery(hql).list();
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
    public void nuevaSucursal(Sucursal sucursal) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(sucursal);
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
    public void actualizarSucursal(Sucursal sucursal) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(sucursal);
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
    public void eliminarSucursal(Sucursal sucursal) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(sucursal);
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
    public Sucursal buscarSucursal(Session session, Integer idSucursal) throws Exception {
        String hql = "FROM Sucursal WHERE idSucursal = :idSucursal";
        Query q = session.createQuery(hql);
        q.setParameter("idSucursal", idSucursal);
        
        return (Sucursal) q.uniqueResult();
    }

    @Override
    public Sucursal buscarSucursal(Integer idSucursal) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Sucursal WHERE idSucursal = :idSucursal";
        Query q = session.createQuery(hql);
        q.setParameter("idSucursal", idSucursal);
        return (Sucursal) q.uniqueResult();
    }
    
}

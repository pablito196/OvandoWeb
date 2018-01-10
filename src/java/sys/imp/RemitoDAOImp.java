package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.RemitoDAO;
import sys.model.Remito;
import sys.util.HibernateUtil;

public class RemitoDAOImp implements RemitoDAO{

    @Override
    public List<Remito> listarRemito() {
        List<Remito> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Remito WHERE venta.estado = 'VENDIDO' ORDER BY fechaRemito desc, numeroRemito desc";
        try
        {
            lista = session.createQuery(hql).list();
            for (Remito l : lista) {
                Hibernate.initialize(l.getVenta().getCliente());
            }
            for (Remito l : lista) {
                Hibernate.initialize(l.getVenta().getUsuario().getEmpleado());
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
    public void actualizarRemito(Remito remito) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(remito);
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
    public boolean guardarRemito(Session session, Remito remito) {
        session.save(remito);
        return true;
    }
    
}

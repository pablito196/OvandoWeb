package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.DosificacionDAO;
import sys.model.Dosificacion;
import sys.util.HibernateUtil;

public class DosificacionDAOImp implements DosificacionDAO
{

    @Override
    public List<Dosificacion> listarDosificaciones() {
        List<Dosificacion> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Dosificacion ORDER BY fechaLimiteEmision DESC";
        try
        {
            lista = session.createQuery(hql).list();
            for (Dosificacion l : lista) {
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
    public boolean nuevaDosificacion(Session session, Dosificacion dosificacion) 
    {
        session.save(dosificacion);
        return true;
    }

    @Override
    public boolean actualizarDosificacion(Session session, Dosificacion dosificacion) 
    {
        session.update(dosificacion);
        return true;
    }

    @Override
    public void eliminarDosificacion(Dosificacion dosificacion) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(dosificacion);
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
    public Dosificacion buscarDosificacionActiva(Session session, Integer idSucursal) throws Exception 
    {
        String hql = "FROM Dosificacion WHERE estado = 1 AND sucursal.idSucursal = :idSucursal";
        Query q = session.createQuery(hql);
        q.setParameter("idSucursal", idSucursal);
        return (Dosificacion) q.uniqueResult();
    }

    @Override
    public Dosificacion buscarDosificacion(Session session, String numeroAutorizacion) throws Exception {
        String hql = "FROM Dosificacion WHERE numeroAutorizacion = :numeroAutorizacion ";
        Query q = session.createQuery(hql);
        q.setParameter("numeroAutorizacion", numeroAutorizacion);
        return  (Dosificacion) q.uniqueResult();
    }
    
}

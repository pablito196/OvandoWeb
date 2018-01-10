
package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.ContactoDAO;
import sys.model.Contacto;
import sys.util.HibernateUtil;

public class ContactoDAOImp implements ContactoDAO{

    @Override
    public List<Contacto> listarContactos() {
        List<Contacto> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Contacto";
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
    public void actualizarCategoria(Contacto contacto) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(contacto);
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
    public void eliminarCategoria(Contacto contacto) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(contacto);
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
    public List<Contacto> listarContactosPorProveedor(Session session, Integer idProveedor) {
        String hql = "FROM Contacto WHERE idProveedor = :idProveedor ORDER BY nombreCompleto ASC";
        Query q = session.createQuery(hql);
        q.setParameter("idProveedor", idProveedor);
        return q.list();
    }

    @Override
    public boolean guardarContacto(Session session, Contacto contacto) {
        session.save(contacto);
        return true;
    }
    
}

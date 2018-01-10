
 
package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.ClienteDAO;
import sys.model.Cliente;
import sys.util.HibernateUtil;


public class ClienteDAOImp implements ClienteDAO
{

    @Override
    public List<Cliente> listarClientes() 
    {
         List<Cliente> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Cliente ORDER BY nombresCliente ASC";
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
    public void nuevoCliente(Cliente cliente) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(cliente);
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
    public void actualizarCliente(Cliente cliente) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(cliente);
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
    public void eliminarCliente(Cliente cliente) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(cliente);
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
    public Cliente obtenerClientePorCi(Session session, String ciCliente) throws Exception 
    {
        String hql = "FROM Cliente WHERE ciCliente = :ciCliente";
        Query q = session.createQuery(hql);
        q.setParameter("ciCliente", ciCliente);
        return (Cliente) q.uniqueResult();
    }
    
}

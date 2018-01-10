package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.CategoriaDAO;
import sys.model.Categoriaarticulo;
import sys.util.HibernateUtil;

public class CategoriaDAOImp implements CategoriaDAO
{

    @Override
    public List<Categoriaarticulo> listarCategorias() 
    {
        List<Categoriaarticulo> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Categoriaarticulo ORDER BY nombreCategoria ASC";
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
    public void nuevaCategoria(Categoriaarticulo categoria) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(categoria);
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
    public void actualizarCategoria(Categoriaarticulo categoria) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(categoria);
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
    public void eliminarCategoria(Categoriaarticulo categoria) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(categoria);
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
    public Categoriaarticulo buscarCategoria(Session session, Integer idCategoria) throws Exception {
        String hql = "FROM Categoriaarticulo WHERE idCategoria = :idCategoria";
        Query q = session.createQuery(hql);
        q.setParameter("idCategoria", idCategoria);
        
        return (Categoriaarticulo) q.uniqueResult();
    }
    
}

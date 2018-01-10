package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.SubcategoriaDAO;
import sys.model.Subcategoriaarticulo;
import sys.util.HibernateUtil;

public class SubcategoriaDAOImp implements SubcategoriaDAO
{

    @Override
    public List<Subcategoriaarticulo> listarSubcategorias() 
    {
        List<Subcategoriaarticulo> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        //String hql = "FROM Subcategoriaarticulo";
        String hql = "FROM Subcategoriaarticulo ORDER BY categoriaarticulo.nombreCategoria ASC";
        try
        {
            Query query = session.createQuery(hql);
            lista = query.list();
            for (Subcategoriaarticulo l : lista) {
                Hibernate.initialize(l.getCategoriaarticulo());
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
    public boolean nuevaSubcategoria(Session session, Subcategoriaarticulo subcategoria) 
    {
        session.save(subcategoria);
        return true;
    }

    @Override
    public void actualizarSubcategoria(Subcategoriaarticulo subcategoria) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(subcategoria);
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
    public void eliminarSubcategoria(Subcategoriaarticulo subcategoria) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(subcategoria);
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
    public List<Subcategoriaarticulo> listarSubcategorias(Subcategoriaarticulo subcategoria) 
    {
        List<Subcategoriaarticulo> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        //String hql = "FROM Subcategoriaarticulo subcat WHERE ";
        try
        {
            Query query = session.createQuery("FROM Subcategoriaarticulo subcat WHERE idCategoria=:iIdCategoria").setParameter("IdCategoria", subcategoria.getIdSubcategoria()); // la consulta debe estar con idcategoria 
            lista = query.list();
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
    public List<Subcategoriaarticulo> listarSubcategoriaPorPais(Session session, Integer idCategoria) {
        String hql = "FROM Subcategoriaarticulo WHERE idCategoria = :idCategoria ORDER BY nombreSubCategoria ASC";
        Query q = session.createQuery(hql);
        q.setParameter("idCategoria", idCategoria);
        return q.list();
    }

    @Override
    public Subcategoriaarticulo buscarSubcategoria(Session session, Integer idSubcategoria) throws Exception {
        String hql = "FROM Subcategoriaarticulo WHERE idSubcategoria = :idSubcategoria";
        Query q = session.createQuery(hql);
        q.setParameter("idSubcategoria", idSubcategoria);
        
        return (Subcategoriaarticulo) q.uniqueResult();
    }
    
}

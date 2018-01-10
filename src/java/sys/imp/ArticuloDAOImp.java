package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.ArticuloDAO;
import sys.model.Articulo;
import sys.util.HibernateUtil;

public class ArticuloDAOImp implements ArticuloDAO
{

    @Override
    public List<Articulo> listarArticulos() 
    {
        List<Articulo> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Articulo ORDER BY subcategoriaarticulo.categoriaarticulo.nombreCategoria ASC";
        try
        {
            lista = session.createQuery(hql).list();
            for (Articulo l : lista) {
                Hibernate.initialize(l.getSubcategoriaarticulo());
            }
            for (Articulo l : lista) {
                Hibernate.initialize(l.getUnidad());
            }
            for (Articulo l : lista) {
                Hibernate.initialize(l.getSubcategoriaarticulo().getCategoriaarticulo());
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
    public boolean nuevoArticulo(Session session, Articulo articulo) 
    {
        session.save(articulo);
        return true;
    }

    @Override
    public boolean actualizarArticulo(Session session, Articulo articulo) 
    {
        session.update(articulo);
        return true;
    }

    @Override
    public void eliminarArticulo(Articulo articulo) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(articulo);
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
    public Articulo obtenerArticuloPorCodigo(Session session, String codigoArticulo) throws Exception 
    {
        String hql = "FROM Articulo WHERE codigoArticulo = :codigoArticulo";
        Query q = session.createQuery(hql);
        q.setParameter("codigoArticulo", codigoArticulo);
        
        return (Articulo) q.uniqueResult();
    }

    @Override
    public List<Articulo> listarArticulosPorSubcategoria(Session session, Integer idSubcategoria) {
        String hql = "FROM Articulo WHERE subcategoriaarticulo.idSubcategoria = :idSubcategoria ORDER BY nombre ASC";
        Query q = session.createQuery(hql);
        q.setParameter("idSubcategoria", idSubcategoria);
        return q.list();
    }

    @Override
    public List<Articulo> listarPrecios(Session session, Integer idCategoria, Integer idSubcategoria, String codigoArticulo) throws Exception {
        StringBuilder hql = new StringBuilder();
        hql.append("FROM Articulo");
        System.out.println("llega hasta aqui");
        if(idCategoria != null)
        {
            hql.append(" WHERE codigoArticulo in (");
            hql.append(" SELECT a.codigoArticulo");
            hql.append(" FROM Categoriaarticulo c, Subcategoriaarticulo sc, Articulo a");
            hql.append(" WHERE c.idCategoria = sc.categoriaarticulo.idCategoria");
            hql.append(" AND sc.idSubcategoria = a.subcategoriaarticulo.idSubcategoria");
            hql.append(" AND c.idCategoria = :idCategoria");
            System.out.println("id subcategoria imp: "+idSubcategoria);
            if(idSubcategoria != null)
            {
                hql.append(" AND sc.idSubcategoria = :idSubcategoria");
            }
            /*if(codigoArticulo != null)
            {
                hql.append(" AND a.codigoArticulo = :codigoArticulo");
            }*/
            hql.append(" )");
        }
        hql.append(" ORDER BY codigoArticulo ASC");
        System.out.println("consulta: "+hql.toString());
        Query q = session.createQuery(hql.toString());
        if(idCategoria != null)
        {
            q.setParameter("idCategoria", idCategoria);
        }
        if(idSubcategoria != null)
        {
            q.setParameter("idSubcategoria", idSubcategoria);
        }
        /*if(idSubcategoria != null)
        {
            q.setParameter("idSubcategoria", idSubcategoria);
        }
        if(codigoArticulo != null)
        {
            q.setParameter("codigoArticulo", codigoArticulo);
        }*/
        return q.list();
    }
    
}

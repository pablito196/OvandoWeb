package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.ProveedorDAO;
import sys.model.Proveedor;
import sys.util.HibernateUtil;

public class ProveedorDAOImp implements ProveedorDAO {

    @Override
    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Proveedor";
        try {
            Query query = session.createQuery(hql);
            lista = query.list();
            for (Proveedor l : lista) {
                Hibernate.initialize(l.getCiudad());
            }
            t.commit();
            session.close();
        } catch (Exception e) {
            t.rollback();
        }
        return lista;
    }

    @Override
    public boolean guardarProveedor(Session session, Proveedor proveedor) {
        session.save(proveedor);
        return true;
    }

    @Override
    public boolean actualizarProveedor(Session session, Proveedor proveedor) {
        session.update(proveedor);
        return true;
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(proveedor);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Proveedor obtenerUltimoRegistro(Session session) throws Exception {
        String hql = "FROM Proveedor ORDER BY idProveedor DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        return (Proveedor) q.uniqueResult();
    }

    @Override
    public Proveedor obtenerProveedorPorNit(Session session, String nit) throws Exception {
        String hql = "FROM Proveedor WHERE nit = :nit";
        Query q = session.createQuery(hql);
        q.setParameter("nit", nit);
        return (Proveedor) q.uniqueResult();
    }

    @Override
    public List<Proveedor> buscarProveedores(Session session, Integer idCiudad, Integer idCategoria, Integer idSubcategoria, String codigoArticulo) throws Exception {
        List<Proveedor> lista = null;

        StringBuilder hql = new StringBuilder();
        System.out.println("Entra a buscar provedores con parametro");

        hql.append("FROM Proveedor ");
        hql.append(" WHERE");
        if (idCiudad != null) {
            hql.append(" ciudad.idCiudad = :idCiudad AND");
            System.out.println(" consulta: " + hql.toString());
        }

        hql.append(" idProveedor in (");
        hql.append(" SELECT comp.proveedor.idProveedor ");
        hql.append(" FROM Compra comp, Detallecompra dc, Articulo art, Categoriaarticulo ca, Subcategoriaarticulo sc");
        hql.append(" WHERE comp.idCompra = dc.compra.idCompra");
        hql.append(" AND dc.articulo.codigoArticulo = art.codigoArticulo");
        hql.append(" AND sc.idSubcategoria = art.subcategoriaarticulo.idSubcategoria");
        hql.append(" AND sc.categoriaarticulo.idCategoria = ca.idCategoria");
        if (idCategoria != null) {
            hql.append(" AND ca.idCategoria = :idCategoria");
            System.out.println(" consulta: " + hql.toString());
        }
        System.out.println(" id subcategoria: " + idSubcategoria);
        if (idSubcategoria != null) {
            hql.append(" AND sc.idSubcategoria = :idSubcategoria");
            System.out.println(" consulta: " + hql.toString());
        }
        if (codigoArticulo != null) {
            hql.append(" AND art.codigoArticulo = :codigoArticulo");
            System.out.println(" consulta: " + hql.toString());
        }

        hql.append(" )");
        hql.append(" ORDER BY idProveedor asc");

        System.out.println("consulta: " + hql.toString());

        Query q = session.createQuery(hql.toString());
        if (idCiudad != null) {
            q.setParameter("idCiudad", idCiudad);
        }
        if (idCategoria != null) {
            q.setParameter("idCategoria", idCategoria);
        }
        if (idSubcategoria != null) {
            q.setParameter("idSubcategoria", idSubcategoria);
        }
        if (codigoArticulo != null) {
            q.setParameter("codigoArticulo", codigoArticulo);
        }
        lista = q.list();
        for (Proveedor l : lista) {
            Hibernate.initialize(l.getCiudad());
        }
        return lista;
    }

}

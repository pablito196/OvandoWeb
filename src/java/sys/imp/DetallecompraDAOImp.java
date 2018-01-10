package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.DetallecompraDAO;
import sys.model.Detallecompra;
import sys.util.HibernateUtil;

public class DetallecompraDAOImp implements DetallecompraDAO
{

    @Override
    public List<DetallecompraDAO> listarDetalleCompra(Session session, Integer idCompra) throws Exception 
    {
         
        
        Transaction t = session.beginTransaction();
        String hql = "FROM Detallecompra WHERE idCompra = :idCompra";
        Query q = session.createQuery(hql);
        q.setParameter("idCompra", idCompra);
        return (List<DetallecompraDAO>) q.uniqueResult();
    }

    @Override
    public void actualizarDetalleCompra(DetallecompraDAO categoria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Detallecompra buscarUltimaCompraDetallePorArticulo(Session session, String codigoArticulo) throws Exception {
        String hql = "FROM Detallecompra detcompra WHERE detcompra.idDetalleCompra = (select max(b.idDetalleCompra) from Detallecompra b where b.articulo.codigoArticulo = :codigoArticulo)";
        //String hql = "FROM Detallecompra WHERE codigoArticulo = :codigoArticulo ORDER BY idDetalleCompra DESC";
        Query q = session.createQuery(hql);
        q.setParameter("codigoArticulo", codigoArticulo);
        
        return (Detallecompra) q.uniqueResult();
    }

    @Override
    public boolean guardarDetalleCompra(Session session, Detallecompra detalleCompra) throws Exception {
        session.save(detalleCompra);
        return true;
    }

    @Override
    public List<Detallecompra> listarDetalleCompraPorCompra(Session session, Integer idCompra) {
        String hql = "FROM Detallecompra WHERE compra.idCompra = :idCompra";
        Query q = session.createQuery(hql);
        q.setParameter("idCompra", idCompra);
        return q.list();
    }
    
}

package sys.imp;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.ExistenciaDAO;
import sys.model.Existencia;
import sys.util.HibernateUtil;

public class ExistenciaDAOImp implements ExistenciaDAO{

    @Override
    public Existencia buscarExistenciaPorAlmacen(Session session, Integer idAlmacen, String codigoArticulo) throws Exception {
        String hql = "FROM Existencia WHERE idAlmacen = :idAlmacen AND codigoArticulo = :codigoArticulo";
        Query q = session.createQuery(hql);
        q.setParameter("idAlmacen", idAlmacen);
        q.setParameter("codigoArticulo", codigoArticulo);
        
        return (Existencia) q.uniqueResult();
    }

    @Override
    public boolean guardarExistencia(Session session, Existencia existencia) throws Exception {
        session.save(existencia);
        return true;
    }

    @Override
    public boolean actualizarExistencia(Session session, Existencia existencia) throws Exception {
        session.update(existencia);
        return true;
    }

    @Override
    public Double totalExistenciaArticuloPorSucursal(Session session, String codigoArticulo, Integer idSucursal) throws Exception {
        String hql = "SELECT SUM(cantidad) FROM Existencia WHERE codigoArticulo = :codigoArticulo and almacen.idAlmacen in (SELECT idAlmacen FROM Almacen WHERE sucursal.idSucursal = :idSucursal)";
        Query q = session.createQuery(hql);
        q.setParameter("codigoArticulo", codigoArticulo);
        q.setParameter("idSucursal", idSucursal);
        return (Double) q.uniqueResult();
    }

    @Override
    public Double totalExistenciaArticuloPorSucursal(String codigoArticulo, Integer idSucursal) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "SELECT SUM(cantidad) FROM Existencia WHERE codigoArticulo = :codigoArticulo and almacen.idAlmacen in (SELECT idAlmacen FROM Almacen WHERE sucursal.idSucursal = :idSucursal)";
        Query q = session.createQuery(hql);
        q.setParameter("codigoArticulo", codigoArticulo);
        q.setParameter("idSucursal", idSucursal);
        t.commit();
        session.close();
        return (Double) q.uniqueResult();
    }
    
}

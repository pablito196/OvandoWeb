
package sys.imp;

import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.CronocomprascredDAO;
import sys.model.Cronocomprascred;

public class CronocomprascredDAOImp implements CronocomprascredDAO{

    @Override
    public boolean guardarCronogramaCompras(Session session, Cronocomprascred cronogramaCompras) throws Exception {
        session.save(cronogramaCompras);
        return true;
    }

    @Override
    public Cronocomprascred obtenerUltimoRegistro(Session session) throws Exception {
        String hql = "FROM Cronocomprascred ORDER BY idCronogramaC DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        return (Cronocomprascred) q.uniqueResult();
    }

    @Override
    public Integer buscarCronogramaPorCompra(Session session, Integer idCompra) throws Exception {
        String hql = "SELECT MAX(idCronogramaC) FROM Cronocomprascred WHERE idCronogramaC <  (SELECT MAX(idCronogramaC) FROM Cronocomprascred WHERE compra.idCompra = :idCompra) AND compra.idCompra = :idCompra ";
        Query q = session.createQuery(hql);
        q.setParameter("idCompra", idCompra);
        return (Integer) q.uniqueResult();
    }

    @Override
    public Cronocomprascred obtenerUltimoCronogramaPorCompra(Session session, Integer idCompra) throws Exception {
        String hql = "FROM Cronocomprascred WHERE idCronogramaC = (SELECT MAX(idCronogramaC) FROM Cronocomprascred WHERE compra.idCompra = :idCompra)";
        Query q = session.createQuery(hql);
        q.setParameter("idCompra", idCompra);
        return (Cronocomprascred) q.uniqueResult();
    }
    
}

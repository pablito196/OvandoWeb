
package sys.imp;

import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.CronoventascreditoDAO;
import sys.model.Cronoventascredito;


public class CronoventascreditoDAOImp implements CronoventascreditoDAO{

    @Override
    public Integer buscarCronogramaPorVenta(Session session, Integer numeroVenta) throws Exception {
        String hql = "SELECT MAX(idCronoV) FROM Cronoventascredito WHERE idCronoV <  (SELECT MAX(idCronoV) FROM Cronoventascredito WHERE venta.numeroVenta = :numeroVenta) AND venta.numeroVenta = :numeroVenta ";
        Query q = session.createQuery(hql);
        q.setParameter("numeroVenta", numeroVenta);
        return (Integer) q.uniqueResult();
    }

    @Override
    public Cronoventascredito obtenerUltimoCronogramaPorVenta(Session session, Integer numeroVenta) throws Exception {
        String hql = "FROM Cronoventascredito WHERE idCronoV = (SELECT MAX(idCronoV) FROM Cronoventascredito WHERE venta.numeroVenta = :numeroVenta)";
        Query q = session.createQuery(hql);
        q.setParameter("numeroVenta", numeroVenta);
        return (Cronoventascredito) q.uniqueResult();
    }

    @Override
    public boolean guardarCronogramaVentas(Session session, Cronoventascredito cronogramaVentas) throws Exception {
        session.save(cronogramaVentas);
        return true;
    }

    @Override
    public Cronoventascredito obtenerUltimoRegistro(Session session) throws Exception {
         String hql = "FROM Cronoventascredito ORDER BY idCronoV DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        return (Cronoventascredito) q.uniqueResult();
    }
    
}

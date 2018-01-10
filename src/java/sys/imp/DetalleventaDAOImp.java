package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.DetalleventaDAO;
import sys.model.Detalleventa;

public class DetalleventaDAOImp implements DetalleventaDAO{

    @Override
    public List<Detalleventa> listarDetalleVentaPorVenta(Session session, Integer numeroVenta) {
        List<Detalleventa> lista;
        String hql = "FROM Detalleventa WHERE venta.numeroVenta = :numeroVenta";
        Query q = session.createQuery(hql);
        q.setParameter("numeroVenta", numeroVenta);
        lista = q.list();
         for (Detalleventa l : lista) {
                Hibernate.initialize(l.getArticulo());
            }
        return lista;
    }

    @Override
    public boolean guardarDetalleVenta(Session session, Detalleventa detalleVenta) throws Exception {
        session.save(detalleVenta);
        return true;
    }

    @Override
    public boolean actualizarDetalleVenta(Session session, Detalleventa detalleVenta) throws Exception {
        session.update(detalleVenta);
        return true;
    }
    
}

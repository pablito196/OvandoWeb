
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Detalleventa;


public interface DetalleventaDAO {
    public List<Detalleventa> listarDetalleVentaPorVenta(Session session, Integer numeroVenta);
    public boolean guardarDetalleVenta(Session session, Detalleventa detalleVenta) throws Exception;
    public boolean actualizarDetalleVenta(Session session, Detalleventa detalleVenta) throws Exception;
}

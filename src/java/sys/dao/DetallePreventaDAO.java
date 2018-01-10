
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Detallepreventa;

public interface DetallePreventaDAO 
{
    //metodo para guardar el registro en la tabla preventa de la BD
    public boolean guardarDetallePreventa(Session session, Detallepreventa detallePreventa) throws Exception;
    public boolean actualizarDetallePreventa(Session session, Detallepreventa detallePreventa) throws Exception;
    public List<Detallepreventa> listarDetallepreventaPornumeroPreventa(Session session, Integer numeroPreVenta);
}

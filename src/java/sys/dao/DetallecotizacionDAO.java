
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Detallecotizacion;

public interface DetallecotizacionDAO {
    public boolean guardarDetalleCotizacion(Session session, Detallecotizacion detalleCotizacion) throws Exception;
    public List<Detallecotizacion> listarDetalleCotizacionPorCotizacion(Session session, Integer numeroCotizacion);
}

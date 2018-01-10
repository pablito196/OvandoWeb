
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Cotizacion;

public interface CotizacionDAO {
    public Long obtenerTotalRegistrosCotizacion(Session session);
    public Cotizacion obtenerUltimoRegistro(Session session)throws Exception;
    public boolean guardarCotizacion(Session session, Cotizacion cotizacion) throws Exception;
    public List<Cotizacion> listarCotizacion();
}


package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.DetallecotizacionDAO;
import sys.model.Detallecotizacion;

public class DetallecotizacionDAOImp implements DetallecotizacionDAO {

    @Override
    public boolean guardarDetalleCotizacion(Session session, Detallecotizacion detalleCotizacion) throws Exception {
        session.save(detalleCotizacion);
        return true;
    }

    @Override
    public List<Detallecotizacion> listarDetalleCotizacionPorCotizacion(Session session, Integer numeroCotizacion) {
        String hql = "FROM Detallecotizacion WHERE cotizacion.numeroCotizacion = :numeroCotizacion";
        Query q = session.createQuery(hql);
        q.setParameter("numeroCotizacion", numeroCotizacion);
        return q.list();
    }
    
}

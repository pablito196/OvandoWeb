
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Tipomoneda;

public interface TipomonedaDAO {
    public List<Tipomoneda> listarTipomoneda();
    public void nuevoTipomoneda(Tipomoneda tipomoneda);
    public void actualizarTipomoneda(Tipomoneda tipomoneda);
    public void eliminarTipomoneda(Tipomoneda tipomoneda);
    public Tipomoneda buscarMoneda(Session session, Integer idTipomoneda) throws Exception;
}


package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Ciudad;


public interface CiudadDAO {
    public List<Ciudad> listarCiudadesPorPais(Session session, Integer idPais);
    public void nuevoBanco(Ciudad ciudad);
    public void actualizarCiudad(Ciudad ciudad);
    public void eliminarBanco(Ciudad ciudad);
    public Ciudad buscarCiudad(Session session, Integer idCiudad) throws Exception;
    public List<Ciudad> listarCiudades();
}

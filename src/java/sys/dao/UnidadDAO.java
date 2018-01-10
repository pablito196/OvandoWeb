
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Unidad;

public interface UnidadDAO {
    public List<Unidad> listarUnidad();
    public void nuevaUnidad(Unidad unidad);
    public void actualizarUnidad(Unidad unidad);
    public void eliminarUnidad(Unidad unidad);
    public Unidad buscarUnidad(Session session, Integer idUnidad) throws Exception;
}

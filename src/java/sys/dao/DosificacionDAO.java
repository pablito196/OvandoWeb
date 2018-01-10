
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Dosificacion;


public interface DosificacionDAO {
    public List<Dosificacion> listarDosificaciones();
    public boolean nuevaDosificacion(Session session, Dosificacion dosificacion);
    public boolean actualizarDosificacion(Session session, Dosificacion dosificacion);
    public void eliminarDosificacion(Dosificacion dosificacion);
    public Dosificacion buscarDosificacionActiva(Session session, Integer idSucursal) throws Exception;
    public Dosificacion buscarDosificacion(Session session, String numeroAutorizacion) throws Exception;
}

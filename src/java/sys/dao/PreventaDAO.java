
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Preventa;


public interface PreventaDAO 
{
    //obtener el ultimo registro de la tabla preventa en la BD
    public Preventa obtenerUltimoRegistro(Session session)throws Exception;
    //obtener el ultimo registro de la tabla preventa en la BD por tiempo de registro
    public Preventa obtenerUltimoRegistroTiempo(Session session, Long tiempo)throws Exception;
    //Averiguar si la tabla preventa tiene registros
    public Long obtenerTotalRegistrosPreventa(Session session);
    //metodo para guardar el registro en la tabla preventa de la BD
    public boolean guardarPreventa(Session session, Preventa preventa) throws Exception;
    public List<Preventa> listarPreventas();
    public boolean actualizarPreventa(Session session, Preventa preventa) throws Exception;
}

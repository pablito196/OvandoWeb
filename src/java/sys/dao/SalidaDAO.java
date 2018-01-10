
package sys.dao;

import org.hibernate.Session;
import sys.model.Salida;


public interface SalidaDAO {
    public boolean guardarSalida(Session session, Salida salida) throws Exception;
}

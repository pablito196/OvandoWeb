
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Ingreso;


public interface IngresoDAO {
    public List<Ingreso> listarIngresoPorEmpleado(Session session, Integer idEmpleado);
    public boolean guardarIngreso(Session session, Ingreso ingreso); 
}

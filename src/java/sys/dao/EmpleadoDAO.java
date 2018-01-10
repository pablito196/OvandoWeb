package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Empleado;

public interface EmpleadoDAO {
    public List<Empleado> listarEmpleados();
    public void nuevoEmpleado(Empleado empleado);
    public void actualizarEmpleado(Empleado empleado);
    public void eliminarEmpleado(Empleado empleado);
    public boolean guardarEmpleado(Session session, Empleado empleado) throws Exception;
    public Empleado buscarEmpleado(Session session, Integer idEmpleado) throws Exception;
}

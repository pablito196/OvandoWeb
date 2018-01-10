
package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.IngresoDAO;
import sys.model.Ingreso;


public class IngresoDAOImp implements IngresoDAO{

    @Override
    public List<Ingreso> listarIngresoPorEmpleado(Session session, Integer idEmpleado) {
        List<Ingreso> lista = null;
        String hql = "FROM Ingreso WHERE empleado.idEmpleado = :idEmpleado";
        Query q = session.createQuery(hql);
        q.setParameter("idEmpleado", idEmpleado);
        lista = q.list();
        for (Ingreso l : lista) {
                Hibernate.initialize(l.getSucursal());
            }
        return q.list();
    }

    @Override
    public boolean guardarIngreso(Session session, Ingreso ingreso) {
        session.save(ingreso);
        return true;
    }
    
}

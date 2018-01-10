
package sys.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import sys.model.Gasto;


public interface GastoDAO {
    public List<Gasto> listarGastos();
    public void nuevoGasto(Gasto gasto);
    public void actualizarGasto(Gasto gasto);
    public void eliminarGasto(Gasto gasto);
    public List<Gasto> listarGastosPorFechas(Session session, Date fechaInicio, Date fechaFin) throws Exception;
}

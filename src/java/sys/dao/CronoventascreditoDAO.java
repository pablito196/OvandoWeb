
package sys.dao;

import org.hibernate.Session;
import sys.model.Cronoventascredito;

public interface CronoventascreditoDAO {
    public Integer buscarCronogramaPorVenta(Session session, Integer numeroVenta) throws Exception;
    public Cronoventascredito obtenerUltimoCronogramaPorVenta(Session session, Integer numeroVenta) throws Exception;
    public boolean guardarCronogramaVentas(Session session, Cronoventascredito cronogramaVentas) throws Exception;
    public Cronoventascredito obtenerUltimoRegistro(Session session) throws Exception;
}

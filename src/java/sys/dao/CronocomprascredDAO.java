
package sys.dao;

import org.hibernate.Session;
import sys.model.Cronocomprascred;


public interface CronocomprascredDAO {
    public boolean guardarCronogramaCompras(Session session, Cronocomprascred cronogramaCompras) throws Exception;
    public Cronocomprascred obtenerUltimoRegistro(Session session) throws Exception;
    public Integer buscarCronogramaPorCompra(Session session, Integer idCompra) throws Exception;
    public Cronocomprascred obtenerUltimoCronogramaPorCompra(Session session, Integer idCompra) throws Exception;
}

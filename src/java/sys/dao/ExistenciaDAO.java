package sys.dao;

import org.hibernate.Session;
import sys.model.Existencia;


public interface ExistenciaDAO {
    public Existencia buscarExistenciaPorAlmacen(Session session, Integer idAlmacen, String codigoArticulo) throws Exception;
    public boolean guardarExistencia(Session session, Existencia existencia) throws Exception;
    public boolean actualizarExistencia(Session session, Existencia existencia) throws Exception;
    public Double totalExistenciaArticuloPorSucursal(Session session, String codigoArticulo, Integer idSucursal) throws Exception;
    public Double totalExistenciaArticuloPorSucursal(String codigoArticulo, Integer idSucursal) throws Exception;
}

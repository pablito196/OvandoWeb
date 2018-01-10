
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Sucursal;

public interface SucursalDAO {
    public List<Sucursal> listarSucursal();
    public void nuevaSucursal(Sucursal sucursal);
    public void actualizarSucursal(Sucursal sucursal);
    public void eliminarSucursal(Sucursal sucursal);
    public Sucursal buscarSucursal(Session session, Integer idSucursal) throws Exception;
    public Sucursal buscarSucursal(Integer idSucursal);
}

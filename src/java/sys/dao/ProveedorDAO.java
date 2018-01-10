
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Proveedor;


public interface ProveedorDAO
{
    public List<Proveedor> listarProveedores();
    public boolean guardarProveedor(Session session, Proveedor proveedor);
    public boolean actualizarProveedor(Session session, Proveedor proveedor);
    public void eliminarProveedor(Proveedor proveedor);
    public Proveedor obtenerUltimoRegistro(Session session)throws Exception;
    public Proveedor obtenerProveedorPorNit(Session session, String nit) throws Exception;
    public List<Proveedor> buscarProveedores(Session session, Integer idCiudad, Integer idCategoria, Integer idSubcategoria, String codigoArticulo) throws Exception;
}

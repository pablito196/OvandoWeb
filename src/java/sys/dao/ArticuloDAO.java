
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Articulo;

public interface ArticuloDAO 
{
    public List<Articulo> listarArticulos();
    public boolean nuevoArticulo(Session session, Articulo articulo);
    public boolean actualizarArticulo(Session session, Articulo articulo);
    public void eliminarArticulo(Articulo articulo);
    
    //este metodo se utiliza en compras, preventa, venta
    public Articulo obtenerArticuloPorCodigo(Session session, String codigoArticulo) throws Exception;
    //este metodo se utiliza para listar articulos por subcategoria
    public List<Articulo> listarArticulosPorSubcategoria(Session session, Integer idSubcategoria);
    //metodo para sacar listado de precios
    public List<Articulo> listarPrecios(Session session, Integer idCategoria, Integer idSubCategoria, String codigoArticulo) throws Exception;
}

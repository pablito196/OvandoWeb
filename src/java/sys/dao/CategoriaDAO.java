
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Categoriaarticulo;

public interface CategoriaDAO 
{
    public List<Categoriaarticulo> listarCategorias();
    public void nuevaCategoria(Categoriaarticulo categoria);
    public void actualizarCategoria(Categoriaarticulo categoria);
    public void eliminarCategoria(Categoriaarticulo categoria);
    
    public Categoriaarticulo buscarCategoria(Session session, Integer idCategoria) throws Exception;
}

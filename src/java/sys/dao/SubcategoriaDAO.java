package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Subcategoriaarticulo;

public interface SubcategoriaDAO 
{
    public List<Subcategoriaarticulo> listarSubcategorias();
    public List<Subcategoriaarticulo> listarSubcategorias(Subcategoriaarticulo subcategoria);
    public boolean nuevaSubcategoria(Session session, Subcategoriaarticulo subcategoria);
    public void actualizarSubcategoria(Subcategoriaarticulo subcategoria);
    public void eliminarSubcategoria(Subcategoriaarticulo subcategoria);
    
    public List<Subcategoriaarticulo> listarSubcategoriaPorPais(Session session, Integer idCategoria);
    public Subcategoriaarticulo buscarSubcategoria(Session session, Integer idSubcategoria) throws Exception;
}

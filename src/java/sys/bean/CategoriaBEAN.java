
package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import sys.dao.CategoriaDAO;
import sys.imp.CategoriaDAOImp;
import sys.model.Categoriaarticulo;

@ManagedBean
@ViewScoped
public class CategoriaBEAN 
{
    private List<Categoriaarticulo> listaCategorias;
    private Categoriaarticulo categoria;
    
    private List<SelectItem> lstCategorias;
    
    
    public CategoriaBEAN() 
    {
        categoria = new Categoriaarticulo();
    }

    
    public void setListaCategorias(List<Categoriaarticulo> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public Categoriaarticulo getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoriaarticulo categoria) {
        this.categoria = categoria;
    }
    
    public List<Categoriaarticulo> getListaCategorias() {
        CategoriaDAO dao =  new CategoriaDAOImp();
        listaCategorias = dao.listarCategorias();
        return listaCategorias;
    }

    public void prepararNuevaCategoria()
    {
        categoria = new Categoriaarticulo();
    }
    
    public void nuevaCategoria()
    {
        CategoriaDAO dao = new CategoriaDAOImp();
        dao.nuevaCategoria(categoria);
    }
    
    public void actualizarCategoria()
    {
        CategoriaDAO dao =  new CategoriaDAOImp();
        dao.actualizarCategoria(categoria);
        categoria = new Categoriaarticulo();
    }
    
    public void eliminarCategoria()
    {
        CategoriaDAO dao = new CategoriaDAOImp();
        dao.eliminarCategoria(categoria);
        categoria = new Categoriaarticulo();
    }

    public List<SelectItem> getLstCategorias() throws Exception 
    {
        this.lstCategorias = new ArrayList<SelectItem>();
        listar();
        lstCategorias.clear();
        for (Categoriaarticulo categorias :listaCategorias) 
        {
            SelectItem categoriaItem = new SelectItem(categorias.getIdCategoria(),categorias.getNombreCategoria());
            this.lstCategorias.add(categoriaItem);
        }
        return lstCategorias;
        
    }
    
    public void listar() throws Exception
    {
        CategoriaDAO dao;
        try
        {
            dao = new CategoriaDAOImp();
            listaCategorias = dao.listarCategorias();
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}

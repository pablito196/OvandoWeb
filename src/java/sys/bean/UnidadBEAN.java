
package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import sys.dao.UnidadDAO;
import sys.imp.UnidadDAOImp;
import sys.model.Unidad;


@ManagedBean
@ViewScoped
public class UnidadBEAN {

    private List<Unidad> listaUnidades;
    private Unidad unidad;
    private List<SelectItem> lstUnidades;
    
    public UnidadBEAN() {
        unidad = new Unidad();
    }

    public List<Unidad> getListaUnidades() {
        UnidadDAO udao =  new UnidadDAOImp();
        listaUnidades = udao.listarUnidad();
        return listaUnidades;
    }

    public void setListaUnidades(List<Unidad> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public List<SelectItem> getLstUnidades() throws Exception {
        this.lstUnidades = new ArrayList<SelectItem>();
        listar();
        lstUnidades.clear();
        for (Unidad unidades :listaUnidades) 
        {
            SelectItem unidadItem = new SelectItem(unidades.getIdUnidad(), unidades.getSigla());
            this.lstUnidades.add(unidadItem);
        }
        return lstUnidades;
    }

    public void setLstUnidades(List<SelectItem> lstUnidades) {
        this.lstUnidades = lstUnidades;
    }
    
    //metodos
    public void listar() throws Exception
    {
        UnidadDAO dao;
        try
        {
            dao = new UnidadDAOImp();
            listaUnidades = dao.listarUnidad();
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    public void prepararNuevaUnidad()
    {
        unidad = new Unidad();
    }
    
    public void nuevaUnidad()
    {
        UnidadDAO dao = new UnidadDAOImp();
        dao.nuevaUnidad(unidad);
    }
    
    public void actualizarUnidad()
    {
        UnidadDAO dao =  new UnidadDAOImp();
        dao.actualizarUnidad(unidad);
        unidad = new Unidad();
    }
    
    
}

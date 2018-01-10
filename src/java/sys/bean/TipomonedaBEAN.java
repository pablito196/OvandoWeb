
package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import sys.dao.TipomonedaDAO;
import sys.imp.TipomonedaDAOImp;
import sys.model.Tipomoneda;


@ManagedBean
@ViewScoped
public class TipomonedaBEAN {

    private Tipomoneda tipomoneda;
    private List<SelectItem> lstMonedas;
    private List<Tipomoneda> listaMonedas;
    
    public TipomonedaBEAN() {
        this.tipomoneda = new Tipomoneda();
    }

    public Tipomoneda getTipomoneda() {
        return tipomoneda;
    }

    public void setTipomoneda(Tipomoneda tipomoneda) {
        this.tipomoneda = tipomoneda;
    }

    public List<SelectItem> getLstMonedas() throws Exception {
        this.lstMonedas = new ArrayList<SelectItem>();
        listar();
        lstMonedas.clear();
        for (Tipomoneda monedas :listaMonedas) 
        {
            SelectItem monedaItem = new SelectItem(monedas.getIdTipomoneda(), monedas.getDescripcion());
            this.lstMonedas.add(monedaItem);
        }
        return lstMonedas;
    }

    public void setLstMonedas(List<SelectItem> lstMonedas) {
        this.lstMonedas = lstMonedas;
    }

    public List<Tipomoneda> getListaMonedas() {
        TipomonedaDAO tmDao =  new TipomonedaDAOImp();
        listaMonedas = tmDao.listarTipomoneda();
        return listaMonedas;
    }

    public void setListaMonedas(List<Tipomoneda> listaMonedas) {
        this.listaMonedas = listaMonedas;
    }
    
    public void listar() throws Exception
    {
        TipomonedaDAO tmDao;
        try
        {
            tmDao = new TipomonedaDAOImp();
            listaMonedas = tmDao.listarTipomoneda();
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}

package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import sys.dao.PaisDAO;
import sys.imp.PaisDAOImp;
import sys.model.Pais;

@ManagedBean
@ViewScoped
public class PaisBEAN {

    private Pais pais;
    private List<SelectItem> lstPaises;
    private List<Pais> listaPaises;

    public PaisBEAN() {
        this.pais = new Pais();
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<SelectItem> getLstPaises() throws Exception {
        this.lstPaises = new ArrayList<SelectItem>();
        listar();
        lstPaises.clear();
        for (Pais paises :listaPaises) 
        {
            SelectItem paisItem = new SelectItem(paises.getIdPais(), paises.getNombrePais());
            this.lstPaises.add(paisItem);
        }
        return lstPaises;
    }

    public void setLstPaises(List<SelectItem> lstPaises) {
        this.lstPaises = lstPaises;
    }

    public List<Pais> getListaPaises() {
        PaisDAO pDao =  new PaisDAOImp();
        listaPaises = pDao.listarPaises();
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }
    
    public void listar() throws Exception
    {
        PaisDAO pDao;
        try
        {
            pDao = new PaisDAOImp();
            listaPaises = pDao.listarPaises();
        }
        catch(Exception e)
        {
            throw e;
        }
    }

}

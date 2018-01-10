
package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.CiudadDAO;
import sys.imp.CiudadDAOImp;
import sys.model.Ciudad;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class CiudadBEAN {

    Session session;
    Transaction transaction;
    private Ciudad ciudad;
    private List<SelectItem> lstCiudades;
    private List<Ciudad> listaCiudades;
    private List<Ciudad> listaCompletaCiudades;
    private List<SelectItem> lstCompletaCiudades;
    private Integer idPais;
    
    public CiudadBEAN() {
        this.ciudad = new Ciudad();
        this.listaCompletaCiudades = new ArrayList<>();
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Ciudad> getListaCompletaCiudades() {
        CiudadDAO cDao = new CiudadDAOImp();
        listaCompletaCiudades = cDao.listarCiudades();
        return listaCompletaCiudades;
    }

    public void setListaCompletaCiudades(List<Ciudad> listaCompletaCiudades) {
        this.listaCompletaCiudades = listaCompletaCiudades;
    }

    public List<SelectItem> getLstCompletaCiudades() {
        this.lstCompletaCiudades = new ArrayList<SelectItem>();
        CiudadDAO cDao = new CiudadDAOImp();
        listaCompletaCiudades = cDao.listarCiudades();
        lstCompletaCiudades.clear();
        for(Ciudad ciudades : listaCompletaCiudades)
        {
            SelectItem ciudadItem = new SelectItem(ciudades.getIdCiudad(), ciudades.getNombreCiudad());
            lstCompletaCiudades.add(ciudadItem);
        }
        return lstCompletaCiudades;
    }

    public void setLstCompletaCiudades(List<SelectItem> lstCompletaCiudades) {
        this.lstCompletaCiudades = lstCompletaCiudades;
    }

    public List<SelectItem> getLstCiudades() throws Exception {
        this.lstCiudades = new ArrayList<SelectItem>();
        listarCiudadPorPais();
        lstCiudades.clear();
        for (Ciudad ciudades :listaCiudades) 
        {
            SelectItem ciudadItem = new SelectItem(ciudades.getIdCiudad(), ciudades.getNombreCiudad());
            this.lstCiudades.add(ciudadItem);
        }
        return lstCiudades;
    }

    public void setLstCiudades(List<SelectItem> lstCiudades) {
        this.lstCiudades = lstCiudades;
    }

    public List<Ciudad> getListaCiudades() {
       
        return listaCiudades;
    }

    public void setListaCiudades(List<Ciudad> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }
    
    //metodo para listar ciudades en base a un pais
    public void listarCiudadPorPais()
    {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            CiudadDAO cDao = new CiudadDAOImp();
            this.transaction = this.session.beginTransaction();
            this.listaCiudades = cDao.listarCiudadesPorPais(this.session, this.idPais);
            this.transaction.commit();
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();
                
            }
            System.out.println(e.getMessage());
        }finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
}


package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import sys.dao.SucursalDAO;
import sys.imp.SucursalDAOImp;
import sys.model.Sucursal;


@ManagedBean
@ViewScoped
public class SucursalBEAN {

    private List<Sucursal> listaSucursales;
    private Sucursal sucursal;
    private List<SelectItem> lstSucursales;
    
    public SucursalBEAN() {
        sucursal = new Sucursal();
    }

    public List<Sucursal> getListaSucursales() {
        SucursalDAO dao =  new SucursalDAOImp();
        listaSucursales = dao.listarSucursal();
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public List<SelectItem> getLstSucursales() throws Exception {
        this.lstSucursales = new ArrayList<SelectItem>();
        listar();
        lstSucursales.clear();
        for (Sucursal sucursales :listaSucursales) 
        {
            SelectItem sucursalItem = new SelectItem(sucursales.getIdSucursal(),sucursales.getNombreSucursal());
            this.lstSucursales.add(sucursalItem);
        }
        return lstSucursales;
    }

    public void setLstSucursales(List<SelectItem> lstSucursales) {
        this.lstSucursales = lstSucursales;
    }
    
    //metodos
   public void listar() throws Exception
    {
        SucursalDAO sDao;
        try
        {
            sDao = new SucursalDAOImp();
            listaSucursales = sDao.listarSucursal();
        }
        catch(Exception e)
        {
            throw e;
        }
    }
       
   /* public void prepararNuevaSucursal()
    {
        sucursal = new Sucursal();
    }
    
    public void nuevaSucursal()
    {
        SucursalDAO dao = new SucursalDAOImp();
        dao.nuevaSucursal(sucursal);
    }
    
    public void actualizarSucursal()
    {
        SucursalDAO dao =  new SucursalDAOImp();
        dao.actualizarSucursal(sucursal);
        sucursal = new Sucursal();
    }*/
    
}

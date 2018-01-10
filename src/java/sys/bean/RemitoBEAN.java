package sys.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.SelectEvent;
import sys.clasesauxiliares.DetalleRemito;
import sys.dao.RemitoDAO;
import sys.imp.RemitoDAOImp;
import sys.model.Remito;


@ManagedBean
@ViewScoped
public class RemitoBEAN {

    private Remito remito;
    
    private List<Remito> listaRemitos;
    private List<Remito> listaFiltradaRemitos;
    
    public RemitoBEAN() {
        this.remito = new Remito();
        this.listaRemitos = new ArrayList<>();
    }

    public Remito getRemito() {
        return remito;
    }

    public void setRemito(Remito remito) {
        this.remito = remito;
    }

    public List<Remito> getListaRemitos() {
        RemitoDAO dao = new RemitoDAOImp();
        listaRemitos = dao.listarRemito();
        return listaRemitos;
    }

    public void setListaRemitos(List<Remito> listaRemitos) {
        this.listaRemitos = listaRemitos;
    }

    public List<Remito> getListaFiltradaRemitos() {
        return listaFiltradaRemitos;
    }

    public void setListaFiltradaRemitos(List<Remito> listaFiltradaRemitos) {
        this.listaFiltradaRemitos = listaFiltradaRemitos;
    }
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    public void actualizarRemito()
    {
        RemitoDAO rDao = new RemitoDAOImp();
        rDao.actualizarRemito(this.remito);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Entrega de remito registrada"));
    }
    
    public void verRemito() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

         //Instancia hacia la clase DetalleRemito        
        DetalleRemito dRemito = new DetalleRemito();
        
        String reporte = "";
        
        if(this.remito.getTipoRemito().equals("VALORADO"))
        {
            reporte = "/Reportes/DetallerRemito.jasper";
        }
        else
        {
            reporte = "/Reportes/DetalleRemitoNoValorado.jasper";
        }
        
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath(reporte);

        dRemito.getReporte(ruta, this.remito.getVenta().getNumeroVenta());
        FacesContext.getCurrentInstance().responseComplete();
               
    }
}

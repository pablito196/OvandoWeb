package sys.bean;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.SelectEvent;
import sys.dao.DosificacionDAO;
import sys.imp.DosificacionDAOImp;
import sys.model.Dosificacion;
import sys.model.Sucursal;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class DosificacionBEAN {

    Session session = null;
    Transaction transaction = null;

    private List<Dosificacion> listaDosificaciones;
    private Dosificacion dosificacion;
    
    private Integer idSucursal;

    public DosificacionBEAN() {
        this.dosificacion = new Dosificacion();
    }

    public List<Dosificacion> getListaDosificaciones() {
        DosificacionDAO dao = new DosificacionDAOImp();
        listaDosificaciones = dao.listarDosificaciones();
        return listaDosificaciones;
    }

    public void setListaDosificaciones(List<Dosificacion> listaDosificaciones) {
        this.listaDosificaciones = listaDosificaciones;
    }

    public Dosificacion getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(Dosificacion dosificacion) {
        this.dosificacion = dosificacion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    //metodos
    public void prepararNuevaDosificacion() {
        dosificacion = new Dosificacion();
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    
    public void nuevaDosificacion() {
        this.session = null;
        this.transaction = null;
        
        Dosificacion dosif = new Dosificacion();
        dosif = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            
            DosificacionDAO dDao = new DosificacionDAOImp();
            this.transaction = this.session.beginTransaction();
            dosif = dDao.buscarDosificacionActiva(this.session, this.idSucursal);
            if(dosif != null)
            {
                dosif.setEstado((short) 0);
                System.out.println("encuentra dosificacion activa, cambia estado a: "+dosif.getEstado());
                dDao.actualizarDosificacion(this.session, dosif);
            }
            java.sql.Date fechalimiteemision = new java.sql.Date(this.dosificacion.getFechaLimiteEmision().getTime());
            //System.out.println("fecha limite emision: "+this.dosificacion.getFechaLimiteEmision());
            //System.out.println("fecha limite emision formateada: "+fechalimiteemision);
            
            this.dosificacion.setEstado((short) 1);
            this.dosificacion.setInicialActivo(1);
            this.dosificacion.setFechaLimiteEmision(fechalimiteemision);
            Sucursal sucursal = new Sucursal();
            sucursal.setIdSucursal(this.idSucursal);
            this.dosificacion.setSucursal(sucursal);
            dDao.nuevaDosificacion(this.session, this.dosificacion);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Dosificación registrada"));

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

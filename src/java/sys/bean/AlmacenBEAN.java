package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.AlmacenDAO;
import sys.dao.SucursalDAO;
import sys.imp.AlmacenDAOImp;
import sys.imp.SucursalDAOImp;
import sys.model.Almacen;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class AlmacenBEAN {
    
    Session session;
    Transaction transaction;

    private List<Almacen> listaAlmacenes;
    private Almacen almacen;
    private Integer idSucursal;
    private List<SelectItem> lstAlmacenes;
    
    public AlmacenBEAN() {
        almacen = new Almacen();
    }

    public List<Almacen> getListaAlmacenes() {
        AlmacenDAO dao = new AlmacenDAOImp();
        listaAlmacenes = dao.listarAlmacen();
        return listaAlmacenes;
    }

    public void setListaAlmacenes(List<Almacen> listaAlmacenes) {
        this.listaAlmacenes = listaAlmacenes;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public List<SelectItem> getLstAlmacenes() {
        this.lstAlmacenes = new ArrayList<SelectItem>();
        listarAlmacenPorSucursal();
        lstAlmacenes.clear();
        for (Almacen almacenes :listaAlmacenes) 
        {
            SelectItem almacenItem = new SelectItem(almacenes.getIdAlmacen(), almacenes.getNombreAlmacen());
            this.lstAlmacenes.add(almacenItem);
        }
             
        return lstAlmacenes;
    }

    public void setLstAlmacenes(List<SelectItem> lstAlmacenes) {
        this.lstAlmacenes = lstAlmacenes;
    }
    
       
    //metodos
    //metodo para listar un almacen por sucursal
    public void listarAlmacenPorSucursal()
    {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            AlmacenDAO aDao = new AlmacenDAOImp();
            this.transaction = this.session.beginTransaction();
            this.listaAlmacenes = aDao.listarAlmacenPorSucursal(this.session, this.idSucursal);
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
    public void prepararNuevoAlmacen() {
        this.almacen = new Almacen();
    }
    public void nuevoAlmacen() {
        
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            SucursalDAO sDao = new SucursalDAOImp();
            AlmacenDAO aDao = new AlmacenDAOImp();
            this.transaction = this.session.beginTransaction();
            this.almacen.setSucursal(sDao.buscarSucursal(this.session, this.idSucursal));
            this.almacen.setPrincipal(Boolean.FALSE);
            aDao.nuevoAlmacen(this.session, this.almacen);
            this.transaction.commit();
            this.almacen = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Almacen registrado"));
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
    
    public void actualizarAlmacen() {
        
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            SucursalDAO sDao = new SucursalDAOImp();
            AlmacenDAO aDao = new AlmacenDAOImp();
            this.transaction = this.session.beginTransaction();
            this.almacen.setSucursal(sDao.buscarSucursal(this.session, this.idSucursal));
            this.almacen.setPrincipal(Boolean.FALSE);
            aDao.actualizarAlmacen(this.session, this.almacen);
            this.transaction.commit();
            this.almacen = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Almacen modificado"));
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
    
    public void definirAlmacenPrincipal() {
        this.session = null;
        this.transaction = null;
        
        Almacen almac = new Almacen();
        almac = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            
            AlmacenDAO aDao = new AlmacenDAOImp();
            this.transaction = this.session.beginTransaction();
            almac = aDao.buscarAlmacenPrincipal(this.session, this.almacen.getSucursal().getIdSucursal());
            if(almac != null)
            {
                almac.setPrincipal(Boolean.FALSE);
                aDao.actualizarAlmacen(this.session, almac);
            }
            this.almacen.setPrincipal(Boolean.TRUE);
            aDao.actualizarAlmacen(this.session, this.almacen);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "almacen registrado como principal"));

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

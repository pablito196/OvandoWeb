package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.primefaces.event.RowEditEvent;
import sys.dao.EmpleadoDAO;
import sys.dao.IngresoDAO;
import sys.dao.SucursalDAO;
import sys.dao.UsuarioDAO;
import sys.imp.EmpleadoDAOImp;
import sys.imp.IngresoDAOImp;
import sys.imp.SucursalDAOImp;
import sys.imp.UsuarioDAOImp;
import sys.model.Empleado;
import sys.model.Ingreso;
import sys.model.Sucursal;
import sys.model.Usuario;
import sys.util.HibernateUtil;


@ManagedBean
@ViewScoped
public class UsuarioBEAN {

    Session session = null;
    Transaction transaction = null;
    
    private Usuario usuario;
    private Sucursal sucursal;
    private Empleado empleado;
    
    private String password;
    private String rePassword;
    private Integer idSucursal;
    
    private List<Usuario> listaUsuarios;
    private List<Ingreso> listaSucursalesAsignadas;
    private List<Ingreso> listaSucursalesParaAsignar;
    
    public UsuarioBEAN() {
        this.usuario = new Usuario();
        this.empleado = new Empleado();
        this.listaSucursalesAsignadas = new ArrayList<>();
        this.listaSucursalesParaAsignar = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    
    public List<Usuario> getListaUsuarios() {
        UsuarioDAO uDao = new UsuarioDAOImp();
        listaUsuarios = uDao.listarUsuarios();
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Ingreso> getListaSucursalesAsignadas() {
        listarSucursalesAsignadas();
        return listaSucursalesAsignadas;
    }

    public void setListaSucursalesAsignadas(List<Ingreso> listaSucursalesAsignadas) {
        this.listaSucursalesAsignadas = listaSucursalesAsignadas;
    }

    public List<Ingreso> getListaSucursalesParaAsignar() {
        return listaSucursalesParaAsignar;
    }

    public void setListaSucursalesParaAsignar(List<Ingreso> listaSucursalesParaAsignar) {
        this.listaSucursalesParaAsignar = listaSucursalesParaAsignar;
    }

    
    //METODOS
    public void listarSucursalesAsignadas()
    {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            IngresoDAO iDao = new IngresoDAOImp();
            
            this.transaction = this.session.beginTransaction();
            this.listaSucursalesAsignadas = iDao.listarIngresoPorEmpleado(this.session, this.usuario.getEmpleado().getIdEmpleado());
            this.transaction.commit();
        } catch (Exception e) {if (this.transaction != null) {
                this.transaction.rollback();
                
            }
            System.out.println(e.getMessage());
        }finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
    
    public void agregarSucursalAsignada()
    {
        System.out.println("id sucursal: "+this.idSucursal);
        if (this.idSucursal <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione una sucursal"));
            this.idSucursal = 0;
        } else {
            try {
                this.session = HibernateUtil.getSessionFactory().openSession();
                SucursalDAO sDao = new SucursalDAOImp();
                EmpleadoDAO eDao = new EmpleadoDAOImp();
                //this.transaction = this.session.beginTransaction();
                System.out.println("antes de los seter");
                this.sucursal = sDao.buscarSucursal(this.session, this.idSucursal);
                this.empleado = eDao.buscarEmpleado(this.session, this.usuario.getEmpleado().getIdEmpleado());
                
                
                System.out.println("despues de los seter");
                System.out.println("id empleado despues: "+this.empleado.getIdEmpleado());
                System.out.println("id sucursal despues: "+this.sucursal.getIdSucursal());
                
                this.listaSucursalesParaAsignar.add(new Ingreso(this.empleado, this.sucursal));
                
                this.transaction.commit();
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Sucursal asignada"));
                
            } catch (Exception e) {
            }
            
        }
    }
    
     //metodo para quitar sucursal asignada
    public void quitarSucursalAsignada(String nombreSucursal, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Ingreso item : this.listaSucursalesParaAsignar) {
                if (item.getSucursal().getNombreSucursal().equals(nombreSucursal) && filaSeleccionada.equals(i)) {
                    this.listaSucursalesParaAsignar.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Sucursal eliminada"));
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }
    
    //metodo para guardar asignacion de sucursales
    public void guardarAsignacionSucursal()
    {
        this.session = null;
        this.transaction = null;
        try {
            
            
            this.session = HibernateUtil.getSessionFactory().openSession();
           
            
            this.transaction = this.session.beginTransaction();
            
            IngresoDAO iDao = new IngresoDAOImp();
            
            for (Ingreso item : listaSucursalesParaAsignar) {
                
                iDao.guardarIngreso(this.session, item);
                
            }
            this.transaction.commit();
            this.listaSucursalesParaAsignar = null;
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Sucursales asignadas al usuario"));
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();

            }
            System.out.println(e.getMessage());
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        limpiarAsignacionSucursal();
    }
    
    //limpiar asignacion sucursal
    public void limpiarAsignacionSucursal()
    {
        this.listaSucursalesAsignadas = new ArrayList<>();
        this.listaSucursalesParaAsignar = new ArrayList<>();
        this.idSucursal = 0;
        this.sucursal = null;
        this.empleado = null;
        this.usuario = null;
    }
    
    //modificar contrseña
    public void modificarPassword() {
        if (!this.rePassword.equals(this.password)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La contraseña no coincide"));

        } else {
            this.session = null;
            this.transaction = null;
            try {

                this.session = HibernateUtil.getSessionFactory().openSession();

                UsuarioDAO uDao = new UsuarioDAOImp();

                this.transaction = this.session.beginTransaction();

                //se encripta la contraseña
                StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
                s.setPassword("uniquekey");
                String passwd = s.encrypt(this.password);

                this.usuario.setPassword(passwd);

                //System.out.println("login " + this.nuevoUsuario.getLoginUsuario());
                //System.out.println("passwd " + this.nuevoUsuario.getPassword());

                uDao.actualizarUsuario(this.session, this.usuario);

                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Contraseña modificada"));
                //this.usuario = null;
                //this.nuevoUsuario = null;

            } catch (Exception e) {
                if (this.transaction != null) {
                    System.out.println(e.getMessage());
                    transaction.rollback();
                }
            } finally {
                if (this.session != null) {
                    this.session.close();
                }
            }

        }
    }
    //Metodo para editar la sucursal asignada al empleado o usuario
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se modificó la Cuenta Bancaria"));
        
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se realizó la modificación en la Cuenta Bancaria"));
    }
}

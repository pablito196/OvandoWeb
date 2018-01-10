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
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.primefaces.context.RequestContext;
import sys.dao.EmpleadoDAO;
import sys.dao.TipousuarioDAO;
import sys.dao.UsuarioDAO;
import sys.imp.EmpleadoDAOImp;
import sys.imp.TipousuarioDAOImp;
import sys.imp.UsuarioDAOImp;
import sys.model.Empleado;
import sys.model.Tipousuario;
import sys.model.Usuario;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class EmpleadoBEAN {

    Session session = null;
    Transaction transaction = null;

    private List<Empleado> listaEmpleados;
    private Empleado empleado;

    private List<SelectItem> lstTipoUsuario;
    private List<Tipousuario> listaTiposUsuario;
    private Tipousuario tipousuario;
    private Integer idTipousuario;

    private Usuario usuario;
    private Usuario nuevoUsuario;
    private String rePassword;

    public EmpleadoBEAN() {
        empleado = new Empleado();
        tipousuario = new Tipousuario();
        usuario = new Usuario();
        nuevoUsuario = new Usuario();
    }

    public List<Empleado> getListaEmpleados() {
        EmpleadoDAO dao = new EmpleadoDAOImp();
        listaEmpleados = dao.listarEmpleados();
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Tipousuario> getListaTiposUsuario() {
        return listaTiposUsuario;
    }

    public void setListaTiposUsuario(List<Tipousuario> listaTiposUsuario) {
        this.listaTiposUsuario = listaTiposUsuario;
    }

    public Tipousuario getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(Tipousuario tipousuario) {
        this.tipousuario = tipousuario;
    }

    public Integer getIdTipousuario() {
        return idTipousuario;
    }

    public void setIdTipousuario(Integer idTipousuario) {
        this.idTipousuario = idTipousuario;
    }

    public List<SelectItem> getLstTipoUsuario() throws Exception {
        this.lstTipoUsuario = new ArrayList<SelectItem>();
        listarTipousuario();
        lstTipoUsuario.clear();
        for (Tipousuario tipousuarios : listaTiposUsuario) {
            SelectItem tipoItem = new SelectItem(tipousuarios.getIdTipoUsuario(), tipousuarios.getDescripcion());
            this.lstTipoUsuario.add(tipoItem);
        }
        return lstTipoUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuario nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }

    
    
    
    //METODOS
    //metodo para listar los tipos de usuarios
    public void listarTipousuario() throws Exception {
        TipousuarioDAO dao;
        try {
            dao = new TipousuarioDAOImp();
            listaTiposUsuario = dao.listarTipousuario();
        } catch (Exception e) {
            throw e;
        }
    }

    public void prepararNuevoEmpleado() {
        empleado = new Empleado();
    }

    public void guardarEmpleado() {
        /*EmpleadoDAO eDao = new EmpleadoDAOImp();
         this.empleado.setEstado("VIGENTE");
         eDao.nuevoEmpleado(this.empleado);*/
        this.session = null;
        this.transaction = null;
        try {

            this.session = HibernateUtil.getSessionFactory().openSession();

            EmpleadoDAO eDao = new EmpleadoDAOImp();

            this.transaction = this.session.beginTransaction();

            this.empleado.setEstado("VIGENTE");

            eDao.guardarEmpleado(this.session, this.empleado);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Empleado registrado"));

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

    public void modificarEmpleado() {
        EmpleadoDAO eDao = new EmpleadoDAOImp();
        //this.empleado.setEstado("VIGENTE");
        eDao.actualizarEmpleado(this.empleado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Empleado modificado"));
    }

    public void eliminarEmpleado() {
        EmpleadoDAO eDao = new EmpleadoDAOImp();
        this.empleado.setEstado("ANULADO");
        eDao.actualizarEmpleado(this.empleado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Empleado eliminado"));
    }

    //metodo para determinar si un empleado ya cuenta con usuario en el sistema
    public void buscarUsuarioEmpleado() {
        this.session = null;
        this.transaction = null;
        //Integer idEmpleado = 0;
        try {
            //idEmpleado = this.empleado.getIdEmpleado();
            //f (idEmpleado > 0 || this.empleado != null) {
            
                this.session = HibernateUtil.getSessionFactory().openSession();
                UsuarioDAO uDao = new UsuarioDAOImp();
                this.transaction = this.session.beginTransaction();
                System.out.println("id empleado: " + this.empleado.getIdEmpleado());
                usuario = uDao.buscarUsuario(this.session, this.empleado.getIdEmpleado());
                if (usuario == null) {
                    //mostramos dialog para crear nuevo usuario
                    //prepararNuevoUsuario();
                    RequestContext contex = RequestContext.getCurrentInstance();
                    contex.execute("PF('dialogCrearUsuario').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El empleado ya tiene un usuario registrado"));
                    empleado = null;
                    usuario = null;

                }
                this.transaction.commit();
                //this.empleado = null;
                this.usuario = null;
                
            //}

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

    public void prepararNuevoUsuario() {
        nuevoUsuario = new Usuario();
    }

    public void guardarUsuario() {
        if (!this.rePassword.equals(this.nuevoUsuario.getPassword())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La contraseña no coincide, Usuario no registrado"));

        } else {
            this.session = null;
            this.transaction = null;
            try {

                this.session = HibernateUtil.getSessionFactory().openSession();

                //prepararNuevoUsuario();
                TipousuarioDAO tuDao = new TipousuarioDAOImp();
                UsuarioDAO uDao = new UsuarioDAOImp();

                this.transaction = this.session.beginTransaction();

                this.tipousuario = tuDao.buscarusuario(session, this.idTipousuario);
                nuevoUsuario.setTipousuario(this.tipousuario);
                nuevoUsuario.setEmpleado(this.empleado);

                //se encripta la contraseña
                StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
                s.setPassword("uniquekey");
                String passwd = s.encrypt(this.nuevoUsuario.getPassword());

                this.nuevoUsuario.setPassword(passwd);

                System.out.println("login " + this.nuevoUsuario.getLoginUsuario());
                System.out.println("passwd " + this.nuevoUsuario.getPassword());

                uDao.guardarUsuario(this.session, this.nuevoUsuario);

                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Usuario registrado"));
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
}

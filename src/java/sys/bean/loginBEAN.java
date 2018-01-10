
package sys.bean;

import java.awt.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import sys.dao.SucursalDAO;
import sys.dao.UsuarioDAO;
import sys.imp.SucursalDAOImp;
import sys.imp.UsuarioDAOImp;
import sys.model.Sucursal;
import sys.model.Usuario;


@ManagedBean
@SessionScoped
public class loginBEAN {

    private String loginUsuario;
    private String password;
    private Integer idSucursal;
    private Usuario usuario;
    private Sucursal sucursal;
    
    
    public loginBEAN() {
        this.usuario = new Usuario();
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
    
    
    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }
 
     
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }
   
    
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        String ruta = "";
        
        UsuarioDAO uDao = new UsuarioDAOImp();
        this.usuario = uDao.login(this.usuario, this.idSucursal);
        
        if(this.usuario != null ) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.usuario.getLoginUsuario());
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", this.usuario.getLoginUsuario());
            ruta = "/SysOvandos/faces/views/bienvenido.xhtml";
            
            SucursalDAO sDao = new SucursalDAOImp();
            this.sucursal = sDao.buscarSucursal(this.idSucursal);
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de acceso", "Usuario o Password son incorrectos");
            this.usuario = new Usuario();
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("ruta", ruta);
    }
    
    
    //metodo para cerrar la sesion
    public String cerrarSession()
    {
        this.loginUsuario = null;
        this.password = null;
        
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        httpSession.invalidate(); //para borrar la sesion
        return("/login");
    }
}

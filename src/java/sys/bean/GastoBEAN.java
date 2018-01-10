package sys.bean;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.GastoDAO;
import sys.imp.GastoDAOImp;
import sys.model.Gasto;
import sys.model.Usuario;

@ManagedBean
@ViewScoped
public class GastoBEAN {

    Session session = null;
    Transaction transaction = null;

    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;

    private List<Gasto> listaGastos;
    private Gasto gasto;
    private List<Gasto> listaFiltradaGastos;
    private Usuario usuario;

    public GastoBEAN() {
        gasto = new Gasto();
        usuario = new Usuario();
    }

    public loginBEAN getlBean() {
        return lBean;
    }

    public void setlBean(loginBEAN lBean) {
        this.lBean = lBean;
    }

    public List<Gasto> getListaGastos() {
        GastoDAO dao = new GastoDAOImp();
        listaGastos = dao.listarGastos();
        return listaGastos;
    }

    public void setListaGastos(List<Gasto> listaGastos) {
        this.listaGastos = listaGastos;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

    public List<Gasto> getListaFiltradaGastos() {
        return listaFiltradaGastos;
    }

    public void setListaFiltradaGastos(List<Gasto> listaFiltradaGastos) {
        this.listaFiltradaGastos = listaFiltradaGastos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //metodos
    //metodo para guardar el gasto
    public void guardarGasto() {
        //lBean.getUsuario().getIdUsuario()
        //System.out.println("id de usuario: "+lBean.getUsuario().getIdUsuario());
        int idusuario = lBean.getUsuario().getIdUsuario();
        this.usuario.setIdUsuario(idusuario);
        //java.sql.Date fechagasto = new java.sql.Date(new java.util.Date().getTime());

        GastoDAO gDao = new GastoDAOImp();
        this.gasto.setUsuario(this.usuario);
        //this.gasto.setFechaHoraRegistro(fechagasto);
        gDao.nuevoGasto(this.gasto);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Gasto registrada"));

    }

}

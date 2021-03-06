package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private Integer idUsuario;
     private Empleado empleado;
     private Tipousuario tipousuario;
     private String loginUsuario;
     private String password;
     private Set<Compra> compras = new HashSet<Compra>(0);
     private Set<Gasto> gastos = new HashSet<Gasto>(0);
     private Set<Preventa> preventas = new HashSet<Preventa>(0);
     private Set<Ventaservicio> ventaservicios = new HashSet<Ventaservicio>(0);
     private Set<Pagocompracredito> pagocompracreditos = new HashSet<Pagocompracredito>(0);
     private Set<Cronocomprascred> cronocomprascreds = new HashSet<Cronocomprascred>(0);
     private Set<Acceso> accesos = new HashSet<Acceso>(0);
     private Set<Venta> ventas = new HashSet<Venta>(0);
     private Set<Cotizacion> cotizacions = new HashSet<Cotizacion>(0);
     private Set<Articulo> articulos = new HashSet<Articulo>(0);
     private Set<Pagoventascredito> pagoventascreditos = new HashSet<Pagoventascredito>(0);
     private Set<Cronoventascredito> cronoventascreditos = new HashSet<Cronoventascredito>(0);

    public Usuario() {
    }

	
    public Usuario(Empleado empleado, Tipousuario tipousuario, String loginUsuario, String password) {
        this.empleado = empleado;
        this.tipousuario = tipousuario;
        this.loginUsuario = loginUsuario;
        this.password = password;
    }
    public Usuario(Empleado empleado, Tipousuario tipousuario, String loginUsuario, String password, Set<Compra> compras, Set<Gasto> gastos, Set<Preventa> preventas, Set<Ventaservicio> ventaservicios, Set<Pagocompracredito> pagocompracreditos, Set<Cronocomprascred> cronocomprascreds, Set<Acceso> accesos, Set<Venta> ventas, Set<Cotizacion> cotizacions, Set<Articulo> articulos, Set<Pagoventascredito> pagoventascreditos, Set<Cronoventascredito> cronoventascreditos) {
       this.empleado = empleado;
       this.tipousuario = tipousuario;
       this.loginUsuario = loginUsuario;
       this.password = password;
       this.compras = compras;
       this.gastos = gastos;
       this.preventas = preventas;
       this.ventaservicios = ventaservicios;
       this.pagocompracreditos = pagocompracreditos;
       this.cronocomprascreds = cronocomprascreds;
       this.accesos = accesos;
       this.ventas = ventas;
       this.cotizacions = cotizacions;
       this.articulos = articulos;
       this.pagoventascreditos = pagoventascreditos;
       this.cronoventascreditos = cronoventascreditos;
    }
   
    public Integer getIdUsuario() {
        return this.idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Empleado getEmpleado() {
        return this.empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Tipousuario getTipousuario() {
        return this.tipousuario;
    }
    
    public void setTipousuario(Tipousuario tipousuario) {
        this.tipousuario = tipousuario;
    }
    public String getLoginUsuario() {
        return this.loginUsuario;
    }
    
    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Compra> getCompras() {
        return this.compras;
    }
    
    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }
    public Set<Gasto> getGastos() {
        return this.gastos;
    }
    
    public void setGastos(Set<Gasto> gastos) {
        this.gastos = gastos;
    }
    public Set<Preventa> getPreventas() {
        return this.preventas;
    }
    
    public void setPreventas(Set<Preventa> preventas) {
        this.preventas = preventas;
    }
    public Set<Ventaservicio> getVentaservicios() {
        return this.ventaservicios;
    }
    
    public void setVentaservicios(Set<Ventaservicio> ventaservicios) {
        this.ventaservicios = ventaservicios;
    }
    public Set<Pagocompracredito> getPagocompracreditos() {
        return this.pagocompracreditos;
    }
    
    public void setPagocompracreditos(Set<Pagocompracredito> pagocompracreditos) {
        this.pagocompracreditos = pagocompracreditos;
    }
    public Set<Cronocomprascred> getCronocomprascreds() {
        return this.cronocomprascreds;
    }
    
    public void setCronocomprascreds(Set<Cronocomprascred> cronocomprascreds) {
        this.cronocomprascreds = cronocomprascreds;
    }
    public Set<Acceso> getAccesos() {
        return this.accesos;
    }
    
    public void setAccesos(Set<Acceso> accesos) {
        this.accesos = accesos;
    }
    public Set<Venta> getVentas() {
        return this.ventas;
    }
    
    public void setVentas(Set<Venta> ventas) {
        this.ventas = ventas;
    }
    public Set<Cotizacion> getCotizacions() {
        return this.cotizacions;
    }
    
    public void setCotizacions(Set<Cotizacion> cotizacions) {
        this.cotizacions = cotizacions;
    }
    public Set<Articulo> getArticulos() {
        return this.articulos;
    }
    
    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }
    public Set<Pagoventascredito> getPagoventascreditos() {
        return this.pagoventascreditos;
    }
    
    public void setPagoventascreditos(Set<Pagoventascredito> pagoventascreditos) {
        this.pagoventascreditos = pagoventascreditos;
    }
    public Set<Cronoventascredito> getCronoventascreditos() {
        return this.cronoventascreditos;
    }
    
    public void setCronoventascreditos(Set<Cronoventascredito> cronoventascreditos) {
        this.cronoventascreditos = cronoventascreditos;
    }




}



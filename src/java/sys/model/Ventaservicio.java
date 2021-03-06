package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * Ventaservicio generated by hbm2java
 */
public class Ventaservicio  implements java.io.Serializable {


     private Integer idVentaServicio;
     private Cliente cliente;
     private Servicio servicio;
     private Usuario usuario;
     private BigDecimal precio;
     private String detalle;
     private Date fechaServicio;
     private Integer idTipomoneda;
     private Integer idEmpleado;

    public Ventaservicio() {
    }

	
    public Ventaservicio(Cliente cliente, Servicio servicio, Usuario usuario, BigDecimal precio, Date fechaServicio) {
        this.cliente = cliente;
        this.servicio = servicio;
        this.usuario = usuario;
        this.precio = precio;
        this.fechaServicio = fechaServicio;
    }
    public Ventaservicio(Cliente cliente, Servicio servicio, Usuario usuario, BigDecimal precio, String detalle, Date fechaServicio, Integer idTipomoneda, Integer idEmpleado) {
       this.cliente = cliente;
       this.servicio = servicio;
       this.usuario = usuario;
       this.precio = precio;
       this.detalle = detalle;
       this.fechaServicio = fechaServicio;
       this.idTipomoneda = idTipomoneda;
       this.idEmpleado = idEmpleado;
    }
   
    public Integer getIdVentaServicio() {
        return this.idVentaServicio;
    }
    
    public void setIdVentaServicio(Integer idVentaServicio) {
        this.idVentaServicio = idVentaServicio;
    }
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Servicio getServicio() {
        return this.servicio;
    }
    
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public BigDecimal getPrecio() {
        return this.precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public String getDetalle() {
        return this.detalle;
    }
    
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    public Date getFechaServicio() {
        return this.fechaServicio;
    }
    
    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }
    public Integer getIdTipomoneda() {
        return this.idTipomoneda;
    }
    
    public void setIdTipomoneda(Integer idTipomoneda) {
        this.idTipomoneda = idTipomoneda;
    }
    public Integer getIdEmpleado() {
        return this.idEmpleado;
    }
    
    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }




}



package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Venta generated by hbm2java
 */
public class Venta  implements java.io.Serializable {


     private Integer numeroVenta;
     private Almacen almacen;
     private Cliente cliente;
     private Tipomoneda tipomoneda;
     private Tipopago tipopago;
     private Usuario usuario;
     private Long tiempoRegistro;
     private Date fechaVenta;
     private BigDecimal montoTotal;
     private BigDecimal tipoCambio;
     private Double impuesto;
     private String observaciones;
     private String estado;
     private Integer numeroCotizacion;
     private Date fechaHoraRegistro;
     private Integer numeroPreVenta;
     private Set<Ventafactura> ventafacturas = new HashSet<Ventafactura>(0);
     private Set<Cronoventascredito> cronoventascreditos = new HashSet<Cronoventascredito>(0);
     private Set<Detalleventa> detalleventas = new HashSet<Detalleventa>(0);
     private Set<Remito> remitos = new HashSet<Remito>(0);
     private Set<Devolucion> devolucions = new HashSet<Devolucion>(0);

    public Venta() {
    }

	
    public Venta(Cliente cliente, Tipomoneda tipomoneda, Tipopago tipopago, Usuario usuario, Date fechaVenta) {
        this.cliente = cliente;
        this.tipomoneda = tipomoneda;
        this.tipopago = tipopago;
        this.usuario = usuario;
        this.fechaVenta = fechaVenta;
    }
    public Venta(Almacen almacen, Cliente cliente, Tipomoneda tipomoneda, Tipopago tipopago, Usuario usuario, Long tiempoRegistro, Date fechaVenta, BigDecimal montoTotal, BigDecimal tipoCambio, Double impuesto, String observaciones, String estado, Integer numeroCotizacion, Date fechaHoraRegistro, Integer numeroPreVenta, Set<Ventafactura> ventafacturas, Set<Cronoventascredito> cronoventascreditos, Set<Detalleventa> detalleventas, Set<Remito> remitos, Set<Devolucion> devolucions) {
       this.almacen = almacen;
       this.cliente = cliente;
       this.tipomoneda = tipomoneda;
       this.tipopago = tipopago;
       this.usuario = usuario;
       this.tiempoRegistro = tiempoRegistro;
       this.fechaVenta = fechaVenta;
       this.montoTotal = montoTotal;
       this.tipoCambio = tipoCambio;
       this.impuesto = impuesto;
       this.observaciones = observaciones;
       this.estado = estado;
       this.numeroCotizacion = numeroCotizacion;
       this.fechaHoraRegistro = fechaHoraRegistro;
       this.numeroPreVenta = numeroPreVenta;
       this.ventafacturas = ventafacturas;
       this.cronoventascreditos = cronoventascreditos;
       this.detalleventas = detalleventas;
       this.remitos = remitos;
       this.devolucions = devolucions;
    }
   
    public Integer getNumeroVenta() {
        return this.numeroVenta;
    }
    
    public void setNumeroVenta(Integer numeroVenta) {
        this.numeroVenta = numeroVenta;
    }
    public Almacen getAlmacen() {
        return this.almacen;
    }
    
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Tipomoneda getTipomoneda() {
        return this.tipomoneda;
    }
    
    public void setTipomoneda(Tipomoneda tipomoneda) {
        this.tipomoneda = tipomoneda;
    }
    public Tipopago getTipopago() {
        return this.tipopago;
    }
    
    public void setTipopago(Tipopago tipopago) {
        this.tipopago = tipopago;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Long getTiempoRegistro() {
        return this.tiempoRegistro;
    }
    
    public void setTiempoRegistro(Long tiempoRegistro) {
        this.tiempoRegistro = tiempoRegistro;
    }
    public Date getFechaVenta() {
        return this.fechaVenta;
    }
    
    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    public BigDecimal getMontoTotal() {
        return this.montoTotal;
    }
    
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
    public BigDecimal getTipoCambio() {
        return this.tipoCambio;
    }
    
    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }
    public Double getImpuesto() {
        return this.impuesto;
    }
    
    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Integer getNumeroCotizacion() {
        return this.numeroCotizacion;
    }
    
    public void setNumeroCotizacion(Integer numeroCotizacion) {
        this.numeroCotizacion = numeroCotizacion;
    }
    public Date getFechaHoraRegistro() {
        return this.fechaHoraRegistro;
    }
    
    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
    public Integer getNumeroPreVenta() {
        return this.numeroPreVenta;
    }
    
    public void setNumeroPreVenta(Integer numeroPreVenta) {
        this.numeroPreVenta = numeroPreVenta;
    }
    public Set<Ventafactura> getVentafacturas() {
        return this.ventafacturas;
    }
    
    public void setVentafacturas(Set<Ventafactura> ventafacturas) {
        this.ventafacturas = ventafacturas;
    }
    public Set<Cronoventascredito> getCronoventascreditos() {
        return this.cronoventascreditos;
    }
    
    public void setCronoventascreditos(Set<Cronoventascredito> cronoventascreditos) {
        this.cronoventascreditos = cronoventascreditos;
    }
    public Set<Detalleventa> getDetalleventas() {
        return this.detalleventas;
    }
    
    public void setDetalleventas(Set<Detalleventa> detalleventas) {
        this.detalleventas = detalleventas;
    }
    public Set<Remito> getRemitos() {
        return this.remitos;
    }
    
    public void setRemitos(Set<Remito> remitos) {
        this.remitos = remitos;
    }
    public Set<Devolucion> getDevolucions() {
        return this.devolucions;
    }
    
    public void setDevolucions(Set<Devolucion> devolucions) {
        this.devolucions = devolucions;
    }




}



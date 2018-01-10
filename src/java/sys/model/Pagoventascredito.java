package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

/**
 * Pagoventascredito generated by hbm2java
 */
public class Pagoventascredito  implements java.io.Serializable {


     private Integer idPagoVentaCred;
     private Cronoventascredito cronoventascredito;
     private Usuario usuario;
     private BigDecimal montoPagado;
     private Date fechaPago;
     private BigDecimal saldo;
     private String observacion;

    public Pagoventascredito() {
    }

	
    public Pagoventascredito(Cronoventascredito cronoventascredito, Usuario usuario, BigDecimal montoPagado, Date fechaPago, BigDecimal saldo) {
        this.cronoventascredito = cronoventascredito;
        this.usuario = usuario;
        this.montoPagado = montoPagado;
        this.fechaPago = fechaPago;
        this.saldo = saldo;
    }
    public Pagoventascredito(Cronoventascredito cronoventascredito, Usuario usuario, BigDecimal montoPagado, Date fechaPago, BigDecimal saldo, String observacion) {
       this.cronoventascredito = cronoventascredito;
       this.usuario = usuario;
       this.montoPagado = montoPagado;
       this.fechaPago = fechaPago;
       this.saldo = saldo;
       this.observacion = observacion;
    }
   
    public Integer getIdPagoVentaCred() {
        return this.idPagoVentaCred;
    }
    
    public void setIdPagoVentaCred(Integer idPagoVentaCred) {
        this.idPagoVentaCred = idPagoVentaCred;
    }
    public Cronoventascredito getCronoventascredito() {
        return this.cronoventascredito;
    }
    
    public void setCronoventascredito(Cronoventascredito cronoventascredito) {
        this.cronoventascredito = cronoventascredito;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public BigDecimal getMontoPagado() {
        return this.montoPagado;
    }
    
    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }
    public Date getFechaPago() {
        return this.fechaPago;
    }
    
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    public BigDecimal getSaldo() {
        return this.saldo;
    }
    
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }




}



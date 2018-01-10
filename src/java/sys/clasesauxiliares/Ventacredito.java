
package sys.clasesauxiliares;

import java.math.BigDecimal;
import java.util.Date;


public class Ventacredito {
    private Integer numeroVenta;
    private String ciCliente;
    private Integer idCronoV;
    private Integer idPagoVentaCred;
    private Date fechaVenta;
    private BigDecimal montoVenta;
    private BigDecimal montoPagado;
    private BigDecimal saldo;

    public Ventacredito() {
    }

    public Ventacredito(Integer numeroVenta, String ciCliente, Integer idCronoV, Integer idPagoVentaCred, Date fechaVenta, BigDecimal montoVenta, BigDecimal montoPagado, BigDecimal saldo) {
        this.numeroVenta = numeroVenta;
        this.ciCliente = ciCliente;
        this.idCronoV = idCronoV;
        this.idPagoVentaCred = idPagoVentaCred;
        this.fechaVenta = fechaVenta;
        this.montoVenta = montoVenta;
        this.montoPagado = montoPagado;
        this.saldo = saldo;
    }

    public Integer getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(Integer numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public Integer getIdCronoV() {
        return idCronoV;
    }

    public void setIdCronoV(Integer idCronoV) {
        this.idCronoV = idCronoV;
    }

    public Integer getIdPagoVentaCred() {
        return idPagoVentaCred;
    }

    public void setIdPagoVentaCred(Integer idPagoVentaCred) {
        this.idPagoVentaCred = idPagoVentaCred;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getMontoVenta() {
        return montoVenta;
    }

    public void setMontoVenta(BigDecimal montoVenta) {
        this.montoVenta = montoVenta;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    
}

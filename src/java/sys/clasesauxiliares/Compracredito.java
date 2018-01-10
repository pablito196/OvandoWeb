package sys.clasesauxiliares;

import java.math.BigDecimal;
import java.util.Date;

public class Compracredito {
    private Integer idCompra;
    private Integer idProveedor;
    private Integer idCronogramaC;
    private Integer idPagocredito;
    private Date fechaCompra;
    private BigDecimal montoCompra;
    private BigDecimal montoPagado;
    private BigDecimal saldo;

    public Compracredito() {
    }

    public Compracredito(Integer idCompra, Integer idProveedor, Integer idCronogramaC, Integer idPagocredito, Date fechaCompra, BigDecimal montoCompra, BigDecimal montoPagado, BigDecimal saldo) {
        this.idCompra = idCompra;
        this.idProveedor = idProveedor;
        this.idCronogramaC = idCronogramaC;
        this.idPagocredito = idPagocredito;
        this.fechaCompra = fechaCompra;
        this.montoCompra = montoCompra;
        this.montoPagado = montoPagado;
        this.saldo = saldo;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdCronogramaC() {
        return idCronogramaC;
    }

    public void setIdCronogramaC(Integer idCronogramaC) {
        this.idCronogramaC = idCronogramaC;
    }

    public Integer getIdPagocredito() {
        return idPagocredito;
    }

    public void setIdPagocredito(Integer idPagocredito) {
        this.idPagocredito = idPagocredito;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public BigDecimal getMontoCompra() {
        return montoCompra;
    }

    public void setMontoCompra(BigDecimal montoCompra) {
        this.montoCompra = montoCompra;
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

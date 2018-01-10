
package sys.clasesauxiliares;

import java.math.BigDecimal;
import java.util.Date;
import sys.model.Cliente;
import sys.model.Venta;

public class VentaFormateada {
    private Integer numeroVenta;
    private Integer numeroPreventa;
    private String ciCliente;
    private String nombresCliente;
    private String tipoVenta;
    private BigDecimal tipoCambio;
    private BigDecimal total;
    //private Venta venta;
    private BigDecimal saldo;
    private String facturas;
    private Date fechaVenta;
    private String estado;
    private String observaciones;
    private String sucursal;
    private Integer idSucursal;

    public VentaFormateada() {
    }

    public VentaFormateada(Integer numeroVenta, Integer numeroPreventa, String ciCliente, String nombresCliente, String tipoVenta, BigDecimal tipoCambio, BigDecimal total, BigDecimal saldo, String facturas, Date fechaVenta, String estado, String observaciones, String sucursal, Integer idSucursal) {
        this.numeroVenta = numeroVenta;
        this.numeroPreventa = numeroPreventa;
        this.ciCliente = ciCliente;
        this.nombresCliente = nombresCliente;
        this.tipoVenta = tipoVenta;
        this.tipoCambio = tipoCambio;
        this.total = total;
        this.saldo = saldo;
        this.facturas = facturas;
        this.fechaVenta = fechaVenta;
        this.estado = estado;
        this.observaciones = observaciones;
        this.sucursal = sucursal;
        this.idSucursal = idSucursal;
    }

       
    public Integer getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(Integer numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public Integer getNumeroPreventa() {
        return numeroPreventa;
    }

    public void setNumeroPreventa(Integer numeroPreventa) {
        this.numeroPreventa = numeroPreventa;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    /*public VentaFormateada(Venta venta, BigDecimal saldo, String facturas) {
    this.venta = venta;
    this.saldo = saldo;
    this.facturas = facturas;
    }
    public Venta getVenta() {
    return venta;
    }
    public void setVenta(Venta venta) {
    this.venta = venta;
    }*/
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

   public String getFacturas() {
        return facturas;
    }

    public void setFacturas(String facturas) {
        this.facturas = facturas;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    
}

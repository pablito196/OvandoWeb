
package sys.clasesauxiliares;

import java.math.BigDecimal;
import java.util.Date;


public class LibroVentas {
    private Integer numero;
    private Date fechaFactura;
    private Integer numeroFactura;
    private String numeroAutorizacion;
    private char estado;
    private String ciCliente;
    private String razonSocial;
    private BigDecimal importeTotalVenta;
    private BigDecimal ICE;
    private BigDecimal exportaciones;
    private BigDecimal ventasGravadasTasaCero;
    private BigDecimal subTotal;
    private BigDecimal descuentos;
    private BigDecimal importeBaseDebitoFiscal;
    private BigDecimal debitoFiscal;
    private String codigoControl;

    public LibroVentas() {
    }

    public LibroVentas(Integer numero, Date fechaFactura, Integer numeroFactura, String numeroAutorizacion, char estado, String ciCliente, String razonSocial, BigDecimal importeTotalVenta, BigDecimal ICE, BigDecimal exportaciones, BigDecimal ventasGravadasTasaCero, BigDecimal subTotal, BigDecimal descuentos, BigDecimal importeBaseDebitoFiscal, BigDecimal debitoFiscal, String codigoControl) {
        this.numero = numero;
        this.fechaFactura = fechaFactura;
        this.numeroFactura = numeroFactura;
        this.numeroAutorizacion = numeroAutorizacion;
        this.estado = estado;
        this.ciCliente = ciCliente;
        this.razonSocial = razonSocial;
        this.importeTotalVenta = importeTotalVenta;
        this.ICE = ICE;
        this.exportaciones = exportaciones;
        this.ventasGravadasTasaCero = ventasGravadasTasaCero;
        this.subTotal = subTotal;
        this.descuentos = descuentos;
        this.importeBaseDebitoFiscal = importeBaseDebitoFiscal;
        this.debitoFiscal = debitoFiscal;
        this.codigoControl = codigoControl;
    }
    
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public BigDecimal getImporteTotalVenta() {
        return importeTotalVenta;
    }

    public void setImporteTotalVenta(BigDecimal importeTotalVenta) {
        this.importeTotalVenta = importeTotalVenta;
    }

    public BigDecimal getICE() {
        return ICE;
    }

    public void setICE(BigDecimal ICE) {
        this.ICE = ICE;
    }

    public BigDecimal getExportaciones() {
        return exportaciones;
    }

    public void setExportaciones(BigDecimal exportaciones) {
        this.exportaciones = exportaciones;
    }

    public BigDecimal getVentasGravadasTasaCero() {
        return ventasGravadasTasaCero;
    }

    public void setVentasGravadasTasaCero(BigDecimal ventasGravadasTasaCero) {
        this.ventasGravadasTasaCero = ventasGravadasTasaCero;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public BigDecimal getImporteBaseDebitoFiscal() {
        return importeBaseDebitoFiscal;
    }

    public void setImporteBaseDebitoFiscal(BigDecimal importeBaseDebitoFiscal) {
        this.importeBaseDebitoFiscal = importeBaseDebitoFiscal;
    }

    public BigDecimal getDebitoFiscal() {
        return debitoFiscal;
    }

    public void setDebitoFiscal(BigDecimal debitoFiscal) {
        this.debitoFiscal = debitoFiscal;
    }

    public String getCodigoControl() {
        return codigoControl;
    }

    public void setCodigoControl(String codigoControl) {
        this.codigoControl = codigoControl;
    }
    
    
}

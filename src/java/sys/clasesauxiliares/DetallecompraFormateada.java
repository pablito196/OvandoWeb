
package sys.clasesauxiliares;

import java.math.BigDecimal;


public class DetallecompraFormateada {
    private Integer idDetalleCompra;
    private Integer idCompra;
    private String codigoArticulo;
    private String nombreArticulo;        
    private String descripcion;
    private float cantidad;
     private BigDecimal costoUnitario;
     private BigDecimal costoTotal;
     
     public DetallecompraFormateada() {
    }
     
     public DetallecompraFormateada(Integer idDetalleCompra, Integer idCompra, String codigoArticulo, String nombreArticulo, String descripcion, float cantidad, BigDecimal costoUnitario, BigDecimal costoTotal) {
       this.idDetalleCompra = idDetalleCompra;
       this.idCompra = idCompra;
       this.codigoArticulo = codigoArticulo;
       this.nombreArticulo = nombreArticulo;
       this.descripcion = descripcion;
       this.cantidad = cantidad;
       this.costoUnitario = costoUnitario;
       this.costoTotal = costoTotal;
    }

    public Integer getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(Integer idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }
     
     
}

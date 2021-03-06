package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * Detalleventa generated by hbm2java
 */
public class Detalleventa  implements java.io.Serializable {


     private Integer idDetalleVenta;
     private Articulo articulo;
     private Venta venta;
     private BigDecimal precioVentaReal;
     private float cantidad;
     private BigDecimal total;
     private Integer idFactura;

    public Detalleventa() {
    }

	
    public Detalleventa(Articulo articulo, Venta venta, BigDecimal precioVentaReal, float cantidad, BigDecimal total) {
        this.articulo = articulo;
        this.venta = venta;
        this.precioVentaReal = precioVentaReal;
        this.cantidad = cantidad;
        this.total = total;
    }
    public Detalleventa(Articulo articulo, Venta venta, BigDecimal precioVentaReal, float cantidad, BigDecimal total, Integer idFactura) {
       this.articulo = articulo;
       this.venta = venta;
       this.precioVentaReal = precioVentaReal;
       this.cantidad = cantidad;
       this.total = total;
       this.idFactura = idFactura;
    }
   
    public Integer getIdDetalleVenta() {
        return this.idDetalleVenta;
    }
    
    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }
    public Articulo getArticulo() {
        return this.articulo;
    }
    
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    public Venta getVenta() {
        return this.venta;
    }
    
    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    public BigDecimal getPrecioVentaReal() {
        return this.precioVentaReal;
    }
    
    public void setPrecioVentaReal(BigDecimal precioVentaReal) {
        this.precioVentaReal = precioVentaReal;
    }
    public float getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getTotal() {
        return this.total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public Integer getIdFactura() {
        return this.idFactura;
    }
    
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }




}



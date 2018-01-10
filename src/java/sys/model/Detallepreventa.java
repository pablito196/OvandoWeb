package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * Detallepreventa generated by hbm2java
 */
public class Detallepreventa  implements java.io.Serializable {


     private Integer idDetallePreventa;
     private Articulo articulo;
     private Preventa preventa;
     private BigDecimal precioVentaReal;
     private float cantidad;
     private BigDecimal total;
     private Integer idFactura;

    public Detallepreventa() {
    }

	
    public Detallepreventa(Articulo articulo, Preventa preventa, BigDecimal precioVentaReal, float cantidad, BigDecimal total) {
        this.articulo = articulo;
        this.preventa = preventa;
        this.precioVentaReal = precioVentaReal;
        this.cantidad = cantidad;
        this.total = total;
    }
    public Detallepreventa(Articulo articulo, Preventa preventa, BigDecimal precioVentaReal, float cantidad, BigDecimal total, Integer idFactura) {
       this.articulo = articulo;
       this.preventa = preventa;
       this.precioVentaReal = precioVentaReal;
       this.cantidad = cantidad;
       this.total = total;
       this.idFactura = idFactura;
    }
   
    public Integer getIdDetallePreventa() {
        return this.idDetallePreventa;
    }
    
    public void setIdDetallePreventa(Integer idDetallePreventa) {
        this.idDetallePreventa = idDetallePreventa;
    }
    public Articulo getArticulo() {
        return this.articulo;
    }
    
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    public Preventa getPreventa() {
        return this.preventa;
    }
    
    public void setPreventa(Preventa preventa) {
        this.preventa = preventa;
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


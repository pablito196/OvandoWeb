package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Salida generated by hbm2java
 */
public class Salida  implements java.io.Serializable {


     private Integer idSalida;
     private Articulo articulo;
     private float cantidad;
     private Date fechaSalida;
     private String observacion;

    public Salida() {
    }

	
    public Salida(Articulo articulo, float cantidad, Date fechaSalida) {
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaSalida = fechaSalida;
    }
    public Salida(Articulo articulo, float cantidad, Date fechaSalida, String observacion) {
       this.articulo = articulo;
       this.cantidad = cantidad;
       this.fechaSalida = fechaSalida;
       this.observacion = observacion;
    }
   
    public Integer getIdSalida() {
        return this.idSalida;
    }
    
    public void setIdSalida(Integer idSalida) {
        this.idSalida = idSalida;
    }
    public Articulo getArticulo() {
        return this.articulo;
    }
    
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    public float getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    public Date getFechaSalida() {
        return this.fechaSalida;
    }
    
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }




}


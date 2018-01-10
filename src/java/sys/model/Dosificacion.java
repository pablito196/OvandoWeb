package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Dosificacion generated by hbm2java
 */
public class Dosificacion  implements java.io.Serializable {


     private String numeroAutorizacion;
     private Sucursal sucursal;
     private int numeroInicialFactura;
     private Date fechaLimiteEmision;
     private String llaveDosificacion;
     private int tiempoAlerta;
     private short estado;
     private Integer inicialActivo;
     private String texto;

    public Dosificacion() {
    }

	
    public Dosificacion(String numeroAutorizacion, int numeroInicialFactura, Date fechaLimiteEmision, String llaveDosificacion, int tiempoAlerta, short estado) {
        this.numeroAutorizacion = numeroAutorizacion;
        this.numeroInicialFactura = numeroInicialFactura;
        this.fechaLimiteEmision = fechaLimiteEmision;
        this.llaveDosificacion = llaveDosificacion;
        this.tiempoAlerta = tiempoAlerta;
        this.estado = estado;
    }
    public Dosificacion(String numeroAutorizacion, Sucursal sucursal, int numeroInicialFactura, Date fechaLimiteEmision, String llaveDosificacion, int tiempoAlerta, short estado, Integer inicialActivo, String texto) {
       this.numeroAutorizacion = numeroAutorizacion;
       this.sucursal = sucursal;
       this.numeroInicialFactura = numeroInicialFactura;
       this.fechaLimiteEmision = fechaLimiteEmision;
       this.llaveDosificacion = llaveDosificacion;
       this.tiempoAlerta = tiempoAlerta;
       this.estado = estado;
       this.inicialActivo = inicialActivo;
       this.texto = texto;
    }
   
    public String getNumeroAutorizacion() {
        return this.numeroAutorizacion;
    }
    
    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }
    public Sucursal getSucursal() {
        return this.sucursal;
    }
    
    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
    public int getNumeroInicialFactura() {
        return this.numeroInicialFactura;
    }
    
    public void setNumeroInicialFactura(int numeroInicialFactura) {
        this.numeroInicialFactura = numeroInicialFactura;
    }
    public Date getFechaLimiteEmision() {
        return this.fechaLimiteEmision;
    }
    
    public void setFechaLimiteEmision(Date fechaLimiteEmision) {
        this.fechaLimiteEmision = fechaLimiteEmision;
    }
    public String getLlaveDosificacion() {
        return this.llaveDosificacion;
    }
    
    public void setLlaveDosificacion(String llaveDosificacion) {
        this.llaveDosificacion = llaveDosificacion;
    }
    public int getTiempoAlerta() {
        return this.tiempoAlerta;
    }
    
    public void setTiempoAlerta(int tiempoAlerta) {
        this.tiempoAlerta = tiempoAlerta;
    }
    public short getEstado() {
        return this.estado;
    }
    
    public void setEstado(short estado) {
        this.estado = estado;
    }
    public Integer getInicialActivo() {
        return this.inicialActivo;
    }
    
    public void setInicialActivo(Integer inicialActivo) {
        this.inicialActivo = inicialActivo;
    }
    public String getTexto() {
        return this.texto;
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }




}



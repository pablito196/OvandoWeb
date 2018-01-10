package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Servicio generated by hbm2java
 */
public class Servicio  implements java.io.Serializable {


     private Integer idServicio;
     private String nombreServicio;
     private Set<Ventaservicio> ventaservicios = new HashSet<Ventaservicio>(0);

    public Servicio() {
    }

	
    public Servicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }
    public Servicio(String nombreServicio, Set<Ventaservicio> ventaservicios) {
       this.nombreServicio = nombreServicio;
       this.ventaservicios = ventaservicios;
    }
   
    public Integer getIdServicio() {
        return this.idServicio;
    }
    
    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }
    public String getNombreServicio() {
        return this.nombreServicio;
    }
    
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }
    public Set<Ventaservicio> getVentaservicios() {
        return this.ventaservicios;
    }
    
    public void setVentaservicios(Set<Ventaservicio> ventaservicios) {
        this.ventaservicios = ventaservicios;
    }




}



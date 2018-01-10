package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Unidad generated by hbm2java
 */
public class Unidad  implements java.io.Serializable {


     private Integer idUnidad;
     private String nombreUnidad;
     private String sigla;
     private String estado;
     private Set<Articulo> articulos = new HashSet<Articulo>(0);

    public Unidad() {
    }

	
    public Unidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }
    public Unidad(String nombreUnidad, String sigla, String estado, Set<Articulo> articulos) {
       this.nombreUnidad = nombreUnidad;
       this.sigla = sigla;
       this.estado = estado;
       this.articulos = articulos;
    }
   
    public Integer getIdUnidad() {
        return this.idUnidad;
    }
    
    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }
    public String getNombreUnidad() {
        return this.nombreUnidad;
    }
    
    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }
    public String getSigla() {
        return this.sigla;
    }
    
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Set<Articulo> getArticulos() {
        return this.articulos;
    }
    
    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }




}



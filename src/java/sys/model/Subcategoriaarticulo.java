package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Subcategoriaarticulo generated by hbm2java
 */
public class Subcategoriaarticulo  implements java.io.Serializable {


     private Integer idSubcategoria;
     private Categoriaarticulo categoriaarticulo;
     private String nombreSubCategoria;
     private String descripcion;
     private Set<Articulo> articulos = new HashSet<Articulo>(0);

    public Subcategoriaarticulo() {
    }

	
    public Subcategoriaarticulo(Categoriaarticulo categoriaarticulo) {
        this.categoriaarticulo = categoriaarticulo;
    }
    public Subcategoriaarticulo(Categoriaarticulo categoriaarticulo, String nombreSubCategoria, String descripcion, Set<Articulo> articulos) {
       this.categoriaarticulo = categoriaarticulo;
       this.nombreSubCategoria = nombreSubCategoria;
       this.descripcion = descripcion;
       this.articulos = articulos;
    }
   
    public Integer getIdSubcategoria() {
        return this.idSubcategoria;
    }
    
    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }
    public Categoriaarticulo getCategoriaarticulo() {
        return this.categoriaarticulo;
    }
    
    public void setCategoriaarticulo(Categoriaarticulo categoriaarticulo) {
        this.categoriaarticulo = categoriaarticulo;
    }
    public String getNombreSubCategoria() {
        return this.nombreSubCategoria;
    }
    
    public void setNombreSubCategoria(String nombreSubCategoria) {
        this.nombreSubCategoria = nombreSubCategoria;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set<Articulo> getArticulos() {
        return this.articulos;
    }
    
    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }




}



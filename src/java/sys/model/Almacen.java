package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Almacen generated by hbm2java
 */
public class Almacen  implements java.io.Serializable {


     private Integer idAlmacen;
     private Sucursal sucursal;
     private String nombreAlmacen;
     private Boolean principal;
     private String estadoAlmacen;
     private Set<Existencia> existencias = new HashSet<Existencia>(0);
     private Set<Compra> compras = new HashSet<Compra>(0);
     private Set<Estante> estantes = new HashSet<Estante>(0);
     private Set<Preventa> preventas = new HashSet<Preventa>(0);
     private Set<Venta> ventas = new HashSet<Venta>(0);

    public Almacen() {
    }

	
    public Almacen(Sucursal sucursal, String nombreAlmacen) {
        this.sucursal = sucursal;
        this.nombreAlmacen = nombreAlmacen;
    }
    public Almacen(Sucursal sucursal, String nombreAlmacen, Boolean principal, String estadoAlmacen, Set<Existencia> existencias, Set<Compra> compras, Set<Estante> estantes, Set<Preventa> preventas, Set<Venta> ventas) {
       this.sucursal = sucursal;
       this.nombreAlmacen = nombreAlmacen;
       this.principal = principal;
       this.estadoAlmacen = estadoAlmacen;
       this.existencias = existencias;
       this.compras = compras;
       this.estantes = estantes;
       this.preventas = preventas;
       this.ventas = ventas;
    }
   
    public Integer getIdAlmacen() {
        return this.idAlmacen;
    }
    
    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
    public Sucursal getSucursal() {
        return this.sucursal;
    }
    
    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
    public String getNombreAlmacen() {
        return this.nombreAlmacen;
    }
    
    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }
    public Boolean getPrincipal() {
        return this.principal;
    }
    
    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
    public String getEstadoAlmacen() {
        return this.estadoAlmacen;
    }
    
    public void setEstadoAlmacen(String estadoAlmacen) {
        this.estadoAlmacen = estadoAlmacen;
    }
    public Set<Existencia> getExistencias() {
        return this.existencias;
    }
    
    public void setExistencias(Set<Existencia> existencias) {
        this.existencias = existencias;
    }
    public Set<Compra> getCompras() {
        return this.compras;
    }
    
    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }
    public Set<Estante> getEstantes() {
        return this.estantes;
    }
    
    public void setEstantes(Set<Estante> estantes) {
        this.estantes = estantes;
    }
    public Set<Preventa> getPreventas() {
        return this.preventas;
    }
    
    public void setPreventas(Set<Preventa> preventas) {
        this.preventas = preventas;
    }
    public Set<Venta> getVentas() {
        return this.ventas;
    }
    
    public void setVentas(Set<Venta> ventas) {
        this.ventas = ventas;
    }




}


package sys.model;
// Generated 21-jul-2016 11:11:08 by Hibernate Tools 4.3.1



/**
 * Ingreso generated by hbm2java
 */
public class Ingreso  implements java.io.Serializable {


     private Integer idIngreso;
     private Empleado empleado;
     private Sucursal sucursal;

    public Ingreso() {
    }

    public Ingreso(Empleado empleado, Sucursal sucursal) {
       this.empleado = empleado;
       this.sucursal = sucursal;
    }
   
    public Integer getIdIngreso() {
        return this.idIngreso;
    }
    
    public void setIdIngreso(Integer idIngreso) {
        this.idIngreso = idIngreso;
    }
    public Empleado getEmpleado() {
        return this.empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Sucursal getSucursal() {
        return this.sucursal;
    }
    
    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }




}


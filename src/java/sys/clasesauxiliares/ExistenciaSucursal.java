
package sys.clasesauxiliares;

public class ExistenciaSucursal {
    private String nombreSucursal;
    private double cantidad;

    public ExistenciaSucursal() {
    }

    public ExistenciaSucursal(String nombreSucursal, double cantidad) {
        this.nombreSucursal = nombreSucursal;
        this.cantidad = cantidad;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    
}

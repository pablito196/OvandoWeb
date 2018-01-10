package sys.clasesauxiliares;

public class CuentaBancariaFormateada {
    
    Integer idCuentaBancaria;
    Integer idBanco;
    String nombreBanco;
    Integer idTipomoneda;
    String nombreMoneda;
    String numeroCuenta;
    Integer idProveedor;
    String nombreProveedor;

    public CuentaBancariaFormateada(Integer idCuentaBancaria, Integer idBanco, String nombreBanco, Integer idTipomoneda, String nombreMoneda, String numeroCuenta, Integer idProveedor, String nombreProveedor) {
        this.idCuentaBancaria = idCuentaBancaria;
        this.idBanco = idBanco;
        this.nombreBanco = nombreBanco;
        this.idTipomoneda = idTipomoneda;
        this.nombreMoneda = nombreMoneda;
        this.numeroCuenta = numeroCuenta;
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
    }
    
    
    public CuentaBancariaFormateada()
    {
    }

    public Integer getIdCuentaBancaria() {
        return idCuentaBancaria;
    }

    public void setIdCuentaBancaria(Integer idCuentaBancaria) {
        this.idCuentaBancaria = idCuentaBancaria;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public Integer getIdTipomoneda() {
        return idTipomoneda;
    }

    public void setIdTipomoneda(Integer idTipomoneda) {
        this.idTipomoneda = idTipomoneda;
    }

    public String getNombreMoneda() {
        return nombreMoneda;
    }

    public void setNombreMoneda(String nombreMoneda) {
        this.nombreMoneda = nombreMoneda;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
    
    
    
}

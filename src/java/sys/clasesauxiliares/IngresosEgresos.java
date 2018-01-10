package sys.clasesauxiliares;

import java.math.BigDecimal;
import java.util.Date;

public class IngresosEgresos implements Comparable<IngresosEgresos>{
    private Date fecha;
    private String concepto;
    private BigDecimal ventas;
    private BigDecimal cobros;
    private BigDecimal totalIngresos;
    private BigDecimal compras;
    private BigDecimal gastos;
    private BigDecimal totalEgresos;

    public IngresosEgresos() {
    }

    public IngresosEgresos(Date fecha, String concepto, BigDecimal ventas, BigDecimal cobros, BigDecimal totalIngresos, BigDecimal compras, BigDecimal gastos, BigDecimal totalEgresos) {
        this.fecha = fecha;
        this.concepto = concepto;
        this.ventas = ventas;
        this.cobros = cobros;
        this.totalIngresos = totalIngresos;
        this.compras = compras;
        this.gastos = gastos;
        this.totalEgresos = totalEgresos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getVentas() {
        return ventas;
    }

    public void setVentas(BigDecimal ventas) {
        this.ventas = ventas;
    }

    public BigDecimal getCobros() {
        return cobros;
    }

    public void setCobros(BigDecimal cobros) {
        this.cobros = cobros;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public BigDecimal getCompras() {
        return compras;
    }

    public void setCompras(BigDecimal compras) {
        this.compras = compras;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public BigDecimal getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(BigDecimal totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    @Override
    public int compareTo(IngresosEgresos o) {
        String a = new String(String.valueOf(this.getFecha()));
        String b = new String(String.valueOf(o.getFecha()));
        return b.compareTo(a);
    }
    
    
}

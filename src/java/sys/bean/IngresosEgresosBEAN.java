package sys.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;
import sys.clasesauxiliares.IngresosEgresos;
import sys.clasesauxiliares.IngresosEgresosDetallado;
import sys.clasesauxiliares.ProveedorDatos;
import sys.dao.ComprasDAO;
import sys.dao.GastoDAO;
import sys.dao.PagocompracreditoDAO;
import sys.dao.PagoventascreditoDAO;
import sys.dao.VentaDAO;
import sys.imp.ComprasDAOImp;
import sys.imp.GastoDAOImp;
import sys.imp.PagocompracreditoDAOImp;
import sys.imp.PagoventascreditoDAOImp;
import sys.imp.VentaDAOImp;
import sys.model.Compra;
import sys.model.Gasto;
import sys.model.Pagocompracredito;
import sys.model.Pagoventascredito;
import sys.model.Venta;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class IngresosEgresosBEAN {

    Session session = null;
    Transaction transaction = null;

    private Date fechaInicio;
    private Date fechaFin;

    private BigDecimal totalVentas;
    private BigDecimal totalCobros;
    private BigDecimal totalIngresos;
    private BigDecimal totalCompras;
    private BigDecimal totalGastos;
    private BigDecimal totalEgresos;
    private BigDecimal totalEfectivo;
    
    private Integer idSucursal;

    private List<IngresosEgresos> lstIngresosEgresos;

    public IngresosEgresosBEAN() {
        this.lstIngresosEgresos = new ArrayList<>();
        this.totalVentas = new BigDecimal(0);
        this.totalCobros = new BigDecimal(0);
        this.totalIngresos = new BigDecimal(0);
        this.totalCompras = new BigDecimal(0);
        this.totalGastos = new BigDecimal(0);
        this.totalEgresos = new BigDecimal(0);
        this.totalEfectivo = new BigDecimal(0);
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(BigDecimal totalVentas) {
        this.totalVentas = totalVentas;
    }

    public BigDecimal getTotalCobros() {
        return totalCobros;
    }

    public void setTotalCobros(BigDecimal totalCobros) {
        this.totalCobros = totalCobros;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public BigDecimal getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(BigDecimal totalCompras) {
        this.totalCompras = totalCompras;
    }

    public BigDecimal getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(BigDecimal totalGastos) {
        this.totalGastos = totalGastos;
    }

    public BigDecimal getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(BigDecimal totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    public BigDecimal getTotalEfectivo() {
        return totalEfectivo;
    }

    public void setTotalEfectivo(BigDecimal totalEfectivo) {
        this.totalEfectivo = totalEfectivo;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public List<IngresosEgresos> getLstIngresosEgresos() {
        return lstIngresosEgresos;
    }

    public void setLstIngresosEgresos(List<IngresosEgresos> lstIngresosEgresos) {
        this.lstIngresosEgresos = lstIngresosEgresos;
    }

    //METODOS
    public void buscar() {
        this.lstIngresosEgresos = new ArrayList<>();
        VentaDAO vDao = new VentaDAOImp();
        PagoventascreditoDAO pvcDao = new PagoventascreditoDAOImp();
        ComprasDAO cDao = new ComprasDAOImp();
        PagocompracreditoDAO pccDao = new PagocompracreditoDAOImp();
        GastoDAO gDao = new GastoDAOImp();

        this.session = null;
        this.transaction = null;

        try {

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            List<Venta> lista = new ArrayList<>();
            List<Pagoventascredito> listaPvc = new ArrayList<>();
            List<Compra> listaC = new ArrayList<>();
            List<Pagocompracredito> listaPcc = new ArrayList<>();
            List<Gasto> listaG = new ArrayList<>();

            lista = vDao.listarVentasPorFecha(this.session, this.fechaInicio, this.fechaFin);
            String concepto = "";
            for (Venta item : lista) {
                if (item.getNumeroPreVenta() > 0) {
                    concepto = "Venta Nro. " + item.getNumeroVenta() + " Pre. Venta Nro. " + item.getNumeroPreVenta() + " Cliente: " + item.getCliente().getNombresCliente();
                } else {
                    concepto = "Venta Nro. " + item.getNumeroVenta() + " Cliente: " + item.getCliente().getNombresCliente();
                }
                this.lstIngresosEgresos.add(new IngresosEgresos(item.getFechaVenta(), concepto, item.getMontoTotal(), new BigDecimal(0), item.getMontoTotal(), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)));
            }
            listaPvc = pvcDao.listarPagosPorFechas(this.session, this.fechaInicio, this.fechaFin);
            for (Pagoventascredito item : listaPvc) {
                concepto = "Crédito Nro. " + item.getCronoventascredito().getVenta().getNumeroVenta() + " Cliente: " + item.getCronoventascredito().getVenta().getCliente().getNombresCliente();
                this.lstIngresosEgresos.add(new IngresosEgresos(item.getFechaPago(), concepto, new BigDecimal(0), item.getMontoPagado(), item.getMontoPagado(), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)));
            }
            listaC = cDao.listarComprasPorFecha(this.session, this.fechaInicio, this.fechaFin);
            for (Compra item : listaC) {
                concepto = "Compra Nro. " + item.getIdCompra() + " Proveedor: " + item.getProveedor().getNombre();
                this.lstIngresosEgresos.add(new IngresosEgresos(item.getFechaCompra(), concepto, new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), item.getMontoCompra(), new BigDecimal(0), item.getMontoCompra()));
            }
            listaPcc = pccDao.listarPagosPorFechas(this.session, this.fechaInicio, this.fechaFin);
            for (Pagocompracredito item : listaPcc) {
                concepto = "Compra al crédito Nro. " + item.getCronocomprascred().getCompra().getIdCompra() + " Proveedor: " + item.getCronocomprascred().getCompra().getProveedor().getNombre();
                this.lstIngresosEgresos.add(new IngresosEgresos(item.getFechaPago(), concepto, new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), item.getMontoPagado(), new BigDecimal(0), item.getMontoPagado()));
            }
            listaG = gDao.listarGastosPorFechas(this.session, this.fechaInicio, this.fechaFin);
            for (Gasto item : listaG) {
                concepto = "Gasto Nro. " + item.getIdGasto() + " Concepto: " + item.getConcepto();
                this.lstIngresosEgresos.add(new IngresosEgresos(item.getFechaHoraRegistro(), concepto, new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), item.getMonto(), item.getMonto()));
            }
            calcularTotales();
            this.transaction.commit();
            Collections.sort(this.lstIngresosEgresos);
            /*this.fechaInicio = null;
             this.fechaFin = null;*/
        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    //metodo para calcular totales de ingresos y egresos

    public void calcularTotales() {
        this.totalVentas = new BigDecimal(0);
        this.totalCobros = new BigDecimal(0);
        this.totalIngresos = new BigDecimal(0);
        this.totalCompras = new BigDecimal(0);
        this.totalGastos = new BigDecimal(0);
        this.totalEgresos = new BigDecimal(0);
        this.totalEfectivo = new BigDecimal(0);
        try { 
            for (IngresosEgresos item : lstIngresosEgresos) {
                totalVentas = totalVentas.add(item.getVentas());
                totalCobros = totalCobros.add(item.getCobros());
                totalIngresos = totalIngresos.add(item.getTotalIngresos());
                totalCompras = totalCompras.add(item.getCompras());
                totalGastos = totalGastos.add(item.getGastos());
                totalEgresos = totalEgresos.add(item.getTotalEgresos());
            }
            totalEfectivo = totalIngresos.subtract(totalEgresos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        SimpleDateFormat Formato1 = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaIni = Formato1.format(this.fechaInicio);
        String fechaFi = Formato1.format(this.fechaFin);
         //Instancia hacia la clase ProveedorDatos        
        IngresosEgresosDetallado iDatos = new IngresosEgresosDetallado();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/Reportes/IngresosEgresosDetallado.jasper");

        iDatos.getReporte(ruta, fechaIni, fechaFi);
        FacesContext.getCurrentInstance().responseComplete();
               
    }
}

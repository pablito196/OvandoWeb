package sys.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.clasesauxiliares.FacturaOficial;
import sys.clasesauxiliares.Numero_a_Letra;
import sys.clasesauxiliares.ProveedorDatos;
import sys.clasesauxiliares.VisualizarFactura;
import sys.dao.DosificacionDAO;
import sys.dao.FacturaDAO;
import sys.dao.VentaDAO;
import sys.dao.VentafacturaDAO;
import sys.imp.DosificacionDAOImp;
import sys.imp.FacturaDAOImp;
import sys.imp.VentaDAOImp;
import sys.imp.VentafacturaDAOImp;
import sys.model.Dosificacion;
import sys.model.Factura;
import sys.model.Venta;
import sys.model.Ventafactura;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class FacturaBEAN {

    Session session = null;
    Transaction transaction = null;

    private Ventafactura ventaFactura;

    private List<Ventafactura> listaFactura;
    private List<Ventafactura> listaFiltradaFactura;

    public FacturaBEAN() {
        this.ventaFactura = new Ventafactura();
        this.listaFactura = new ArrayList<>();
    }

    public Ventafactura getVentaFactura() {
        return ventaFactura;
    }

    public void setVentaFactura(Ventafactura ventaFactura) {
        this.ventaFactura = ventaFactura;
    }

    public List<Ventafactura> getListaFactura() {
        VentafacturaDAO vfDao = new VentafacturaDAOImp();
        this.listaFactura = vfDao.listarVentafactura();
        return listaFactura;
    }

    public void setListaFactura(List<Ventafactura> listaFactura) {
        this.listaFactura = listaFactura;
    }

    public List<Ventafactura> getListaFiltradaFactura() {
        return listaFiltradaFactura;
    }

    public void setListaFiltradaFactura(List<Ventafactura> listaFiltradaFactura) {
        this.listaFiltradaFactura = listaFiltradaFactura;
    }

    //metodos
    //METODO PARA VISUALIZAR LA FACTURA
    public void verFactura() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.session = null;
        this.transaction = null;

        try {
            System.out.println("entra a ver factura");
            System.out.println("id venta factura: " + ventaFactura.getIdVentaFactura());
            System.out.println("id factura: " + ventaFactura.getFactura().getIdFactura());
            Numero_a_Letra numeroaletra = new Numero_a_Letra();
            String precioliteral = numeroaletra.Convertir(String.valueOf(ventaFactura.getFactura().getMonto()), true);

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            DosificacionDAO dDao = new DosificacionDAOImp();
            Dosificacion dosificacion = dDao.buscarDosificacion(this.session, ventaFactura.getFactura().getNumeroAutorizacion());

            VentaDAO vDao = new VentaDAOImp();
            Venta venta = vDao.buscarVenta(this.session, ventaFactura.getVenta().getNumeroVenta());

            VisualizarFactura vFactura = new VisualizarFactura();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String ruta = servletContext.getRealPath("/Reportes/VisualizarFactura.jasper");

            System.out.println("numero factura: " + ventaFactura.getFactura().getNumeroFactura());

            vFactura.getReporte(ruta, ventaFactura.getFactura().getIdFactura(), ventaFactura.getFactura().getNumeroFactura(), precioliteral, ventaFactura.getFactura().getFechaFactura(), ventaFactura.getFactura().getRazonSocial(),
                    ventaFactura.getFactura().getMonto(), ventaFactura.getFactura().getNitCliente(), dosificacion.getFechaLimiteEmision(), ventaFactura.getFactura().getCodigoControl(), venta.getUsuario().getLoginUsuario(), ventaFactura.getFactura().getNumeroAutorizacion());

            FacesContext.getCurrentInstance().responseComplete();
            this.transaction.commit();
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();

            }
            System.out.println(e.getMessage());
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

    }

    //METODO PARA imprimir LA FACTURA
    public void imprimirFactura() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.session = null;
        this.transaction = null;

        try {
            System.out.println("entra a ver factura");
            System.out.println("id venta factura: " + ventaFactura.getIdVentaFactura());
            System.out.println("id factura: " + ventaFactura.getFactura().getIdFactura());
            Numero_a_Letra numeroaletra = new Numero_a_Letra();
            String precioliteral = numeroaletra.Convertir(String.valueOf(ventaFactura.getFactura().getMonto()), true);

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            DosificacionDAO dDao = new DosificacionDAOImp();
            Dosificacion dosificacion = dDao.buscarDosificacion(this.session, ventaFactura.getFactura().getNumeroAutorizacion());

            VentaDAO vDao = new VentaDAOImp();
            Venta venta = vDao.buscarVenta(this.session, ventaFactura.getVenta().getNumeroVenta());

            FacturaOficial fOficial = new FacturaOficial();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String ruta = servletContext.getRealPath("/Reportes/FacturaOficial.jasper");

            System.out.println("numero factura: " + ventaFactura.getFactura().getNumeroFactura());

            fOficial.getReporte(ruta, ventaFactura.getFactura().getIdFactura(), ventaFactura.getFactura().getNumeroFactura(), precioliteral, ventaFactura.getFactura().getFechaFactura(), ventaFactura.getFactura().getRazonSocial(),
                    ventaFactura.getFactura().getMonto(), ventaFactura.getFactura().getNitCliente(), dosificacion.getFechaLimiteEmision(), ventaFactura.getFactura().getCodigoControl(), venta.getUsuario().getLoginUsuario(), ventaFactura.getFactura().getNumeroAutorizacion(), dosificacion.getTexto());
            FacesContext.getCurrentInstance().responseComplete();
            this.transaction.commit();
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();

            }
            System.out.println(e.getMessage());
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

    }

    // METODO PARA ANULAR LA FACTURA
    public void anularFactura() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            FacturaDAO fDao = new FacturaDAOImp();
            this.transaction = this.session.beginTransaction();
            Factura factura = new Factura();
            factura = fDao.buscarFactura(this.session, ventaFactura.getFactura().getIdFactura());
            factura.setEstado("ANULADA");
            fDao.actualizarFactura(this.session, factura);
            this.transaction.commit();
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();

            }
            System.out.println(e.getMessage());
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
}

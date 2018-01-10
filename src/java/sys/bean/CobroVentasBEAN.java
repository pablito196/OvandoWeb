package sys.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.SelectEvent;
import sys.clasesauxiliares.PagoCreditoMaestro;
import sys.clasesauxiliares.VentaFormateada;
import sys.dao.ClienteDAO;
import sys.dao.PagoventascreditoDAO;
import sys.dao.VentaDAO;
import sys.imp.ClienteDAOImp;
import sys.imp.PagoventascreditoDAOImp;
import sys.imp.VentaDAOImp;
import sys.model.Cliente;
import sys.model.Venta;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class CobroVentasBEAN {

    Session session = null;
    Transaction transaction = null;

    private Cliente cliente;
    private List<Cliente> listaClientes;
    private List<Cliente> listaFiltradaClientes;
    private List<Cliente> listaClientesInforme;
    private List<Venta> lstVentas;
    private List<VentaFormateada> listaVentasFormateada;
    private Date fechaInicio;
    private Date fechaFin;

    public CobroVentasBEAN() {
        cliente = new Cliente();
        listaClientesInforme = new ArrayList<>();
        lstVentas = new ArrayList<>();
        this.listaVentasFormateada = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public List<Cliente> getListaClientes() {
        ClienteDAO dao = new ClienteDAOImp();
        listaClientes = dao.listarClientes();
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Cliente> getListaFiltradaClientes() {
        return listaFiltradaClientes;
    }

    public void setListaFiltradaClientes(List<Cliente> listaFiltradaClientes) {
        this.listaFiltradaClientes = listaFiltradaClientes;
    }

    public List<Cliente> getListaClientesInforme() {
        return listaClientesInforme;
    }

    public void setListaClientesInforme(List<Cliente> listaClientesInforme) {
        this.listaClientesInforme = listaClientesInforme;
    }

    public List<Venta> getLstVentas() {
        return lstVentas;
    }

    public void setLstVentas(List<Venta> lstVentas) {
        this.lstVentas = lstVentas;
    }

    public List<VentaFormateada> getListaVentasFormateada() {
        return listaVentasFormateada;
    }

    public void setListaVentasFormateada(List<VentaFormateada> listaVentasFormateada) {
        this.listaVentasFormateada = listaVentasFormateada;
    }

    //METODOS
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void agregarClientesInforme() {

        try {
            this.listaClientesInforme.add(this.cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cliente agregado al detalle de búsqueda"));

            this.cliente = null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void quitarClienteListaInforme(String ci, Integer filaSeleccionada) {
        try {
            int i = 0;
            /*System.out.println("ci cliente parametro: " + ciCliente);
             System.out.println("fila seleccionada: " + filaSeleccionada);*/
            for (Cliente item : this.listaClientesInforme) {
                //System.out.println("ci cliente lista: " + item.getCiCliente());
                if (item.getCiCliente().equals(ci) && filaSeleccionada.equals(i)) {
                    this.listaClientesInforme.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Cliente retirado de la lista"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void buscarVentas() {
        VentaDAO vDao = new VentaDAOImp();
        this.lstVentas = vDao.listarVenta();
        //System.out.println("tamaño lista de ventas: " + this.listaVentas.size());
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            PagoventascreditoDAO pcrDao = new PagoventascreditoDAOImp();
            //FacturaDAO fDao = new FacturaDAOImp();

            this.transaction = this.session.beginTransaction();

            int cont = 0;
            /*for (Venta item : this.listaVentas) {
             BigDecimal saldo = new BigDecimal(0);
             String facturas = "";
                
             if (item.getTipopago().getIdTipopago() == 2) {
             saldo = pcrDao.buscarUltimoSaldoPorVenta(this.session, item.getNumeroVenta());
             }
             this.listaVentasFormateada.add(new VentaFormateada(item.getNumeroVenta(), item.getNumeroPreVenta(), item.getCliente().getCiCliente(), item.getCliente().getNombresCliente(), item.getTipopago().getDescPago(), item.getTipoCambio(), item.getMontoTotal(), saldo, facturas, item.getFechaVenta(), item.getEstado(), item.getObservaciones()));
             cont++;
             }*/
            System.out.println("contador por el for: " + cont);
            //this.listaVentas = new ArrayList<>();
            this.transaction.commit();
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

    public String listarClientes() {
        String hash = " ( ";
        int i = 0;
        for (Cliente item : listaClientesInforme) {
            hash += item.getCiCliente();
            if (i == listaClientesInforme.size() - 1) {
                hash += ")";
            } else {
                hash += ",";
            }
            i++;
        }

        return hash;
    }

    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        SimpleDateFormat Formato1 = new SimpleDateFormat("yyyy-MM-dd");

        String fechaIni = Formato1.format(this.fechaInicio);
        String fechaFi = Formato1.format(this.fechaFin);

        System.out.println("entra a ver reporte");

        String clientes = listarClientes();
        
        PagoCreditoMaestro pagDatos = new PagoCreditoMaestro();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/Reportes/PagoCreditoMaestro.jasper");

        pagDatos.getReporte(ruta, fechaIni, fechaFi, clientes);

        //vDetalladas.getReporte(ruta, fechaIni, fechaFi);
        FacesContext.getCurrentInstance().responseComplete();


    }
}

package sys.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import sys.clasesauxiliares.FacturaOficial;
import sys.clasesauxiliares.Numero_a_Letra;
import sys.clasesauxiliares.VentaFormateada;
import sys.clasesauxiliares.Ventacredito;
import sys.clasesauxiliares.VentasDetalladas;
import sys.clasesauxiliares.VentasParametrizado;
import sys.dao.AlmacenDAO;
import sys.dao.ArticuloDAO;
import sys.dao.ClienteDAO;
import sys.dao.CronoventascreditoDAO;
import sys.dao.DetallePreventaDAO;
import sys.dao.DetalleventaDAO;
import sys.dao.DosificacionDAO;
import sys.dao.EntradaDAO;
import sys.dao.ExistenciaDAO;
import sys.dao.FacturaDAO;
import sys.dao.PagoventascreditoDAO;
import sys.dao.PreventafacturaDAO;
import sys.dao.SalidaDAO;
import sys.dao.UsuarioDAO;
import sys.dao.VentaDAO;
import sys.dao.VentafacturaDAO;
import sys.imp.AlmacenDAOImp;
import sys.imp.ArticuloDAOImp;
import sys.imp.ClienteDAOImp;
import sys.imp.CronoventascreditoDAOImp;
import sys.imp.DetallePreventaDAOImp;
import sys.imp.DetalleventaDAOImp;
import sys.imp.DosificacionDAOImp;
import sys.imp.EntradaDAOImp;
import sys.imp.ExistenciaDAOImp;
import sys.imp.FacturaDAOImp;
import sys.imp.PagoventascreditoDAOImp;
import sys.imp.PreventafacturaDAOImp;
import sys.imp.SalidaDAOImp;
import sys.imp.UsuarioDAOImp;
import sys.imp.VentaDAOImp;
import sys.imp.VentafacturaDAOImp;
import sys.impuestos.CodigoControl7;
import sys.model.Almacen;
import sys.model.Articulo;
import sys.model.Cliente;
import sys.model.Cronoventascredito;
import sys.model.Detallepreventa;
import sys.model.Detalleventa;
import sys.model.Dosificacion;
import sys.model.Entrada;
import sys.model.Existencia;
import sys.model.Factura;
import sys.model.Pagoventascredito;
import sys.model.Preventafactura;
import sys.model.Salida;
import sys.model.Tipomoneda;
import sys.model.Tipopago;
import sys.model.Usuario;
import sys.model.Venta;
import sys.model.Ventafactura;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class VentaBEAN {

    Session session = null;
    Transaction transaction = null;

    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;

    private Venta venta;
    private Cliente cliente;
    private Pagoventascredito pagoventa;
    private Usuario usuario;
    private Cronoventascredito cronoVentasCredito;
    private Articulo articulo;
    private Tipomoneda tipoMoneda;
    private Tipopago tipoPago;
    private VentaFormateada ventaFormateada;
    private Dosificacion dosificacion;
    private Factura factura;
    private Ventafactura ventaFactura;

    private String ciCliente;
    private BigDecimal totalSaldo;
    private String montoaPagar;
    private String observacionPagoVenta;
    private Long numeroVenta;
    private BigDecimal totalVenta;
    private String fechaSistema;
    private String codigoArticulo;
    private String observaciones;
    private String articuloSeleccionado;
    private BigDecimal precioVentaReal;
    private String cantidadArticulo;
    private String cantidadArticuloporCodigo;
    private BigDecimal precioVentaRealporCodigo;

    private List<Detalleventa> listaDetalleventa;
    private List<Detalleventa> listaDetalleventaAdministracion;
    private List<Venta> listaVentas;
    private List<Venta> listaFiltradaVentas;
    private List<Venta> listaVentasCredito;
    private List<Ventacredito> listaVentascreditoFormateada;
    private List<VentaFormateada> listaVentasFormateada;
    private List<VentaFormateada> listaFiltradaVentasFormateada;
    //private List<Venta> listaVentasInforme;
    private List<VentaFormateada> listaVentasFormateadaInforme;
    private List<Cliente> listaClientes;

    private List<Venta> lstVentas;
    //para reporte de ventas
    private Date fechaInicio;
    private Date fechaFin;
    private boolean todosLosClientes;
    private BigDecimal totalVentaInforme;

    private String nitFactura;
    private String razonSocialFactura;
    
    private Integer numeroFactura;

    public VentaBEAN() {
        this.venta = new Venta();
        this.cliente = new Cliente();
        this.pagoventa = new Pagoventascredito();
        this.usuario = new Usuario();
        this.listaVentasCredito = new ArrayList<>();
        this.listaVentascreditoFormateada = new ArrayList<>();
        this.listaDetalleventaAdministracion = new ArrayList<>();
        this.cronoVentasCredito = new Cronoventascredito();
        this.articulo = new Articulo();
        this.tipoMoneda = new Tipomoneda();
        this.tipoPago = new Tipopago();
        this.listaDetalleventa = new ArrayList<>();
        this.listaVentasFormateada = new ArrayList<>();

        //this.listaVentasInforme = new ArrayList<>();
        this.lstVentas = new ArrayList<>();
        this.listaVentasFormateadaInforme = new ArrayList<>();

        this.listaClientes = new ArrayList<>();

        this.ventaFormateada = new VentaFormateada();

        this.fechaInicio = null;
        this.fechaFin = null;
        this.totalVentaInforme = new BigDecimal(0);

        this.nitFactura = "";
        this.razonSocialFactura = "";
        
        this.dosificacion = new Dosificacion();
        this.factura = new Factura();
        this.ventaFactura = new Ventafactura();
    }

    public loginBEAN getlBean() {
        return lBean;
    }

    public void setlBean(loginBEAN lBean) {
        this.lBean = lBean;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pagoventascredito getPagoventa() {
        return pagoventa;
    }

    public void setPagoventa(Pagoventascredito pagoventa) {
        this.pagoventa = pagoventa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cronoventascredito getCronoVentasCredito() {
        return cronoVentasCredito;
    }

    public void setCronoVentasCredito(Cronoventascredito cronoVentasCredito) {
        this.cronoVentasCredito = cronoVentasCredito;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Tipomoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(Tipomoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Tipopago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Tipopago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public VentaFormateada getVentaFormateada() {
        return ventaFormateada;
    }

    public void setVentaFormateada(VentaFormateada ventaFormateada) {
        this.ventaFormateada = ventaFormateada;
    }

    public Dosificacion getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(Dosificacion dosificacion) {
        this.dosificacion = dosificacion;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Ventafactura getVentaFactura() {
        return ventaFactura;
    }

    public void setVentaFactura(Ventafactura ventaFactura) {
        this.ventaFactura = ventaFactura;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public BigDecimal getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(BigDecimal totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public String getMontoaPagar() {
        return montoaPagar;
    }

    public void setMontoaPagar(String montoaPagar) {
        this.montoaPagar = montoaPagar;
    }

    public String getObservacionPagoVenta() {
        return observacionPagoVenta;
    }

    public void setObservacionPagoVenta(String observacionPagoVenta) {
        this.observacionPagoVenta = observacionPagoVenta;
    }

    public Long getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(Long numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getFechaSistema() {
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        this.fechaSistema = (dia + "/" + (mes + 1) + "/" + anio);
        return fechaSistema;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getArticuloSeleccionado() {
        return articuloSeleccionado;
    }

    public void setArticuloSeleccionado(String articuloSeleccionado) {
        this.articuloSeleccionado = articuloSeleccionado;
    }

    public BigDecimal getPrecioVentaReal() {
        return precioVentaReal;
    }

    public void setPrecioVentaReal(BigDecimal precioVentaReal) {
        this.precioVentaReal = precioVentaReal;
    }

    public String getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(String cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    public String getCantidadArticuloporCodigo() {
        return cantidadArticuloporCodigo;
    }

    public void setCantidadArticuloporCodigo(String cantidadArticuloporCodigo) {
        this.cantidadArticuloporCodigo = cantidadArticuloporCodigo;
    }

    public BigDecimal getPrecioVentaRealporCodigo() {
        return precioVentaRealporCodigo;
    }

    public void setPrecioVentaRealporCodigo(BigDecimal precioVentaRealporCodigo) {
        this.precioVentaRealporCodigo = precioVentaRealporCodigo;
    }

    public String getNitFactura() {
        return nitFactura;
    }

    public void setNitFactura(String nitFactura) {
        this.nitFactura = nitFactura;
    }

    public String getRazonSocialFactura() {
        return razonSocialFactura;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public void setRazonSocialFactura(String razonSocialFactura) {
        this.razonSocialFactura = razonSocialFactura;
    }

    public List<Venta> getListaFiltradaVentas() {
        return listaFiltradaVentas;
    }

    public void setListaFiltradaVentas(List<Venta> listaFiltradaVentas) {
        this.listaFiltradaVentas = listaFiltradaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public List<Venta> getListaVentasCredito() {
        return listaVentasCredito;
    }

    public void setListaVentasCredito(List<Venta> listaVentasCredito) {
        this.listaVentasCredito = listaVentasCredito;
    }

    public List<Ventacredito> getListaVentascreditoFormateada() {
        return listaVentascreditoFormateada;
    }

    public void setListaVentascreditoFormateada(List<Ventacredito> listaVentascreditoFormateada) {
        this.listaVentascreditoFormateada = listaVentascreditoFormateada;
    }

    public List<Detalleventa> getListaDetalleventa() {
        return listaDetalleventa;
    }

    public void setListaDetalleventa(List<Detalleventa> listaDetalleventa) {
        this.listaDetalleventa = listaDetalleventa;
    }

    public List<VentaFormateada> getListaVentasFormateada() {
        this.listarVentas();
        return listaVentasFormateada;
    }

    public void setListaVentasFormateada(List<VentaFormateada> listaVentasFormateada) {
        this.listaVentasFormateada = listaVentasFormateada;
    }

    public List<VentaFormateada> getListaFiltradaVentasFormateada() {
        return listaFiltradaVentasFormateada;
    }

    public void setListaFiltradaVentasFormateada(List<VentaFormateada> listaFiltradaVentasFormateada) {
        this.listaFiltradaVentasFormateada = listaFiltradaVentasFormateada;
    }

    public List<Detalleventa> getListaDetalleventaAdministracion() {
        this.listarDetalleVentaPorVenta();
        return listaDetalleventaAdministracion;
    }

    public void setListaDetalleventaAdministracion(List<Detalleventa> listaDetalleventaAdministracion) {
        this.listaDetalleventaAdministracion = listaDetalleventaAdministracion;
    }

    //METODOS
    //metodo para listar ventas en administracion de ventas
    public void listarVentas() {
        VentaDAO vDao = new VentaDAOImp();
        this.listaVentas = vDao.listarVenta();
        System.out.println("tamaño lista de ventas: " + this.listaVentas.size());
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            PagoventascreditoDAO pcrDao = new PagoventascreditoDAOImp();
            //FacturaDAO fDao = new FacturaDAOImp();

            this.transaction = this.session.beginTransaction();

            int cont = 0;
            for (Venta item : this.listaVentas) {
                BigDecimal saldo = new BigDecimal(0);
                String facturas = "";
                /*List<Factura> listaFactura = new ArrayList<Factura>();
                 listaFactura = fDao.listarFacturasPorVenta(this.session, item.getNumeroVenta());
                 if (listaFactura.size() > 0) {
                 for (Factura l : listaFactura) {
                 facturas = facturas + ", " + l.getNumeroFactura();
                 }
                 }*/
                if (item.getTipopago().getIdTipopago() == 2) {
                    saldo = pcrDao.buscarUltimoSaldoPorVenta(this.session, item.getNumeroVenta());
                }
                this.listaVentasFormateada.add(new VentaFormateada(item.getNumeroVenta(), item.getNumeroPreVenta(), item.getCliente().getCiCliente(), item.getCliente().getNombresCliente(), item.getTipopago().getDescPago(), item.getTipoCambio(), item.getMontoTotal(), saldo, facturas, item.getFechaVenta(), item.getEstado(), item.getObservaciones(), item.getAlmacen().getSucursal().getNombreSucursal(), item.getAlmacen().getSucursal().getIdSucursal()));
                cont++;
            }
            System.out.println("contador por el for: " + cont);
            this.listaVentas = new ArrayList<>();
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

    //Metodo para mostrar los datos de cliente por medio del campo de texto cicliente
    public void agregarDatosClientetxtnitCliente() {
        this.session = null;
        this.transaction = null;

        try {

            if (this.ciCliente.equals("")) {
                return;
            }
            this.session = HibernateUtil.getSessionFactory().openSession();
            ClienteDAO cDao = new ClienteDAOImp();
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del proveedor en la variable objeto proveedor
            this.cliente = cDao.obtenerClientePorCi(this.session, this.ciCliente);
            if (this.cliente != null) {
                this.ciCliente = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del cliente agregados"));
                listarVentaCredito(this.session);
                //calcularTotalSaldo();
            } else {
                this.ciCliente = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cliente no encontrado"));
            }
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

    //metodo para listar ventas a credito por cliente
    public void listarVentaCredito(Session session) throws Exception {
        VentaDAO vDao = new VentaDAOImp();
        CronoventascreditoDAO crDao = new CronoventascreditoDAOImp();
        PagoventascreditoDAO pcrDao = new PagoventascreditoDAOImp();
        //System.out.println("id Proveedor: "+this.proveedor.getIdProveedor());
        this.listaVentasCredito = vDao.listarVentaCreditoVigentePorCliente(this.session, this.cliente.getCiCliente());
        this.listaVentascreditoFormateada = new ArrayList<>();
        //System.out.println("cantidad de elementos de la lista: "+this.listaComprasCredito.size());
        if (this.listaVentasCredito.size() > 0) {

            System.out.println("entra por el if !!!!");
            for (Venta item : listaVentasCredito) {
                System.out.println("entra por el for !!!!");
                System.out.println("numero venta: " + item.getNumeroVenta());

                Integer idCronoventascred = crDao.buscarCronogramaPorVenta(this.session, item.getNumeroVenta());

                //System.out.println("id Cronograma c: "+idCronocomprascred);
                this.pagoventa = pcrDao.buscarPagoVenta(this.session, idCronoventascred);

                //System.out.println("id Pago credito: "+pagocompra.getIdPagocredito());
                //MONTO PAGADO POR VENTA
                BigDecimal montoTotalPagado = pcrDao.calcularMontoPagado(this.session, item.getNumeroVenta());
                //System.out.println("monto pagado hasta ahora: "+montoTotalPagado);
                //
                this.listaVentascreditoFormateada.add(new Ventacredito(item.getNumeroVenta(), this.cliente.getCiCliente(), idCronoventascred, pagoventa.getIdPagoVentaCred(), item.getFechaVenta(), item.getMontoTotal(), montoTotalPagado, pagoventa.getSaldo()));

            }
            this.calcularTotalSaldo();
            this.enabledButton();
        } else {
            this.disableButton();
            this.calcularTotalSaldo();
        }
    }

    //metodo para calcular el total saldo
    public void calcularTotalSaldo() {
        this.totalSaldo = new BigDecimal(0);
        try {
            for (Ventacredito item : listaVentascreditoFormateada) {
                totalSaldo = totalSaldo.add(item.getSaldo());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //Metodo para mostrar los datos de proveeor por medio del dialog proveedor
    public void agregarDatosCliente(String ciCliente) {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ClienteDAO cDao = new ClienteDAOImp();
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del proveedor en la variable objeto proveedor
            this.cliente = cDao.obtenerClientePorCi(this.session, ciCliente);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del cliente agregados"));
            listarVentaCredito(this.session);
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

    //metodo para cobrar las ventas a credito
    public void cobrarVentaCredito() {
        BigDecimal montoPagar = new BigDecimal(this.montoaPagar);
        this.session = null;
        this.transaction = null;
        int idusuario = lBean.getUsuario().getIdUsuario();
        this.usuario.setIdUsuario(idusuario);
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            java.sql.Date fechahoy = new java.sql.Date(new java.util.Date().getTime());
            CronoventascreditoDAO crDao = new CronoventascreditoDAOImp();
            VentaDAO vDao = new VentaDAOImp();
            PagoventascreditoDAO pcDao = new PagoventascreditoDAOImp();
            //Cronocomprascred cronogramaCompras = new Cronocomprascred();
            //***
            Iterator<Ventacredito> iterador = listaVentascreditoFormateada.listIterator();

            System.out.println("id usuario: " + this.usuario.getIdUsuario());

            while (iterador.hasNext() && montoPagar.compareTo(BigDecimal.ZERO) > 0) {
                Ventacredito item = (Ventacredito) iterador.next();
                Pagoventascredito pagoVenta = new Pagoventascredito();
                //System.out.println("monto pagar: "+montoPagar);
                //System.out.println("saldo: "+item.getSaldo());
                if (montoPagar.compareTo(item.getSaldo()) > 0) {
                    //System.out.println("id compra: "+item.getIdCompra());
                    pagoVenta.setCronoventascredito(crDao.obtenerUltimoCronogramaPorVenta(this.session, item.getNumeroVenta()));
                    pagoVenta.setFechaPago(fechahoy);
                    pagoVenta.setMontoPagado(item.getSaldo());
                    pagoVenta.setSaldo(new BigDecimal("0"));
                    pagoVenta.setObservacion(this.observacionPagoVenta);
                    pagoVenta.setUsuario(this.usuario);
                    pcDao.guardarPagoVentas(this.session, pagoVenta);
                    montoPagar = montoPagar.subtract(item.getSaldo());
                    //System.out.println("monto pagar: "+montoPagar);
                } else {
                    //System.out.println("id compra: "+item.getIdCompra());
                    pagoVenta.setCronoventascredito(crDao.obtenerUltimoCronogramaPorVenta(this.session, item.getNumeroVenta()));
                    pagoVenta.setFechaPago(fechahoy);
                    pagoVenta.setMontoPagado(montoPagar);
                    //System.out.println("saldo: "+item.getSaldo().subtract(montoPagar));
                    pagoVenta.setSaldo(item.getSaldo().subtract(montoPagar));
                    pagoVenta.setObservacion(this.observacionPagoVenta);
                    pagoVenta.setUsuario(this.usuario);
                    pcDao.guardarPagoVentas(this.session, pagoVenta);

                    montoPagar = montoPagar.subtract(item.getSaldo());
                    //System.out.println("monto pagar: "+montoPagar);
                    this.venta = vDao.buscarVenta(this.session, item.getNumeroVenta());
                    this.cronoVentasCredito.setVenta(this.venta);
                    this.cronoVentasCredito.setFechaProgramada(fechahoy);
                    this.cronoVentasCredito.setUsuario(this.usuario);
                    this.cronoVentasCredito.setUsuario(this.usuario);
                    crDao.guardarCronogramaVentas(this.session, this.cronoVentasCredito);
                }

            }
            this.transaction.commit();
            //***
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Pago realizado"));
        this.limpiarVentasaCredito();
    }

    //metodo para limpiar ventas a credito
    public void limpiarVentasaCredito() {
        this.cliente = new Cliente();
        this.venta = new Venta();
        //this.numeroVenta = null;
        this.montoaPagar = "";
        this.observacionPagoVenta = "";
        this.ciCliente = "";
        this.listaVentascreditoFormateada = new ArrayList<>();
        this.pagoventa = new Pagoventascredito();
        this.totalSaldo = new BigDecimal(0);
        this.montoaPagar = "";
        //this.idAlmacen = 0;
        //invocar al metodo para desactivar controles
        this.disableButton();
    }

    //METODOS PARA NUEVA VENTA
    //metodo para generar el numero de venta
    public void numeracionVenta() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            VentaDAO vDao = new VentaDAOImp();
            //verificar si hay registros en la tabla venta de la BD
            this.numeroVenta = vDao.obtenerTotalRegistrosVenta(this.session);

            if (this.numeroVenta <= 0 || this.numeroVenta == null) {
                this.numeroVenta = Long.valueOf("1");
            } else {
                //recuperamos el ultimo registro q existe en la bd
                this.venta = vDao.obtenerUltimoRegistro(this.session);
                this.numeroVenta = Long.valueOf(this.venta.getNumeroVenta() + 1);

                //limpiar la variable totalVenta
                this.totalVenta = new BigDecimal("0");
            }
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

    //Metodo para mostrar el dialogCantidadArticuloporCodigo
    public void mostrarCantidadArticuloporCodigo() {
        this.session = null;
        this.transaction = null;

        try {

            if (this.codigoArticulo.equals("")) {
                return;
            }
            System.out.println("codigo articulo: " + this.codigoArticulo);
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del articulo en la variable objeto articulo segun el codigoarticulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.codigoArticulo);

            if (this.articulo != null) {
                //solicitar mostrar dialogCantidadArticuloporCodigo
                RequestContext contex = RequestContext.getCurrentInstance();
                contex.execute("PF('dialogCantidadArticuloporCodigo').show();");

                this.codigoArticulo = "";
            } else {
                this.codigoArticulo = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Artículo no encontrado"));
            }
            this.transaction.commit();

            //llamada al metodo calcular total compra
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

    //metodo para calcular el total de la venta
    public void totalVenta() {
        this.totalVenta = new BigDecimal(0);
        try {
            for (Detalleventa item : listaDetalleventa) {
                BigDecimal totalVentaPorArticulo = item.getPrecioVentaReal().multiply(new BigDecimal(item.getCantidad()));
                item.setTotal(totalVentaPorArticulo);
                totalVenta = totalVenta.add(totalVentaPorArticulo);
            }
            this.venta.setMontoTotal(totalVenta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //metodo para quitar articulo del detalle de venta
    public void quitarArticuloDetalleVenta(String codArticulo, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Detalleventa item : this.listaDetalleventa) {
                if (item.getArticulo().getCodigoArticulo().equals(codArticulo) && filaSeleccionada.equals(i)) {
                    this.listaDetalleventa.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Artículo quitado de la venta"));
            //llamar al metodo totalVenta para actualizar el total de la venta
            this.totalVenta();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    //metodo para guardar la venta
    public void guardarVenta() {
        System.out.println("entra a guardar venta ");
        this.session = null;
        this.transaction = null;

        int idusuario = lBean.getUsuario().getIdUsuario();
        int idsucursal = lBean.getIdSucursal();
        System.out.println("id sucursal:  " + idsucursal);
        this.usuario.setIdUsuario(idusuario);
        this.tipoMoneda.setIdTipomoneda(1);
        this.tipoPago.setIdTipopago(1);
        long tiempo = System.currentTimeMillis();
        java.sql.Date fechaventa = new java.sql.Date(new java.util.Date().getTime());
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();
            VentaDAO vDao = new VentaDAOImp();
            DetalleventaDAO dvDao = new DetalleventaDAOImp();
            AlmacenDAO alDao = new AlmacenDAOImp();
            SalidaDAO sDao = new SalidaDAOImp();
            ExistenciaDAO exDao = new ExistenciaDAOImp();

            Almacen almacen = new Almacen();
            this.transaction = this.session.beginTransaction();

            almacen = alDao.buscarAlmacenPrincipal(this.session, idsucursal);
            //datos para guardar en la tabla preventa de la bd
            this.venta.setCliente(this.cliente);
            this.venta.setUsuario(this.usuario);
            this.venta.setTipomoneda(tipoMoneda);
            this.venta.setTipopago(tipoPago);
            this.venta.setEstado("VENDIDO");
            this.venta.setTiempoRegistro(tiempo);
            this.venta.setFechaVenta(fechaventa);
            this.venta.setTipoCambio(new BigDecimal(6.96));
            this.venta.setImpuesto(0.00);
            this.venta.setObservaciones(this.observaciones);

            this.venta.setAlmacen(almacen);
            //hacemos el insert a la tabla venta
            vDao.guardarVenta(this.session, this.venta);
            //recuperar el ultimo registro de la tabla venta
            this.venta = vDao.obtenerUltimoRegistroTiempo(this.session, tiempo);
            //recorremos el arraylist para guardar el registro del detalle de venta en la bd
            for (Detalleventa item : listaDetalleventa) {

                this.articulo = aDao.obtenerArticuloPorCodigo(this.session, item.getArticulo().getCodigoArticulo());
                item.setVenta(this.venta);
                item.setArticulo(this.articulo);
                item.setIdFactura(0);
                //hacemos el insert en la tabla detallepreventa de la BD
                dvDao.guardarDetalleVenta(this.session, item);
                //SALIDA EN ALMACEN
                Salida salida = new Salida();
                salida.setArticulo(this.articulo);
                salida.setFechaSalida(fechaventa);
                salida.setCantidad(item.getCantidad());
                salida.setObservacion("POR VENTA Nº " + this.venta.getNumeroVenta());
                sDao.guardarSalida(this.session, salida);

                //EXISTENCIA
                Existencia existencia = new Existencia();

                existencia = exDao.buscarExistenciaPorAlmacen(this.session, almacen.getIdAlmacen(), item.getArticulo().getCodigoArticulo());
                if (existencia == null) {
                    existencia = new Existencia();
                    //System.out.println("codigo articulo no existe en almacen");
                    existencia.setAlmacen(almacen);
                    existencia.setArticulo(item.getArticulo());
                    existencia.setCantidad(-item.getCantidad());

                    //System.out.println("codigo articulo existente: " + existeEnAlmacen.getArticulo().getCodigoArticulo());
                    exDao.guardarExistencia(this.session, existencia);
                } else {
                    //System.out.println("codigo articulo existente: " + this.existencia.getArticulo().getCodigoArticulo());
                    Float cantidadActualizada = existencia.getCantidad() - item.getCantidad();
                    existencia.setCantidad(cantidadActualizada);

                    //System.out.println("nueva cantidad: " + this.existencia.getCantidad());
                    exDao.actualizarExistencia(this.session, existencia);
                }
            }
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Venta registrada"));

            //this.facturar();
            //this.limpiarPreventa();
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

    //metodo para limpiar la venta
    public void limpiarVenta() {
        this.cliente = new Cliente();
        this.venta = new Venta();
        this.listaDetalleventa = new ArrayList<>();
        this.numeroVenta = null;
        this.totalVenta = null;
        //invocar al metodo para desactivar controles
        this.disableButton();
    }

    //Metodo para solicitar la cantidad de articulos a vender
    public void pedirCantidadProducto(String codArticulo) {
        this.articuloSeleccionado = codArticulo;

        //********
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();

            this.transaction = this.session.beginTransaction();
            //Obtener los datos del articulo en la variable objeto articulo segun el codigo de articulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.articuloSeleccionado);

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
        //********
    }

    //Metodo para mostrar los datos de cliente por medio del dialog articulo
    public void agregarDatosArticulo() {
        if (this.cantidadArticulo.equals("0") || this.cantidadArticulo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticulo = "";
        } else {

            if (this.precioVentaReal == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione el precio de venta"));
                this.precioVentaReal = null;
            } else {
                this.listaDetalleventa.add(new Detalleventa(this.articulo, null, this.precioVentaReal, Float.parseFloat(this.cantidadArticulo),
                        this.precioVentaReal.multiply(new BigDecimal(this.cantidadArticulo))));

                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo total venta
                this.totalVenta();

                this.cantidadArticulo = "";
                this.precioVentaReal = null;
                this.articulo = null;
            }

        }

        ///*****
    }

    //Metodo para mostrar los datos de articulo por medio del campo de texto codigoarticulo
    public void agregarDatosArticulotxtcodigoArticulo() {
        System.out.println("entra aqui");
        if (this.cantidadArticuloporCodigo.equals("0") || this.cantidadArticuloporCodigo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticuloporCodigo = "";
        } else {

            if (this.precioVentaRealporCodigo == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione el precio de venta"));
                this.precioVentaRealporCodigo = null;
            } else {
                System.out.println("sigue por aqui");
                this.listaDetalleventa.add(new Detalleventa(this.articulo, null, this.precioVentaRealporCodigo, Float.parseFloat(this.cantidadArticuloporCodigo),
                        this.precioVentaRealporCodigo.multiply(new BigDecimal(this.cantidadArticuloporCodigo))));
                //this.codigoArticulo = "";
                this.cantidadArticuloporCodigo = "";
                this.precioVentaRealporCodigo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo totalVenta()
                this.totalVenta();
                //   this.transaction.commit();
                this.articulo = null;
            }

        }

    }
    //metodos para activar o desactivar los controles en la venta
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void enabledButton() {
        enabled = true;
    }

    public void disableButton() {
        enabled = false;
    }

    //Metodo para editar la cantidad de articulos en el detalle de preventa
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se modificó la cantidad"));
        //llamada al metodo totalVenta para actualizar en base a los cambios en las cantidades de los productos para actualizar el total a vender
        this.totalVenta();
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se realizó la modificación de la cantidad"));
    }

    //PARA ADMINISTRAR VENTA
    public void listarDetalleVentaPorVenta() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            DetalleventaDAO dvDao = new DetalleventaDAOImp();

            this.transaction = this.session.beginTransaction();
            System.out.println("numero de venta: " + this.ventaFormateada.getNumeroVenta());
            this.listaDetalleventaAdministracion = dvDao.listarDetalleVentaPorVenta(this.session, this.ventaFormateada.getNumeroVenta());


            /*if (this.listaDetallecompraPorCompra.size() > 0) {
             this.listaDetallecompraFormateada = new ArrayList<>();
             for (Detallecompra item : listaDetallecompraPorCompra) {

             this.listaDetallecompraFormateada.add(new DetallecompraFormateada(item.getIdDetalleCompra(), item.getCompra().getIdCompra(), item.getArticulo().getCodigoArticulo(),
             item.getArticulo().getNombre(), item.getArticulo().getDescripcion(), item.getCantidad(),
             item.getCostoUnitario(), item.getCostoTotal()));
             }
             } else {
             this.listaDetallecompraFormateada = new ArrayList<>();
             }
             this.listaDetallecompraPorCompra = null;*/
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

    //metodo para anular la venta
    public void anularVenta() {
        //this.listarDetalleVenta();
        this.session = null;
        this.transaction = null;

        if (!"ANULADA".equals(this.ventaFormateada.getEstado())) {
            java.sql.Date fechahoy = new java.sql.Date(new java.util.Date().getTime());
            try {
                this.session = HibernateUtil.getSessionFactory().openSession();
                DetalleventaDAO dvDao = new DetalleventaDAOImp();
                VentaDAO vDao = new VentaDAOImp();
                EntradaDAO eDao = new EntradaDAOImp();
                ExistenciaDAO exDao = new ExistenciaDAOImp();
                FacturaDAO fDao = new FacturaDAOImp();
                List<Detalleventa> listaventa = new ArrayList<>();
                Integer idFactura = -1;
                this.transaction = this.session.beginTransaction();
                Venta vent = new Venta();
                vent = vDao.buscarVenta(this.session, this.ventaFormateada.getNumeroVenta());
                vent.setEstado("ANULADA");
                vDao.actualizarVenta(this.session, vent);
                listaventa = dvDao.listarDetalleVentaPorVenta(this.session, vent.getNumeroVenta());
                for (Detalleventa item : listaventa) {
                    //ENTRADA ALMACEN
                    Entrada entrada = new Entrada();
                    entrada.setArticulo(item.getArticulo());
                    entrada.setFechaEntrada(fechahoy);
                    entrada.setCantidad(item.getCantidad());
                    entrada.setObservacion("POR ANULAR VENTA Nº " + item.getVenta().getNumeroVenta());
                    eDao.guardarEntrada(this.session, entrada);

                    //EXISTENCIA
                    Existencia existencia = new Existencia();

                    existencia = exDao.buscarExistenciaPorAlmacen(this.session, vent.getAlmacen().getIdAlmacen(), item.getArticulo().getCodigoArticulo());

                    if (existencia != null) {
                        Float cantidadActualizada = existencia.getCantidad() + item.getCantidad();
                        existencia.setCantidad(cantidadActualizada);
                    }

                    exDao.actualizarExistencia(this.session, existencia);

                    if (!Objects.equals(idFactura, item.getIdFactura()) && item.getIdFactura() > 0) {
                        Factura factura = new Factura();
                        factura = fDao.buscarFactura(this.session, item.getIdFactura());
                        factura.setEstado("ANULADA");
                        fDao.actualizarFactura(this.session, factura);
                    }

                }
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "venta anulada"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La venta ya se encuentra anulada"));
        }
    }

    //para informe de ventas
    private String estado;
    private Integer tipoVenta;
    private Integer idSucursal;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(Integer tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
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

    public boolean isTodosLosClientes() {
        return todosLosClientes;
    }

    public void setTodosLosClientes(boolean todosLosClientes) {
        this.todosLosClientes = todosLosClientes;
    }

    /*public List<Venta> getListaVentasInforme() {
     listarVentasEntreFechas();
     return listaVentasInforme;
     }

     public void setListaVentasInforme(List<Venta> listaVentasInforme) {
     this.listaVentasInforme = listaVentasInforme;
     }*/
    public List<VentaFormateada> getListaVentasFormateadaInforme() {
        listarVentasEntreFechas();
        return listaVentasFormateadaInforme;
    }

    public void setListaVentasFormateadaInforme(List<VentaFormateada> listaVentasFormateadaInforme) {
        this.listaVentasFormateadaInforme = listaVentasFormateadaInforme;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Venta> getLstVentas() {
        return lstVentas;
    }

    public void setLstVentas(List<Venta> lstVentas) {
        this.lstVentas = lstVentas;
    }

    public BigDecimal getTotalVentaInforme() {
        return totalVentaInforme;
    }

    public void setTotalVentaInforme(BigDecimal totalVentaInforme) {
        this.totalVentaInforme = totalVentaInforme;
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    private boolean enabledControlesBusqueda;

    public boolean isEnabledControlesBusqueda() {
        return enabledControlesBusqueda;
    }

    public void enabledControlesBusquedaButton() {
        enabledControlesBusqueda = true;
    }

    public void disableControlesBusquedaButton() {
        enabledControlesBusqueda = false;
    }

    //METODOS
    public void activarControlesBusqueda() {
        if (this.todosLosClientes) {
            this.disableControlesBusquedaButton();

        } else {
            this.enabledControlesBusquedaButton();
        }
    }

    public void agregarClientesInforme() {

        try {
            this.listaClientes.add(this.cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cliente agregado al detalle de búsqueda"));

            this.cliente = null;
            //this.listaCuentabancaria = null;
        } catch (Exception e) {
        }

    }

    public void quitarClienteListaInforme(String ciCliente, Integer filaSeleccionada) {
        try {
            int i = 0;
            System.out.println("ci cliente parametro: " + ciCliente);
            System.out.println("fila seleccionada: " + filaSeleccionada);
            for (Cliente item : this.listaClientes) {
                System.out.println("ci cliente lista: " + item.getCiCliente());
                if (item.getCiCliente().equals(ciCliente) && filaSeleccionada.equals(i)) {
                    this.listaClientes.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Cliente retirado de la lista"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public String listarClientes() {
        String hash = " ( ";
        int i = 0;
        for (Cliente item : listaClientes) {
            hash += item.getCiCliente();
            if (i == listaClientes.size() - 1) {
                hash += ")";
            } else {
                hash += ",";
            }
            i++;
        }

        return hash;
    }

    public void buscarVentas() {
        VentaDAO vDao = new VentaDAOImp();
        this.session = null;
        this.transaction = null;
        //this.listaVentasInforme = new ArrayList<>();
        Integer idAlmacen = 0;
        Almacen almacen = new Almacen();
        AlmacenDAO aDao = new AlmacenDAOImp();
        try {
            System.out.println("estado: " + this.estado);
            System.out.println("tipo pago: " + this.tipoVenta);
            System.out.println("id Sucursal: " + this.idSucursal);
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            if (this.idSucursal > 0) {
                almacen = aDao.buscarAlmacenPrincipal(this.session, this.idSucursal);
                idAlmacen = almacen.getIdAlmacen();
            }

            if (todosLosClientes) {
              //PagoventascreditoDAO pcrDao = new PagoventascreditoDAOImp();
                //FacturaDAO fDao = new FacturaDAOImp();

                //System.out.println("antes de pasar el parametro");
                //System.out.println("fecha inicial: " + this.fechaInicio);
                //System.out.println("fecha final: " + this.fechaFin);
                System.out.println("para todos los proveedores");
                System.out.println("id almacen: " + idAlmacen);
                this.lstVentas = vDao.buscarVenta(this.session, this.fechaInicio, this.fechaFin, this.estado, this.tipoVenta, idAlmacen);
                //this.listaVentasInforme = vDao.buscarVenta(this.session, this.fechaInicio, this.fechaFin, this.estado, this.tipoVenta, idAlmacen);
                //System.out.println("tamaño lista de ventas: " + this.listaVentasInforme.size());
            } else {
                String clientes = listarClientes();
                if (!"".equals(clientes) || clientes != null) {
                    this.lstVentas = vDao.buscarVentasPorClientes(this.session, this.fechaInicio, this.fechaFin, this.estado, this.tipoVenta, idAlmacen, clientes);

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar por lo menos un cliente"));
                }
                //this.listaVentasInforme = vDao.listarVentasPorFechaClientes(this.session, this.fechaInicio, this.fechaFin, clientes);
            }
            totalVentaInforme();
            //this.listaVentasInforme = new ArrayList<>();
            this.transaction.commit();
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

    //metodo para calcular el total de la venta
    public void totalVentaInforme() {
        this.totalVentaInforme = new BigDecimal(0);
        try {
            for (Venta item : lstVentas) {
                totalVentaInforme = totalVentaInforme.add(item.getMontoTotal());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarVentasEntreFechas() {
        VentaDAO vDao = new VentaDAOImp();
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            if (todosLosClientes) {
              //PagoventascreditoDAO pcrDao = new PagoventascreditoDAOImp();
                //FacturaDAO fDao = new FacturaDAOImp();

                //System.out.println("antes de pasar el parametro");
                //System.out.println("fecha inicial: " + this.fechaInicio);
                //System.out.println("fecha final: " + this.fechaFin);
                //this.listaVentasInforme = vDao.listarVentasPorFecha(this.session, this.fechaInicio, this.fechaFin);
                //System.out.println("tamaño lista de ventas: " + this.listaVentasInforme.size());
            } else {
                String clientes = listarClientes();
                //this.listaVentasInforme = vDao.listarVentasPorFechaClientes(this.session, this.fechaInicio, this.fechaFin, clientes);
            }
            //this.listaVentasInforme = new ArrayList<>();
            this.transaction.commit();
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
    //metodo para listar ventas entre fechas

    /*public void listarVentasEntreFechas() {
     VentaDAO vDao = new VentaDAOImp();

     this.session = null;
     this.transaction = null;
     try {
     this.session = HibernateUtil.getSessionFactory().openSession();

     PagoventascreditoDAO pcrDao = new PagoventascreditoDAOImp();
     //FacturaDAO fDao = new FacturaDAOImp();

     this.transaction = this.session.beginTransaction();
     System.out.println("antes de pasar el parametro");
     System.out.println("fecha inicial: "+this.fechaInicio);
     System.out.println("fecha final: "+this.fechaFin);
     this.listaVentasInforme = vDao.listarVentasPorFecha(this.session, this.fechaInicio, this.fechaFin);
     System.out.println("tamaño lista de ventas: " + this.listaVentasInforme.size());
     int cont = 0;
     for (Venta item : this.listaVentasInforme) {
     BigDecimal saldo = new BigDecimal(0);
     String facturas = "";
     /*List<Factura> listaFactura = new ArrayList<Factura>();
     listaFactura = fDao.listarFacturasPorVenta(this.session, item.getNumeroVenta());
     if (listaFactura.size() > 0) {
     for (Factura l : listaFactura) {
     facturas = facturas + ", " + l.getNumeroFactura();
     }
     }
     /*if (item.getTipopago().getIdTipopago() == 2) {
     saldo = pcrDao.buscarUltimoSaldoPorVenta(this.session, item.getNumeroVenta());
     }
     this.listaVentasFormateadaInforme.add(new VentaFormateada(item.getNumeroVenta(), item.getNumeroPreVenta(), item.getCliente().getCiCliente(), item.getCliente().getNombresCliente(), item.getTipopago().getDescPago(), item.getTipoCambio(), item.getMontoTotal(), saldo, facturas, item.getFechaVenta(), item.getEstado(), item.getObservaciones()));
     cont++;
     }
     System.out.println("contador por el for: " + cont);
     this.listaVentasInforme = new ArrayList<>();
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
     }*/
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        SimpleDateFormat Formato1 = new SimpleDateFormat("yyyy-MM-dd");
        String fechaIni = Formato1.format(this.fechaInicio);
        String fechaFi = Formato1.format(this.fechaFin);
        int idusuario = lBean.getUsuario().getIdUsuario();
        UsuarioDAO uDao = new UsuarioDAOImp();
        AlmacenDAO aDao = new AlmacenDAOImp();
        Almacen almacen = new Almacen();
        Integer idAlmacen = 0;
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            this.usuario = uDao.buscarUsuario(session, idusuario);
            if (this.idSucursal > 0) {
                almacen = aDao.buscarAlmacenPrincipal(this.session, this.idSucursal);
                idAlmacen = almacen.getIdAlmacen();
            }
            String clientes = "";
            if (!todosLosClientes) {
                clientes = listarClientes();
            } else {
                clientes = null;
            }
            //Instancia hacia la clase Ventas Detalladas       
            VentasParametrizado vParametrizado = new VentasParametrizado();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String ruta = servletContext.getRealPath("/Reportes/VentasParametrizado.jasper");
            System.out.println("fecha inicio: " + fechaIni);
            System.out.println("fecha fin: " + fechaFi);
            vParametrizado.getReporte(ruta, fechaIni, fechaFi, this.estado, this.tipoVenta, idAlmacen, this.usuario.getLoginUsuario(), clientes);
            //vDetalladas.getReporte(ruta, fechaIni, fechaFi);
            FacesContext.getCurrentInstance().responseComplete();

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

    public void prepararDatosFactura() {
        this.nitFactura = this.ventaFormateada.getCiCliente();
        this.razonSocialFactura = this.ventaFormateada.getNombresCliente();
    }

    public void facturar() {
        this.session = null;
        this.transaction = null;

        java.sql.Date fechaFactura = new java.sql.Date(new java.util.Date().getTime());
        try {
            VentafacturaDAO vfDao = new VentafacturaDAOImp();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            DosificacionDAO dDao = new DosificacionDAOImp();
            DetalleventaDAO dvDao = new DetalleventaDAOImp();
            FacturaDAO fDao = new FacturaDAOImp();
            VentaDAO vDao = new VentaDAOImp();
            
            
            //this.transaction = this.session.beginTransaction();
            List<Ventafactura> listafacturas = vfDao.buscarFacturaPorVenta(this.session, this.ventaFormateada.getNumeroVenta());

            if (listafacturas.size() > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La venta seleccionada ya fue facturada"));
            } else {
                dosificacion = dDao.buscarDosificacionActiva(this.session, this.ventaFormateada.getIdSucursal());
                if (dosificacion != null) {
                //long milis1 = ultimafecha.getTime();
                    //long milis2 = fechahoy.getTime();
                    //long dif = (milis2 - milis1)/(60 * 1000);
                    final long milisegundospordia = 24 * 60 * 60 * 1000;
                    long milis1 = dosificacion.getFechaLimiteEmision().getTime();
                    long milis2 = fechaFactura.getTime();
                    long dif = ((milis1 - milis2) / milisegundospordia);

                    if (dif >= 0) {
                        if (dif <= dosificacion.getTiempoAlerta() && dif > -1) {
                            String mensajefechalimite = "Ud. tiene " + dif + " días para solicitar nueva Dosificación";
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", mensajefechalimite));
                        }
                        
                        List<Detalleventa> listaDetalleVenta = dvDao.listarDetalleVentaPorVenta(this.session, this.ventaFormateada.getNumeroVenta());
                        
                        int cantidadProductosDetalle = listaDetalleVenta.size();
                        int cantidadProductosDetalleAuxiliar = listaDetalleVenta.size();

                        double cantidadFacturas = cantidadProductosDetalle / 19.0;

                        double diferencia = cantidadFacturas;

                        int j = 0;
                        int aux = 0;
                        int cantidadrealfacturas = (int) cantidadFacturas;

                        diferencia = diferencia - cantidadrealfacturas;

                    //BigDecimal montoventa = new BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN);
                        //double montoventa = 0;
                        if (diferencia > 0) {
                            cantidadrealfacturas++;
                        }
                        //JOptionPane.showMessageDialog(null, "cant real fact: "+cantidadrealfacturas);
                        for (int i = 1; i <= cantidadrealfacturas; i++) {

                            //this.session = HibernateUtil.getSessionFactory().openSession();
                            this.transaction = this.session.beginTransaction();

                            BigDecimal montoventa = new BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN);
                            System.out.println("contador del for: " + i);
                            //JOptionPane.showMessageDialog(null, "entra por el for");
                            aux = j;
                            //JOptionPane.showMessageDialog(null, " valor aux "+aux);
                            int cont = 1;
                            //JOptionPane.showMessageDialog(null, " cantidad registros "+cantidadregistros);
                            while (cantidadProductosDetalle > 0 && cont <= 19) {
                                Detalleventa itemdt = listaDetalleVenta.get(j);
                                montoventa = montoventa.add(itemdt.getTotal()).setScale(2, RoundingMode.HALF_EVEN);
                                j++;
                                cont++;
                                cantidadProductosDetalle--;
                            }
                            /*modificacion bloquear tabla*/
                            //ConexionMySql.BloquearTabla("Factura", "WRITE"); 
                              /*termina modificacion*/

                            System.out.println("monto factura: " + montoventa);
                            numeracionFactura(this.session);
                            String codigoControl = ObtenerCodigoControl(this.dosificacion.getNumeroAutorizacion(), this.numeroFactura, this.nitFactura, fechaFactura, montoventa, this.dosificacion.getLlaveDosificacion());

                            System.out.println("numero factura: " + this.numeroFactura);
                            System.out.println("codigo control: " + codigoControl);

                            this.factura = new Factura();
                            long tiempo = System.currentTimeMillis();

                            this.factura.setNumeroFactura(this.numeroFactura);
                            this.factura.setNumeroAutorizacion(this.dosificacion.getNumeroAutorizacion());
                            this.factura.setNitCliente(this.nitFactura);
                            this.factura.setFechaFactura(fechaFactura);
                            this.factura.setRazonSocial(this.razonSocialFactura);
                            this.factura.setMonto(montoventa);
                            this.factura.setCodigoControl(codigoControl);
                            this.factura.setEstado("VÁLIDA");
                            this.factura.setTiempoRegistro(tiempo);

                            fDao.guardarFactura(this.session, this.factura);
                            System.out.println("numero factura guardada: " + this.factura.getNumeroFactura());

                            Factura fact = new Factura();

                            fact = fDao.obtenerUltimaFacturaTiempo(this.session, tiempo);

                            this.ventaFactura = new Ventafactura();
                            
                            Venta vent =new Venta();
                            vent = vDao.buscarVenta(this.session, this.ventaFormateada.getNumeroVenta());
                            this.ventaFactura.setVenta(vent);
                            this.ventaFactura.setFactura(fact);

                            vfDao.guardarVentaFactura(this.session, this.ventaFactura);

                            cont = 1;
                            j = aux;

                            while (cantidadProductosDetalleAuxiliar > 0 && cont <= 19) {

                                Detalleventa itemdt = listaDetalleVenta.get(j);
                                itemdt.setIdFactura(fact.getIdFactura());
                                dvDao.actualizarDetalleVenta(session, itemdt);
                                j++;
                                cont++;
                                cantidadProductosDetalleAuxiliar--;
                            }
                            Numero_a_Letra numeroaletra = new Numero_a_Letra();
                            String precioliteral = numeroaletra.Convertir(String.valueOf(fact.getMonto()), true);

                            this.transaction.commit();

                        //IMPRIMIR FACTURA
                            imprimirFactura(fact.getIdFactura(), fact.getNumeroFactura(), precioliteral, fact.getFechaFactura(), fact.getRazonSocial(), fact.getMonto(), fact.getNitCliente(), this.dosificacion.getFechaLimiteEmision(), fact.getCodigoControl(), vent.getUsuario().getLoginUsuario(), fact.getNumeroAutorizacion(), this.dosificacion.getTexto());
                            System.out.println("esta dentro del for");
                        }
                    //this.transaction.commit();
                        //***************TERMINA LA MODIFICACION  DE LA FACTURACION
                        //this.transaction.commit();
                        //this.limpiarPreventa();
                        System.out.println("llega hasta antes del mensaje");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Factura generada"));

                    } else {
                        //mensaje no se puede facturar porq ha excedido la fecha limite de facturacion
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha limite de facturacion a expirado, para facturar debe solicitar nueva dosificación"));
                        //this.limpiarPreventa();
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al facturar, no tiene dosificación activa para esta sucursal"));
                    //this.limpiarPreventa();
                }
            }

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
    
    //metodo para generar el numero de factura
    public void numeracionFactura(Session session) throws Exception {
        Integer inicialActivo;

        Factura fact = new Factura();

        FacturaDAO fDao = new FacturaDAOImp();

        inicialActivo = this.dosificacion.getInicialActivo();
        if (inicialActivo == 1) {
            this.numeroFactura = this.dosificacion.getNumeroInicialFactura();
            DosificacionDAO dDao = new DosificacionDAOImp();
            this.dosificacion.setInicialActivo(0);
            dDao.actualizarDosificacion(session, this.dosificacion);
            //dosificacion.CambiarEstadoInicialActivo(numeroautorizacion, 0); //se debe realizar la funcion para cambiar el estado de la variable inicial activo

        } else {
            fact = fDao.obtenerUltimaFactura(session, this.dosificacion.getNumeroAutorizacion());
            this.numeroFactura = fact.getNumeroFactura() + 1;
        }

    }
    
    public String ObtenerCodigoControl(String numeroautorizacion, long numerofactura, String nit, Date fechatransaccion, BigDecimal montotransaccion, String llavedosificacion) {
        CodigoControl7 codigocontrol = new CodigoControl7();
        codigocontrol.setNumeroAutorizacion(numeroautorizacion);
        codigocontrol.setNumeroFactura(numerofactura);
        codigocontrol.setNitci(nit);

        codigocontrol.setFechaTransaccion(fechatransaccion);
        int entero = montotransaccion.intValue();
        BigDecimal resta = montotransaccion;
        //double resta = montotransaccion - entero;
        //resta = montotransaccion;
        resta = resta.subtract(new BigDecimal(entero));
        if (resta.compareTo(new BigDecimal(0.5)) >= 0) {
            entero++;
        }
        codigocontrol.setMonto(entero);
        codigocontrol.setLlaveDosificacion(llavedosificacion);
        String codigo = codigocontrol.obtener();
        return (codigo);
    }
    
    public void imprimirFactura(Integer idFactura, Integer numFactura, String precioLiteral, Date fechaFactura, String razonSocial,
            BigDecimal monto, String nit, Date fechaLimiteEmision, String codigoControl, String usuario, String numeroAutorizacion, String texto) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("entra a imprimir factura");
        FacturaOficial fOficial = new FacturaOficial();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/Reportes/FacturaOficial.jasper");

        fOficial.getReporte(ruta, idFactura, numFactura, precioLiteral, fechaFactura, razonSocial,
                monto, nit, dosificacion.getFechaLimiteEmision(), codigoControl, usuario, numeroAutorizacion, texto);
        FacesContext.getCurrentInstance().responseComplete();

    }
    
     public void reset() {
        RequestContext.getCurrentInstance().reset(":formAdministrarVenta");
    }

}

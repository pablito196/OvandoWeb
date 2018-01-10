package sys.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import sys.clasesauxiliares.Compracredito;
import sys.clasesauxiliares.Compras;
import sys.clasesauxiliares.ComprasParametrizado;
import sys.clasesauxiliares.DetallecompraFormateada;
import sys.clasesauxiliares.VentasParametrizado;
import sys.dao.AlmacenDAO;
import sys.dao.ArticuloDAO;
import sys.dao.ComprasDAO;
import sys.dao.CronocomprascredDAO;
import sys.dao.DetallecompraDAO;
import sys.dao.EntradaDAO;
import sys.dao.ExistenciaDAO;
import sys.dao.PagocompracreditoDAO;
import sys.dao.ProveedorDAO;
import sys.dao.SalidaDAO;
import sys.dao.UsuarioDAO;
import sys.imp.AlmacenDAOImp;
import sys.imp.ArticuloDAOImp;
import sys.imp.ComprasDAOImp;
import sys.imp.CronocomprascredDAOImp;
import sys.imp.DetallecompraDAOImp;
import sys.imp.EntradaDAOImp;
import sys.imp.ExistenciaDAOImp;
import sys.imp.PagocompracreditoDAOImp;
import sys.imp.ProveedorDAOImp;
import sys.imp.SalidaDAOImp;
import sys.imp.UsuarioDAOImp;
import sys.model.Almacen;
import sys.model.Articulo;
import sys.model.Compra;
import sys.model.Cronocomprascred;
import sys.model.Detallecompra;
import sys.model.Entrada;
import sys.model.Existencia;
import sys.model.Pagocompracredito;
import sys.model.Proveedor;
import sys.model.Salida;
import sys.model.Tipomoneda;
import sys.model.Tipopago;
import sys.model.Usuario;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class ComprasBEAN {

    Session session = null;
    Transaction transaction = null;

    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;

    private Proveedor proveedor;
    private Detallecompra detalleCompra;
    private Cronocomprascred cronogramaComprasCred;
    private Almacen almacen;
    private Long idCompra;
    private BigDecimal totalCompra;
    private String nitProveedor;

    private Articulo articulo;
    private String codigoArticulo;

    private List<Detallecompra> listaDetalleCompra;
    private List<Compra> listaCompras;
    private List<Compra> listaFiltradaCompras;
    private List<Compra> listaComprasCredito;

    private String cantidadArticulo;
    private BigDecimal costoUnitario;
    private String articuloSeleccionado;
    private Compra compra;

    private String cantidadArticuloPorCodigo;
    private BigDecimal costoUnitarioPorCodigo;

    private String observacionCompra;

    private Usuario usuario;
    private Tipomoneda tipoMoneda;
    private Tipopago tipoPago;

    private Integer idAlmacen;
    private String acuenta;
    private String saldo;
    private String observacionPagoCompra;

    private List<Detallecompra> listaDetallecompraPorCompra;
    private List<DetallecompraFormateada> listaDetallecompraFormateada;
    private List<Compracredito> listaComprascreditoFormateada;

    private Pagocompracredito pagocompra;
    private BigDecimal totalSaldo;
    private String montoaPagar;

    //para reporte de compras
    private Date fechaInicio;
    private Date fechaFin;
    private boolean todosLosProveedores;
    private BigDecimal totalCompraInforme;
    private String estado;
    private Integer idTipopago;
    private Integer idSucursal;

    private List<Proveedor> listaProveedores;
    private List<Compra> lstCompras;

    public ComprasBEAN() {
        this.compra = new Compra();
        this.proveedor = new Proveedor();
        this.detalleCompra = new Detallecompra();
        this.usuario = new Usuario();
        this.tipoMoneda = new Tipomoneda();
        this.tipoPago = new Tipopago();
        this.almacen = new Almacen();
        this.cronogramaComprasCred = new Cronocomprascred();
        pagocompra = new Pagocompracredito();
        this.listaDetalleCompra = new ArrayList<>();
        this.listaDetallecompraPorCompra = new ArrayList<>();
        this.listaDetallecompraFormateada = new ArrayList<>();
        this.listaCompras = new ArrayList<>();
        this.listaComprasCredito = new ArrayList<>();
        this.listaComprascreditoFormateada = new ArrayList<>();

        this.listaProveedores = new ArrayList<>();
        this.lstCompras = new ArrayList<>();
    }

    public loginBEAN getlBean() {
        return lBean;
    }

    public void setlBean(loginBEAN lBean) {
        this.lBean = lBean;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public String getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(String nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public List<Detallecompra> getListaDetalleCompra() {
        return listaDetalleCompra;
    }

    public void setListaDetalleCompra(List<Detallecompra> listaDetalleCompra) {
        this.listaDetalleCompra = listaDetalleCompra;
    }

    public String getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(String cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getArticuloSeleccionado() {
        return articuloSeleccionado;
    }

    public void setArticuloSeleccionado(String articuloSeleccionado) {
        this.articuloSeleccionado = articuloSeleccionado;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public String getCantidadArticuloPorCodigo() {
        return cantidadArticuloPorCodigo;
    }

    public void setCantidadArticuloPorCodigo(String cantidadArticuloPorCodigo) {
        this.cantidadArticuloPorCodigo = cantidadArticuloPorCodigo;
    }

    public BigDecimal getCostoUnitarioPorCodigo() {
        return costoUnitarioPorCodigo;
    }

    public void setCostoUnitarioPorCodigo(BigDecimal costoUnitarioPorCodigo) {
        this.costoUnitarioPorCodigo = costoUnitarioPorCodigo;
    }

    public void setListaCompras(List<Compra> listaCompras) {
        this.listaCompras = listaCompras;
    }

    public List<Compra> getListaFiltradaCompras() {
        return listaFiltradaCompras;
    }

    public void setListaFiltradaCompras(List<Compra> listaFiltradaCompras) {
        this.listaFiltradaCompras = listaFiltradaCompras;
    }

    public Detallecompra getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(Detallecompra detalleCompra) {
        this.detalleCompra = detalleCompra;
    }

    public String getObservacionCompra() {
        return observacionCompra;
    }

    public void setObservacionCompra(String observacionCompra) {
        this.observacionCompra = observacionCompra;
    }

    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getAcuenta() {
        return acuenta;
    }

    public void setAcuenta(String acuenta) {
        this.acuenta = acuenta;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getObservacionPagoCompra() {
        return observacionPagoCompra;
    }

    public void setObservacionPagoCompra(String observacionPagoCompra) {
        this.observacionPagoCompra = observacionPagoCompra;
    }

    public Cronocomprascred getCronogramaComprasCred() {
        return cronogramaComprasCred;
    }

    public void setCronogramaComprasCred(Cronocomprascred cronogramaComprasCred) {
        this.cronogramaComprasCred = cronogramaComprasCred;
    }

    public List<Compra> getListaCompras() {
        ComprasDAO dao = new ComprasDAOImp();
        listaCompras = dao.listarCompra();
        return listaCompras;
    }

    public List<Compra> getListaComprasCredito() {
        return listaComprasCredito;
    }

    public void setListaComprasCredito(List<Compra> listaComprasCredito) {
        this.listaComprasCredito = listaComprasCredito;
    }

    public List<Compracredito> getListaComprascreditoFormateada() {
        return listaComprascreditoFormateada;
    }

    public void setListaComprascreditoFormateada(List<Compracredito> listaComprascreditoFormateada) {
        this.listaComprascreditoFormateada = listaComprascreditoFormateada;
    }

    public List<Detallecompra> getListaDetallecompraPorCompra() {
        return listaDetallecompraPorCompra;
    }

    public void setListaDetallecompraPorCompra(List<Detallecompra> listaDetallecompraPorCompra) {
        this.listaDetallecompraPorCompra = listaDetallecompraPorCompra;
    }

    public List<DetallecompraFormateada> getListaDetallecompraFormateada() {
        listarDetalleCompraPorCompra();
        return listaDetallecompraFormateada;
    }

    public void setListaDetallecompraFormateada(List<DetallecompraFormateada> listaDetallecompraFormateada) {
        this.listaDetallecompraFormateada = listaDetallecompraFormateada;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Pagocompracredito getPagocompra() {
        return pagocompra;
    }

    public void setPagocompra(Pagocompracredito pagocompra) {
        this.pagocompra = pagocompra;
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

    //METODOS
    //metodo para solicitar cantidad y precio del articulo seleccionado
    public void solicitarCantidadPrecioArticulo(String codArticulo) {
        this.articuloSeleccionado = codArticulo;
    }

    //Metodo para mostrar el dialogCantidadArticuloporCodigo
    public void mostrarCantidadArticuloporCodigo() {
        this.session = null;
        this.transaction = null;

        try {

            if (this.codigoArticulo.equals("")) {
                return;
            }
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();
            DetallecompraDAO dcDao = new DetallecompraDAOImp();
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del articulo en la variable objeto articulo segun el codigoarticulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.codigoArticulo);

            if (this.articulo != null) {

                //Detallecompra detcompra = new Detallecompra();
                //detcompra = dcDao.buscarUltimaCompraDetallePorArticulo(this.session, this.codigoArticulo);
                System.out.println("codigo articulo encontrado: " + this.articulo.getCodigoArticulo());
                System.out.println("nombre articulo encontrado: " + this.articulo.getNombre());

                this.detalleCompra = dcDao.buscarUltimaCompraDetallePorArticulo(this.session, this.codigoArticulo);
                this.costoUnitarioPorCodigo = this.detalleCompra.getCostoUnitario();
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

    //metodo para agragar los datos de articulo por codigo de articulo
    public void buscarDatosArticulo() {

        System.out.println("cantidad unit: " + this.cantidadArticuloPorCodigo);
        System.out.println("costo unit: " + this.costoUnitarioPorCodigo);
        /* this.cantidadArticuloPorCodigo = (float)6.00;
         this.costoUnitarioPorCodigo = new BigDecimal(6);*/

        /*this.listaDetalleCompra.add(new Detallecompra(this.articulo, null, this.cantidadArticuloPorCodigo, this.costoUnitarioPorCodigo,
         BigDecimal.valueOf(this.cantidadArticuloPorCodigo * this.costoUnitarioPorCodigo.floatValue())));
         this.cantidadArticuloPorCodigo = null;
         this.costoUnitarioPorCodigo = null;
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del artículo agregados"));*/
        //this.cantidadArticuloPorCodigo = (float)5.00;
        this.costoUnitarioPorCodigo = new BigDecimal(5);
        //     this.listaDetalleCompra.add(new Detallecompra(this.articulo, null, this.cantidadArticuloPorCodigo, this.costoUnitarioPorCodigo,
        //          BigDecimal.valueOf(this.cantidadArticuloPorCodigo * this.costoUnitarioPorCodigo.floatValue())));
        this.cantidadArticuloPorCodigo = null;
        this.costoUnitarioPorCodigo = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del artículo agregados"));

    }

    //metodo para calcular el total
    public void calcularTotalCompra() {
        this.totalCompra = new BigDecimal(0);
        try {
            for (Detallecompra item : listaDetalleCompra) {
                BigDecimal totalCompraPorArticulo = item.getCostoUnitario().multiply(new BigDecimal(item.getCantidad()));
                item.setCostoTotal(totalCompraPorArticulo);
                totalCompra = totalCompra.add(totalCompraPorArticulo);
            }

            this.compra.setMontoCompra(totalCompra);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //metodo para generar el numero de compra
    public void numeracionCompras() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            ComprasDAO cDao = new ComprasDAOImp();
            //verificar si hay registros en la tabla Compra de la BD

            // System.out.println("cantidad registros compra: "+cDao.obtenerTotalRegistrosCompra(this.session));
            this.idCompra = cDao.obtenerTotalRegistrosCompra(this.session);

            if (this.idCompra <= 0 || this.idCompra == null) {
                this.idCompra = Long.valueOf("1");
            } else {
                //recuperamos el ultimo registro q existe en la bd
                this.compra = cDao.obtenerUltimoRegistro(this.session);
                this.compra.setNumeroRecibo("0");
                this.compra.setFacturaCompra("0");
                this.idCompra = Long.valueOf(this.compra.getIdCompra() + 1);

                //limpiar la variable totalPreventa
                this.totalCompra = new BigDecimal("0");
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

    //Metodo para mostrar los datos de cliente por medio del campo de texto cicliente
    public void agregarDatosProveedortxtnitProveedor() {
        this.session = null;
        this.transaction = null;

        try {

            if (this.nitProveedor.equals("")) {
                return;
            }
            this.session = HibernateUtil.getSessionFactory().openSession();
            ProveedorDAO pDao = new ProveedorDAOImp();
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del proveedor en la variable objeto proveedor
            this.proveedor = pDao.obtenerProveedorPorNit(this.session, this.nitProveedor);
            if (this.proveedor != null) {
                this.nitProveedor = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del proveedor agregados"));
                listarCompraCredito(this.session);
                //calcularTotalSaldo();
            } else {
                this.nitProveedor = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Proveedor no encontrado"));
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

    //Metodo para mostrar los datos de proveeor por medio del dialog proveedor
    public void agregarDatosProveedor(String nit) {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ProveedorDAO pDao = new ProveedorDAOImp();
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del proveedor en la variable objeto proveedor
            this.proveedor = pDao.obtenerProveedorPorNit(session, nit);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del proveedor agregados"));
            listarCompraCredito(this.session);
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

    //Metodo para mostrar los datos de articulo por medio del campo de texto codigoarticulo
    public void agregarDatosArticulotxtcodigoArticulo() {

        if (this.cantidadArticuloPorCodigo.equals("0") || this.cantidadArticuloPorCodigo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticuloPorCodigo = "";
        } else {

            if (this.costoUnitarioPorCodigo == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese el costo unitario"));
                this.costoUnitarioPorCodigo = null;
            } else {

                this.listaDetalleCompra.add(new Detallecompra(this.articulo, null, Float.parseFloat(this.cantidadArticuloPorCodigo), this.costoUnitarioPorCodigo,
                        this.costoUnitarioPorCodigo.multiply(new BigDecimal(this.cantidadArticuloPorCodigo))));
                //this.codigoArticulo = "";
                this.cantidadArticuloPorCodigo = "";
                this.costoUnitarioPorCodigo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo calcularTotalCompra
                this.calcularTotalCompra();
                //   this.transaction.commit();
                this.articulo = null;
            }

        }

    }

    //Metodo para mostrar los datos de cliente por medio del dialog articulo
    public void agregarDatosArticulo() {

        if (this.cantidadArticulo.equals("0") || this.cantidadArticulo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticulo = "";
        } else {

            if (this.costoUnitario == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese el costo unitario"));
                this.costoUnitario = null;
            } else {

                System.out.println("codigo articulo detalle: " + this.articulo.getNombre());
                this.listaDetalleCompra.add(new Detallecompra(this.articulo, null, Float.parseFloat(this.cantidadArticulo), this.costoUnitario,
                        this.costoUnitario.multiply(new BigDecimal(this.cantidadArticulo))));

                //this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo calcularTotalCompra
                this.calcularTotalCompra();

                this.cantidadArticulo = "";
                this.costoUnitario = null;
                this.articulo = null;
            }

        }

        ///*****
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
            DetallecompraDAO dcDao = new DetallecompraDAOImp();

            this.transaction = this.session.beginTransaction();
            //Obtener los datos del articulo en la variable objeto articulo segun el codigo de articulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.articuloSeleccionado);

            this.detalleCompra = dcDao.buscarUltimaCompraDetallePorArticulo(this.session, this.articuloSeleccionado);
            this.costoUnitario = this.detalleCompra.getCostoUnitario();

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

    //metodo para quitar articulo del detalle de compra
    public void quitarArticuloDetallePreventa(String codArticulo, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Detallecompra item : this.listaDetalleCompra) {
                if (item.getArticulo().getCodigoArticulo().equals(codArticulo) && filaSeleccionada.equals(i)) {
                    this.listaDetalleCompra.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Artículo quitado de la compra"));

            this.calcularTotalCompra();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    //metodo para guardar la Compra
    public void guardarCompra(Integer idTipopago) {
        this.session = null;
        this.transaction = null;

        //Almacen almacen;
        int idusuario = lBean.getUsuario().getIdUsuario();
        this.usuario.setIdUsuario(idusuario);
        this.tipoMoneda.setIdTipomoneda(1);
        this.tipoPago.setIdTipopago(idTipopago);

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();
            ComprasDAO cDao = new ComprasDAOImp();
            DetallecompraDAO dcDao = new DetallecompraDAOImp();

            AlmacenDAO alDao = new AlmacenDAOImp();
            EntradaDAO eDao = new EntradaDAOImp();
            ExistenciaDAO exDao = new ExistenciaDAOImp();

            java.sql.Date fechacompra = new java.sql.Date(this.compra.getFechaCompra().getTime());

            this.transaction = this.session.beginTransaction();
            this.almacen = alDao.buscarAlmacen(this.session, this.idAlmacen);
            //datos para guardar en la tabla compra de la bd
            this.compra.setProveedor(this.proveedor);
            this.compra.setUsuario(this.usuario);
            this.compra.setTipomoneda(this.tipoMoneda);
            this.compra.setTipopago(this.tipoPago);
            this.compra.setEstado("VIGENTE");
            this.compra.setFechaCompra(fechacompra);
            this.compra.setTipoCambio(new BigDecimal(6.96));
            this.compra.setObservacionCompra(this.observacionCompra);
            this.compra.setAlmacen(this.almacen);

            //hacemos el insert a la tabla compra
            cDao.guardarCompra(this.session, this.compra);
            //recuperar el ultimo registro de la tabla compra
            this.compra = cDao.obtenerUltimoRegistro(this.session);
            //recorremos el arraylist para guardar el registro del detalle de compra en la bd
            for (Detallecompra item : listaDetalleCompra) {

                this.articulo = aDao.obtenerArticuloPorCodigo(this.session, item.getArticulo().getCodigoArticulo());

                item.setCompra(this.compra);
                item.setArticulo(this.articulo);

                //hacemos el insert en la tabla detallecompra de la BD
                dcDao.guardarDetalleCompra(this.session, item);

                //ENTRADA EN ALMACEN
                Entrada entrada = new Entrada();
                entrada.setArticulo(this.articulo);
                entrada.setFechaEntrada(fechacompra);
                entrada.setCantidad(item.getCantidad());
                entrada.setObservacion("POR COMPRA Nº " + this.compra.getIdCompra());
                eDao.guardarEntrada(this.session, entrada);

                //EXISTENCIA
                Existencia existencia = new Existencia();

                existencia = exDao.buscarExistenciaPorAlmacen(this.session, this.almacen.getIdAlmacen(), item.getArticulo().getCodigoArticulo());
                if (existencia == null) {
                    existencia = new Existencia();
                    //System.out.println("codigo articulo no existe en almacen");
                    existencia.setAlmacen(almacen);
                    existencia.setArticulo(item.getArticulo());
                    existencia.setCantidad(item.getCantidad());

                    //System.out.println("codigo articulo existente: " + existeEnAlmacen.getArticulo().getCodigoArticulo());
                    exDao.guardarExistencia(this.session, existencia);
                } else {
                    //System.out.println("codigo articulo existente: " + this.existencia.getArticulo().getCodigoArticulo());
                    Float cantidadActualizada = existencia.getCantidad() + item.getCantidad();
                    existencia.setCantidad(cantidadActualizada);

                    //System.out.println("nueva cantidad: " + this.existencia.getCantidad());
                    exDao.actualizarExistencia(this.session, existencia);
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
    }

    //metodo para registrar la compra al contado
    public void registrarCompra() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.guardarCompra(1);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Compra registrada"));
        //visualizarCompra();
        this.limpiarCompra();
    }

    //metodo para registrar una compra a credito
    public void registrarCompraCredito() {
        java.sql.Date fechahoy = new java.sql.Date(new java.util.Date().getTime());
        this.guardarCompra(2);

        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            CronocomprascredDAO crDao = new CronocomprascredDAOImp();
            Cronocomprascred cronogramaCompras = new Cronocomprascred();
            cronogramaCompras.setCompra(this.compra);
            cronogramaCompras.setFechaProgramada(fechahoy);
            cronogramaCompras.setUsuario(this.usuario);
            crDao.guardarCronogramaCompras(this.session, cronogramaCompras);

            cronogramaCompras = new Cronocomprascred();
            cronogramaCompras = crDao.obtenerUltimoRegistro(session);

            if (this.acuenta == null || "".equals(this.acuenta)) {
                this.acuenta = "0";
            }
            Pagocompracredito pagoCompra = new Pagocompracredito();
            PagocompracreditoDAO pcDao = new PagocompracreditoDAOImp();
            pagoCompra.setCronocomprascred(cronogramaCompras);
            pagoCompra.setFechaPago(fechahoy);
            pagoCompra.setMontoPagado(new BigDecimal(this.acuenta));
            pagoCompra.setSaldo(new BigDecimal(this.saldo));
            pagoCompra.setObservacion(this.observacionPagoCompra);
            pagoCompra.setUsuario(this.usuario);
            pcDao.guardarPagoCompras(this.session, pagoCompra);

            java.sql.Date fechaproximopago = new java.sql.Date(this.cronogramaComprasCred.getFechaProgramada().getTime());
            this.cronogramaComprasCred.setCompra(this.compra);
            this.cronogramaComprasCred.setFechaProgramada(fechaproximopago);
            this.cronogramaComprasCred.setUsuario(this.usuario);
            crDao.guardarCronogramaCompras(this.session, this.cronogramaComprasCred);

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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Compra a credito registrada"));
        this.limpiarCompra();
    }

    public void saldoInicial() {
        this.saldo = String.valueOf(this.totalCompra);
    }
//metodo para calcular el saldo

    public void calcularSaldo() {
        if ("".equals(this.acuenta)) {
            this.saldo = String.valueOf(this.totalCompra);
        } else {
            BigDecimal saldoCompra = this.compra.getMontoCompra().subtract(new BigDecimal(this.acuenta)).setScale(2, RoundingMode.HALF_EVEN);
            this.saldo = String.valueOf(saldoCompra);
        }
    }

    //metodo para limpiar la compra
    public void limpiarCompra() {
        this.proveedor = new Proveedor();
        this.compra = new Compra();
        this.listaDetalleCompra = new ArrayList<>();
        this.idCompra = null;
        this.totalCompra = new BigDecimal(0);
        this.observacionCompra = "";
        this.observacionPagoCompra = "";
        this.nitProveedor = "";
        //this.idAlmacen = 0;
        //invocar al metodo para desactivar controles
        this.disableButton();
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    //metodos para activar o desactivar los controles en la compra
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

        this.calcularTotalCompra();
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se realizó la modificación de la cantidad"));
    }

    //PARA ADMINISTRAR COMPRA
    public void listarDetalleCompraPorCompra() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            DetallecompraDAO dcDao = new DetallecompraDAOImp();

            this.transaction = this.session.beginTransaction();
            this.listaDetallecompraPorCompra = dcDao.listarDetalleCompraPorCompra(this.session, this.compra.getIdCompra());

            if (this.listaDetallecompraPorCompra.size() > 0) {
                this.listaDetallecompraFormateada = new ArrayList<>();
                for (Detallecompra item : listaDetallecompraPorCompra) {

                    this.listaDetallecompraFormateada.add(new DetallecompraFormateada(item.getIdDetalleCompra(), item.getCompra().getIdCompra(), item.getArticulo().getCodigoArticulo(),
                            item.getArticulo().getNombre(), item.getArticulo().getDescripcion(), item.getCantidad(),
                            item.getCostoUnitario(), item.getCostoTotal()));
                }
            } else {
                this.listaDetallecompraFormateada = new ArrayList<>();
            }
            this.listaDetallecompraPorCompra = null;

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

    //metodo para anular la compra
    public void anularCompra() {
        this.session = null;
        this.transaction = null;
        //System.out.println("estado de la compra "+this.compra.getEstado());
        if (!"ANULADA".equals(this.compra.getEstado())) {
            java.sql.Date fechahoy = new java.sql.Date(new java.util.Date().getTime());
            try {
                this.session = HibernateUtil.getSessionFactory().openSession();
                DetallecompraDAO dcDao = new DetallecompraDAOImp();
                ComprasDAO cDao = new ComprasDAOImp();
                SalidaDAO sDao = new SalidaDAOImp();
                ExistenciaDAO exDao = new ExistenciaDAOImp();
                this.transaction = this.session.beginTransaction();
                this.compra.setEstado("ANULADA");
                cDao.actualizarCompra(this.compra);

                this.listaDetalleCompra = dcDao.listarDetalleCompraPorCompra(this.session, this.compra.getIdCompra());
                for (Detallecompra item : listaDetalleCompra) {
                    //SALIDA ALMACEN
                    Salida salida = new Salida();
                    salida.setArticulo(item.getArticulo());
                    salida.setFechaSalida(fechahoy);
                    salida.setCantidad(item.getCantidad());
                    salida.setObservacion("POR ANULACIÓN DE COMPRA Nº " + item.getCompra().getIdCompra());
                    sDao.guardarSalida(this.session, salida);

                    //EXISTENCIA
                    Existencia existencia = new Existencia();

                    existencia = exDao.buscarExistenciaPorAlmacen(this.session, this.compra.getAlmacen().getIdAlmacen(), item.getArticulo().getCodigoArticulo());

                    if (existencia != null) {
                        Float cantidadActualizada = existencia.getCantidad() - item.getCantidad();
                        existencia.setCantidad(cantidadActualizada);
                    }

                    exDao.actualizarExistencia(this.session, existencia);

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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Compra anulada"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La compra ya se encuentra anulada"));
        }
    }

    //metodo para listar compras a credito por proveedor
    public void listarCompraCredito(Session session) throws Exception {
        ComprasDAO cDao = new ComprasDAOImp();
        CronocomprascredDAO crDao = new CronocomprascredDAOImp();
        PagocompracreditoDAO pcrDao = new PagocompracreditoDAOImp();
        System.out.println("id Proveedor: " + this.proveedor.getIdProveedor());
        this.listaComprasCredito = cDao.listarCompraCreditoVigentePorProveedor(session, this.proveedor.getIdProveedor());
        this.listaComprascreditoFormateada = new ArrayList<>();
        System.out.println("cantidad de elementos de la lista: " + this.listaComprasCredito.size());
        if (this.listaComprasCredito.size() > 0) {

            System.out.println("entra por el if !!!!");
            for (Compra item : listaComprasCredito) {
                System.out.println("entra por el for !!!!");
                System.out.println("id Compra: " + item.getIdCompra());

                Integer idCronocomprascred = crDao.buscarCronogramaPorCompra(session, item.getIdCompra());

                System.out.println("id Cronograma c: " + idCronocomprascred);

                this.pagocompra = pcrDao.buscarPagoCompra(this.session, idCronocomprascred);

                System.out.println("id Pago credito: " + pagocompra.getIdPagocredito());

                //MONTO PAGADO POR COMPRA
                BigDecimal montoTotalPagado = pcrDao.calcularMontoPagado(this.session, item.getIdCompra());
                System.out.println("monto pagado hasta ahora: " + montoTotalPagado);
                //
                this.listaComprascreditoFormateada.add(new Compracredito(item.getIdCompra(), this.proveedor.getIdProveedor(), idCronocomprascred, pagocompra.getIdPagocredito(), item.getFechaCompra(), item.getMontoCompra(), montoTotalPagado, pagocompra.getSaldo()));
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
            for (Compracredito item : listaComprascreditoFormateada) {
                totalSaldo = totalSaldo.add(item.getSaldo());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //metodo para pagar las compras a credito
    public void pagarCompraCredito() {
        BigDecimal montoPagar = new BigDecimal(this.montoaPagar);
        this.session = null;
        this.transaction = null;
        int idusuario = lBean.getUsuario().getIdUsuario();
        this.usuario.setIdUsuario(idusuario);
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            java.sql.Date fechahoy = new java.sql.Date(new java.util.Date().getTime());
            CronocomprascredDAO crDao = new CronocomprascredDAOImp();
            ComprasDAO cDao = new ComprasDAOImp();
            PagocompracreditoDAO pcDao = new PagocompracreditoDAOImp();
            //Cronocomprascred cronogramaCompras = new Cronocomprascred();
            //***
            Iterator<Compracredito> iterador = listaComprascreditoFormateada.listIterator();

            System.out.println("id usuario: " + this.usuario.getIdUsuario());

            while (iterador.hasNext() && montoPagar.compareTo(BigDecimal.ZERO) > 0) {
                Compracredito item = (Compracredito) iterador.next();
                Pagocompracredito pagoCompra = new Pagocompracredito();
                System.out.println("monto pagar: " + montoPagar);
                System.out.println("saldo: " + item.getSaldo());
                if (montoPagar.compareTo(item.getSaldo()) > 0) {
                    System.out.println("id compra: " + item.getIdCompra());
                    pagoCompra.setCronocomprascred(crDao.obtenerUltimoCronogramaPorCompra(this.session, item.getIdCompra()));
                    pagoCompra.setFechaPago(fechahoy);
                    pagoCompra.setMontoPagado(item.getSaldo());
                    pagoCompra.setSaldo(new BigDecimal("0"));
                    pagoCompra.setObservacion(this.observacionPagoCompra);
                    pagoCompra.setUsuario(this.usuario);
                    pcDao.guardarPagoCompras(this.session, pagoCompra);
                    montoPagar = montoPagar.subtract(item.getSaldo());
                    System.out.println("monto pagar: " + montoPagar);
                } else {
                    System.out.println("id compra: " + item.getIdCompra());
                    pagoCompra.setCronocomprascred(crDao.obtenerUltimoCronogramaPorCompra(this.session, item.getIdCompra()));
                    pagoCompra.setFechaPago(fechahoy);
                    pagoCompra.setMontoPagado(montoPagar);
                    System.out.println("saldo: " + item.getSaldo().subtract(montoPagar));
                    pagoCompra.setSaldo(item.getSaldo().subtract(montoPagar));
                    pagoCompra.setObservacion(this.observacionPagoCompra);
                    pagoCompra.setUsuario(this.usuario);
                    pcDao.guardarPagoCompras(this.session, pagoCompra);

                    montoPagar = montoPagar.subtract(item.getSaldo());
                    System.out.println("monto pagar: " + montoPagar);

                    this.compra = cDao.buscarCompra(this.session, item.getIdCompra());
                    this.cronogramaComprasCred.setCompra(this.compra);
                    this.cronogramaComprasCred.setFechaProgramada(fechahoy);
                    this.cronogramaComprasCred.setUsuario(this.usuario);
                    crDao.guardarCronogramaCompras(this.session, this.cronogramaComprasCred);
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
        this.limpiarComprasaCredito();
    }

    //metodo para limpiar compras a credito
    public void limpiarComprasaCredito() {
        this.proveedor = new Proveedor();
        this.compra = new Compra();
        this.idCompra = null;
        this.totalCompra = new BigDecimal(0);
        this.observacionCompra = "";
        this.observacionPagoCompra = "";
        this.nitProveedor = "";
        this.listaComprascreditoFormateada = new ArrayList<>();
        this.pagocompra = new Pagocompracredito();
        this.totalSaldo = new BigDecimal(0);
        this.montoaPagar = "";
        //this.idAlmacen = 0;
        //invocar al metodo para desactivar controles
        this.disableButton();
    }

    //INFORMES DE COMPRAS
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

    public boolean isTodosLosProveedores() {
        return todosLosProveedores;
    }

    public void setTodosLosProveedores(boolean todosLosProveedores) {
        this.todosLosProveedores = todosLosProveedores;
    }

    public BigDecimal getTotalCompraInforme() {
        return totalCompraInforme;
    }

    public void setTotalCompraInforme(BigDecimal totalCompraInforme) {
        this.totalCompraInforme = totalCompraInforme;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdTipopago() {
        return idTipopago;
    }

    public void setIdTipopago(Integer idTipopago) {
        this.idTipopago = idTipopago;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
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

    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public List<Compra> getLstCompras() {
        return lstCompras;
    }

    public void setLstCompras(List<Compra> lstCompras) {
        this.lstCompras = lstCompras;
    }

    //METODOS
    public void activarControlesBusqueda() {
        if (this.todosLosProveedores) {
            this.disableControlesBusquedaButton();

        } else {
            this.enabledControlesBusquedaButton();
        }
    }

    public void agregarProveedoresInforme() {

        try {
            this.listaProveedores.add(this.proveedor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proveedor agregado al detalle de búsqueda"));

            this.proveedor = null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void quitarProveedorListaInforme(String nit, Integer filaSeleccionada) {
        try {
            int i = 0;
            /*System.out.println("ci cliente parametro: " + ciCliente);
             System.out.println("fila seleccionada: " + filaSeleccionada);*/
            for (Proveedor item : this.listaProveedores) {
                //System.out.println("ci cliente lista: " + item.getCiCliente());
                if (item.getNit().equals(nit) && filaSeleccionada.equals(i)) {
                    this.listaProveedores.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Proveedor retirado de la lista"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void buscarCompras() {
        ComprasDAO cDao = new ComprasDAOImp();
        this.session = null;
        this.transaction = null;

        try {
            /*System.out.println("estado: " + this.estado);
             System.out.println("tipo pago: " + this.tipoVenta);
             System.out.println("id Sucursal: " + this.idSucursal);*/
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            if (todosLosProveedores) {
              //PagoventascreditoDAO pcrDao = new PagoventascreditoDAOImp();
                //FacturaDAO fDao = new FacturaDAOImp();

                //System.out.println("antes de pasar el parametro");
                //System.out.println("fecha inicial: " + this.fechaInicio);
                //System.out.println("fecha final: " + this.fechaFin);
                System.out.println("para todos los proveedores");

                this.lstCompras = cDao.buscarCompra(this.session, this.fechaInicio, this.fechaFin, this.estado, this.idTipopago);
                //this.listaVentasInforme = vDao.buscarVenta(this.session, this.fechaInicio, this.fechaFin, this.estado, this.tipoVenta, idAlmacen);
                //System.out.println("tamaño lista de ventas: " + this.listaVentasInforme.size());
            } else {
                String proveedores = listarProveedores();
                if (!"".equals(proveedores) || proveedores != null) {
                    this.lstCompras = cDao.buscarComprasPorProveedor(this.session, this.fechaInicio, this.fechaFin, this.estado, this.idTipopago, proveedores);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar por lo menos un proveedor"));
                }
                //this.listaVentasInforme = vDao.listarVentasPorFechaClientes(this.session, this.fechaInicio, this.fechaFin, clientes);
            }
            totalCompraInforme();
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

    public String listarProveedores() {
        String hash = " ( ";
        int i = 0;
        for (Proveedor item : listaProveedores) {
            hash += item.getNit();
            if (i == listaProveedores.size() - 1) {
                hash += ")";
            } else {
                hash += ",";
            }
            i++;
        }

        return hash;
    }
    
    public void totalCompraInforme() {
        this.totalCompraInforme = new BigDecimal(0);
        try {
            for (Compra item : lstCompras) {
                totalCompraInforme = totalCompraInforme.add(item.getMontoCompra());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        
        SimpleDateFormat Formato1 = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaIni = Formato1.format(this.fechaInicio);
        String fechaFi = Formato1.format(this.fechaFin);
        int idusuario = lBean.getUsuario().getIdUsuario();
        UsuarioDAO uDao = new UsuarioDAOImp();
        
        this.session = null;
        this.transaction = null;

        System.out.println("entra a ver reporte");
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            this.usuario = uDao.buscarUsuario(session, idusuario);
            System.out.println("usuario : "+this.usuario.getLoginUsuario());
            String proveedores = "";
            if (!todosLosProveedores) {
                proveedores = listarProveedores();
            } else {
                proveedores = null;
            }
            System.out.println("proveedores: "+proveedores);
            ComprasParametrizado cParametrizado = new ComprasParametrizado();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            
            
            String ruta = servletContext.getRealPath("/Reportes/ComprasParametrizado.jasper");
            System.out.println("ruta: "+ruta);
            System.out.println("fecha inicio: " + fechaIni);
            System.out.println("fecha fin: " + fechaFi);
            cParametrizado.getReporte(ruta, fechaIni, fechaFi, this.estado, this.idTipopago, this.usuario.getLoginUsuario(), proveedores);
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
    //metodo para visualizar compra
    public void visualizarCompra() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

         //Instancia hacia la clase ProveedorDatos        
        Compras pDatos = new Compras();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/Reportes/Compras.jasper");

        pDatos.getReporte(ruta, this.compra.getIdCompra());
        FacesContext.getCurrentInstance().responseComplete();
               
    }
}

package sys.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import sys.clasesauxiliares.ExistenciaSucursal;
import sys.clasesauxiliares.FacturaOficial;
import sys.clasesauxiliares.FacturaOficialPreVenta;
import sys.clasesauxiliares.Numero_a_Letra;
import sys.dao.AlmacenDAO;
import sys.dao.ArticuloDAO;
import sys.dao.ClienteDAO;
import sys.dao.DetallePreventaDAO;
import sys.dao.DosificacionDAO;
import sys.dao.ExistenciaDAO;
import sys.dao.FacturaDAO;
import sys.dao.PreventaDAO;
import sys.dao.PreventafacturaDAO;
import sys.dao.RemitoDAO;
import sys.dao.SalidaDAO;
import sys.dao.SucursalDAO;
import sys.dao.UsuarioDAO;
import sys.imp.AlmacenDAOImp;
import sys.imp.ArticuloDAOImp;
import sys.imp.ClienteDAOImp;
import sys.imp.DetallePreventaDAOImp;
import sys.imp.DosificacionDAOImp;
import sys.imp.ExistenciaDAOImp;
import sys.imp.FacturaDAOImp;
import sys.imp.PreventaDAOImp;
import sys.imp.PreventafacturaDAOImp;
import sys.imp.RemitoDAOImp;
import sys.imp.SalidaDAOImp;
import sys.imp.SucursalDAOImp;
import sys.imp.UsuarioDAOImp;
import sys.impuestos.CodigoControl7;
import sys.model.Almacen;
import sys.model.Articulo;
import sys.model.Cliente;
import sys.model.Detallepreventa;
import sys.model.Dosificacion;
import sys.model.Existencia;
import sys.model.Factura;
import sys.model.Preventa;
import sys.model.Preventafactura;
import sys.model.Remito;
import sys.model.Salida;
import sys.model.Sucursal;
import sys.model.Tipomoneda;
import sys.model.Tipopago;
import sys.model.Usuario;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class PreventaBEAN {

    Session session = null;
    Transaction transaction = null;

    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;

    private Cliente cliente;
    private String ciCliente;

    private Articulo articulo;
    private String codigoArticulo;

    private List<Detallepreventa> listaDetallepreventa;

    private String cantidadArticulo;
    private String articuloSeleccionado;
    private Preventa preventa;
    private BigDecimal precioVentaReal;

    private String cantidadArticuloporCodigo;
    private BigDecimal precioVentaRealporCodigo;

    private Long numeroPreVenta;

    private BigDecimal totalPreventa;

    private Usuario usuario;
    private Tipomoneda tipoMoneda;
    private Tipopago tipoPago;

    private String observaciones;
    private Dosificacion dosificacion;
    private Integer numeroFactura;
    private Factura factura;
    private Preventafactura preventaFactura;

    private String fechaSistema;
    private Double existenciaPorSucursal;
    private Existencia existenciaSalida;

    private boolean remito;
    private String tipoRemito;

    private String nitFactura;
    private String razonSocialFactura;

    private List<Sucursal> listaSucursal;
    private List<ExistenciaSucursal> listaExistenciaSucursal;

    public PreventaBEAN() {
        this.preventa = new Preventa();
        this.listaDetallepreventa = new ArrayList<>();
        this.usuario = new Usuario();
        this.cliente = new Cliente();
        this.tipoMoneda = new Tipomoneda();
        this.tipoPago = new Tipopago();
        this.dosificacion = new Dosificacion();
        this.factura = new Factura();
        this.preventaFactura = new Preventafactura();
        this.existenciaSalida = new Existencia();

        this.nitFactura = "";
        this.razonSocialFactura = "";

        this.listaSucursal = new ArrayList<>();
        this.listaExistenciaSucursal = new ArrayList<>();
    }

    public loginBEAN getlBean() {
        return lBean;
    }

    public void setlBean(loginBEAN lBean) {
        this.lBean = lBean;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
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

    public List<Detallepreventa> getListaDetallepreventa() {
        return listaDetallepreventa;
    }

    public void setListaDetallepreventa(List<Detallepreventa> listaDetallepreventa) {
        this.listaDetallepreventa = listaDetallepreventa;
    }

    public String getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(String cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    public String getArticuloSeleccionado() {
        return articuloSeleccionado;
    }

    public void setArticuloSeleccionado(String articuloSeleccionado) {
        this.articuloSeleccionado = articuloSeleccionado;
    }

    public Preventa getPreventa() {
        return preventa;
    }

    public void setPreventa(Preventa preventa) {
        this.preventa = preventa;
    }

    public BigDecimal getPrecioVentaReal() {
        return precioVentaReal;
    }

    public void setPrecioVentaReal(BigDecimal precioVentaReal) {
        this.precioVentaReal = precioVentaReal;
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

    public Long getNumeroPreVenta() {
        return numeroPreVenta;
    }

    public void setNumeroPreVenta(Long numeroPreVenta) {
        this.numeroPreVenta = numeroPreVenta;
    }

    public BigDecimal getTotalPreventa() {
        return totalPreventa;
    }

    public void setTotalPreventa(BigDecimal totalPreventa) {
        this.totalPreventa = totalPreventa;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Dosificacion getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(Dosificacion dosificacion) {
        this.dosificacion = dosificacion;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Preventafactura getPreventaFactura() {
        return preventaFactura;
    }

    public void setPreventaFactura(Preventafactura preventaFactura) {
        this.preventaFactura = preventaFactura;
    }

    public String getFechaSistema() {
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        this.fechaSistema = (dia + "/" + (mes + 1) + "/" + anio);
        return fechaSistema;
    }

    public Double getExistenciaPorSucursal() {
        return existenciaPorSucursal;
    }

    public void setExistenciaPorSucursal(Double existenciaPorSucursal) {
        this.existenciaPorSucursal = existenciaPorSucursal;
    }

    public Existencia getExistenciaSalida() {
        return existenciaSalida;
    }

    public void setExistenciaSalida(Existencia existenciaSalida) {
        this.existenciaSalida = existenciaSalida;
    }

    public boolean isRemito() {
        return remito;
    }

    public void setRemito(boolean remito) {
        this.remito = remito;
    }

    public String getTipoRemito() {
        return tipoRemito;
    }

    public void setTipoRemito(String tipoRemito) {
        this.tipoRemito = tipoRemito;
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

    public void setRazonSocialFactura(String razonSocialFactura) {
        this.razonSocialFactura = razonSocialFactura;
    }

    public List<Sucursal> getListaSucursal() {
        return listaSucursal;
    }

    public void setListaSucursal(List<Sucursal> listaSucursal) {
        this.listaSucursal = listaSucursal;
    }

    public List<ExistenciaSucursal> getListaExistenciaSucursal() {
        return listaExistenciaSucursal;
    }

    public void setListaExistenciaSucursal(List<ExistenciaSucursal> listaExistenciaSucursal) {
        this.listaExistenciaSucursal = listaExistenciaSucursal;
    }

    //METODOS
    //Metodo para mostrar los datos de cliente por medio del dialog cliente
    public void agregarDatosCliente(String ciCliente) {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ClienteDAO cDao = new ClienteDAOImp();
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del cliente en la variable objeto cliente
            this.cliente = cDao.obtenerClientePorCi(this.session, ciCliente);
            /*this.listaDetalleCompra.add(new Detallecompra(this.articulo, null, this.cantidadArticulo, this.costoUnitario,
             BigDecimal.valueOf(this.cantidadArticulo * this.costoUnitario.floatValue())));*/
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del cliente agregados"));

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

    //Metodo para mostrar los datos de cliente por medio del campo de texto cicliente
    public void agregarDatosClientetxtciCliente() {
        this.session = null;
        this.transaction = null;

        try {

            if (this.ciCliente.equals("")) {
                return;
            }
            this.session = HibernateUtil.getSessionFactory().openSession();
            ClienteDAO cDao = new ClienteDAOImp();
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del cliente en la variable objeto cliente
            this.cliente = cDao.obtenerClientePorCi(this.session, this.ciCliente);
            /*this.listaDetalleCompra.add(new Detallecompra(this.articulo, null, this.cantidadArticulo, this.costoUnitario,
             BigDecimal.valueOf(this.cantidadArticulo * this.costoUnitario.floatValue())));*/
            if (this.cliente != null) {
                this.ciCliente = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del cliente agregados"));
            } else {
                this.ciCliente = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cliente no encontrado"));
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

    //Metodo para solicitar la cantidad de articulos a vender
    public void pedirCantidadProducto(String codArticulo) {
        this.articuloSeleccionado = codArticulo;
        // System.out.println("articulo  seleccionado: "+this.articuloSeleccionado);
        //********
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();
            ExistenciaDAO eDao = new ExistenciaDAOImp();
            ExistenciaDAO exDao = new ExistenciaDAOImp();
            AlmacenDAO alDao = new AlmacenDAOImp();
            int idsucursal = lBean.getIdSucursal();
            Almacen almacen = new Almacen();
            this.transaction = this.session.beginTransaction();
            
            this.existenciaPorSucursal = exDao.totalExistenciaArticuloPorSucursal(this.session, this.articuloSeleccionado, idsucursal);

            almacen = alDao.buscarAlmacenPrincipal(this.session, idsucursal);
            //
            // this.existenciaPorSucursal = 0.0;
            this.existenciaSalida = eDao.buscarExistenciaPorAlmacen(this.session, almacen.getIdAlmacen(), this.articuloSeleccionado);
            //Obtener los datos del articulo en la variable objeto articulo segun el codigo de articulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.articuloSeleccionado);

            //OBTENER LISTADO DE CANTIDADES DEL ARTICULO EN LAS OTRAS SUCURSALES
            listarExistenciaPorSucursal(this.session, this.articuloSeleccionado);

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

        ///*****
        //Obtener los datos del articulo en la variable objeto articulo segun el codigo de articulo
        //this.listaDetallepreventa.add(new Detallepreventa(this.articulo, null, new BigDecimal(0), this.cantidadArticulo, new BigDecimal(0)));
        if (this.cantidadArticulo.equals("0") || this.cantidadArticulo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticulo = "";
        } else {

            if (this.precioVentaReal == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione el precio de venta"));
                this.precioVentaReal = null;
            } else {
                this.listaDetallepreventa.add(new Detallepreventa(this.articulo, null, this.precioVentaReal, Float.parseFloat(this.cantidadArticulo),
                        this.precioVentaReal.multiply(new BigDecimal(this.cantidadArticulo))));

                /*this.listaDetallepreventa.add(new Detallepreventa(this.articulo, null, this.precioVentaReal, Float.parseFloat(this.cantidadArticulo),
                 new BigDecimal(Float.parseFloat(this.cantidadArticulo)*9)));*/
                //this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo total preventa
                this.totalPreventa();

                this.cantidadArticulo = "";
                this.precioVentaReal = null;
                this.articulo = null;
            }

        }

        ///*****
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
            ExistenciaDAO eDao = new ExistenciaDAOImp();
            ExistenciaDAO exDao = new ExistenciaDAOImp();
            AlmacenDAO alDao = new AlmacenDAOImp();
            int idsucursal = lBean.getIdSucursal();
            Almacen almacen = new Almacen();
            this.transaction = this.session.beginTransaction();
            this.existenciaPorSucursal = exDao.totalExistenciaArticuloPorSucursal(this.session, this.codigoArticulo, idsucursal);

            almacen = alDao.buscarAlmacenPrincipal(this.session, idsucursal);
            //Obtener los datos del articulo en la variable objeto articulo segun el codigoarticulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.codigoArticulo);

            this.existenciaSalida = eDao.buscarExistenciaPorAlmacen(this.session, almacen.getIdAlmacen(), this.codigoArticulo);

            if (this.articulo != null) {

                listarExistenciaPorSucursal(this.session, this.codigoArticulo);
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

    //Metodo para mostrar los datos de articulo por medio del campo de texto codigoarticulo
    public void agregarDatosArticulotxtcodigoArticulo() {

        if (this.cantidadArticuloporCodigo.equals("0") || this.cantidadArticuloporCodigo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticuloporCodigo = "";
        } else {

            if (this.precioVentaRealporCodigo == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione el precio de venta"));
                this.precioVentaRealporCodigo = null;
            } else {
                this.listaDetallepreventa.add(new Detallepreventa(this.articulo, null, this.precioVentaRealporCodigo, Float.parseFloat(this.cantidadArticuloporCodigo),
                        this.precioVentaRealporCodigo.multiply(new BigDecimal(this.cantidadArticuloporCodigo))));
                //this.codigoArticulo = "";
                this.cantidadArticuloporCodigo = "";
                this.precioVentaRealporCodigo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo totalPreventa()
                this.totalPreventa();
                //   this.transaction.commit();
                this.articulo = null;
            }

        }

    }

    //metodo para calcular el total de la preventa
    public void totalPreventa() {
        this.totalPreventa = new BigDecimal(0);
        try {
            for (Detallepreventa item : listaDetallepreventa) {
                BigDecimal totalPreventaPorArticulo = item.getPrecioVentaReal().multiply(new BigDecimal(item.getCantidad()));
                item.setTotal(totalPreventaPorArticulo);
                totalPreventa = totalPreventa.add(totalPreventaPorArticulo);
            }
            this.preventa.setMontoTotal(totalPreventa);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //metodo para quitar articulo del detalle de preventa
    public void quitarArticuloDetallePreventa(String codArticulo, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Detallepreventa item : this.listaDetallepreventa) {
                if (item.getArticulo().getCodigoArticulo().equals(codArticulo) && filaSeleccionada.equals(i)) {
                    this.listaDetallepreventa.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Artículo quitado de la preventa"));
            //llamar al metodo totalVenta para actualizar el total de la preventa
            this.totalPreventa();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    //Metodo para editar la cantidad de articulos en el detalle de preventa
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se modificó la cantidad"));
        //llamada al metodo totalVenta para actualizar en base a los cambios en las cantidades de los productos para actualizar el total a vender
        this.totalPreventa();
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se realizó la modificación de la cantidad"));
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

    //metodo para generar el numero de Preventa
    public void numeracionPreVenta() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            PreventaDAO pDao = new PreventaDAOImp();
            //verificar si hay registros en la tabla Preventa de la BD
            this.numeroPreVenta = pDao.obtenerTotalRegistrosPreventa(this.session);

            if (this.numeroPreVenta <= 0 || this.numeroPreVenta == null) {
                this.numeroPreVenta = Long.valueOf("1");
            } else {
                //recuperamos el ultimo registro q existe en la bd
                this.preventa = pDao.obtenerUltimoRegistro(this.session);
                this.numeroPreVenta = Long.valueOf(this.preventa.getNumeroPreVenta() + 1);

                //limpiar la variable totalPreventa
                this.totalPreventa = new BigDecimal("0");
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

    //metodo para limpiar la preventa
    public void limpiarPreventa() {
        this.cliente = new Cliente();
        this.preventa = new Preventa();
        this.listaDetallepreventa = new ArrayList<>();
        this.numeroPreVenta = null;
        this.totalPreventa = null;
        //this.envioRemito = new Remito();
        this.tipoRemito = "0";
        this.observaciones = "";
        this.ciCliente = "";
        //this.observacionPagoVenta = "";
        //invocar al metodo para desactivar controles
        this.disableButton();
    }

    //metodo para obtener el codigo de control de la factura
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

    //metodo para guardar la Preventa
    public void guardarPreventa() {

        this.session = null;
        this.transaction = null;
        //lBean.getUsuario().getIdUsuario()
        //System.out.println("id de usuario: "+lBean.getUsuario().getIdUsuario());
        int idusuario = lBean.getUsuario().getIdUsuario();
        int idsucursal = lBean.getIdSucursal();
        System.out.println("id sucursal:  " + idsucursal);
        this.usuario.setIdUsuario(idusuario);
        this.tipoMoneda.setIdTipomoneda(1);
        this.tipoPago.setIdTipopago(1);
        long tiempo = System.currentTimeMillis();
        java.sql.Date fechaventa = new java.sql.Date(new java.util.Date().getTime());
        try {

            if (this.cliente != null) {
                this.session = HibernateUtil.getSessionFactory().openSession();
                ArticuloDAO aDao = new ArticuloDAOImp();
                PreventaDAO pDao = new PreventaDAOImp();
                DetallePreventaDAO dvDao = new DetallePreventaDAOImp();
                AlmacenDAO alDao = new AlmacenDAOImp();
                SalidaDAO sDao = new SalidaDAOImp();
                ExistenciaDAO exDao = new ExistenciaDAOImp();

                Almacen almacen = new Almacen();
                this.transaction = this.session.beginTransaction();
                almacen = alDao.buscarAlmacenPrincipal(this.session, idsucursal);
                //datos para guardar en la tabla preventa de la bd
                this.preventa.setCliente(this.cliente);
                this.preventa.setUsuario(this.usuario);
                this.preventa.setTipomoneda(tipoMoneda);
                this.preventa.setTipopago(tipoPago);
                this.preventa.setEstado("VIGENTE");
                this.preventa.setTiempoRegistro(tiempo);
                this.preventa.setFechaPreVenta(fechaventa);
                this.preventa.setTipoCambio(new BigDecimal(6.96));
                this.preventa.setImpuesto(0.00);
                this.preventa.setObservaciones(this.observaciones);

                this.preventa.setAlmacen(almacen);
            //****
            /*System.out.println("cicliente: " + this.preventa.getCliente().getCiCliente());
                 System.out.println("tiemporegistro: " + this.preventa.getTiempoRegistro());
                 System.out.println("fecha: " + this.preventa.getFechaPreVenta());
                 System.out.println("montototal: " + this.preventa.getMontoTotal());
                 System.out.println("tipocambio: " + this.preventa.getTipoCambio());
                 System.out.println("impuesto: " + this.preventa.getImpuesto());
                 System.out.println("observaciones: " + this.preventa.getObservaciones());
                 System.out.println("idtipomoneda: " + this.preventa.getTipomoneda().getIdTipomoneda());
                 System.out.println("estado: " + this.preventa.getEstado());
                 System.out.println("idusuario: " + this.preventa.getUsuario().getIdUsuario());
                 System.out.println("idtipopago: " + this.preventa.getTipopago().getIdTipopago());*/

                //****
                //hacemos el insert a la tabla preventa
                pDao.guardarPreventa(this.session, this.preventa);
                //recuperar el ultimo registro de la tabla preventa
                this.preventa = pDao.obtenerUltimoRegistroTiempo(this.session, tiempo);
                //recorremos el arraylist para guardar el registro del detalle de preventa en la bd
                for (Detallepreventa item : listaDetallepreventa) {

                ///*****
               /* this.dosificacion = dDao.buscarDosificacionActiva(this.session);
                
                     BigDecimal totalPreventaPorArticulo = item.getPrecioVentaReal().multiply(new BigDecimal(item.getCantidad()));
                     item.setTotal(totalPreventaPorArticulo);
                     totalPreventa = totalPreventa.add(totalPreventaPorArticulo);
                     String codigoControl = ObtenerCodigoControl(this.dosificacion.getNumeroAutorizacion(), this.numeroFactura, this.ciCliente, fechaventa, this.totalPreventa, codigoArticulo);*/
                    ///*****
                    this.articulo = aDao.obtenerArticuloPorCodigo(this.session, item.getArticulo().getCodigoArticulo());
                    item.setPreventa(this.preventa);
                    item.setArticulo(this.articulo);
                    item.setIdFactura(0);
                    //***
                    System.out.println("numeropreventa: " + item.getPreventa().getNumeroPreVenta());
                    System.out.println("codigo: " + item.getArticulo().getCodigoArticulo());
                    System.out.println("precio venta real: " + item.getPrecioVentaReal());
                    System.out.println("cantidad: " + item.getCantidad());
                    System.out.println("total: " + item.getTotal());
                    System.out.println("idfactura: " + item.getIdFactura());
                //****

                    //hacemos el insert en la tabla detallepreventa de la BD
                    dvDao.guardarDetallePreventa(this.session, item);
                    //SALIDA EN ALMACEN
                    Salida salida = new Salida();
                    salida.setArticulo(this.articulo);
                    salida.setFechaSalida(fechaventa);
                    salida.setCantidad(item.getCantidad());
                    salida.setObservacion("POR PRE VENTA Nº " + this.preventa.getNumeroPreVenta());
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
                /*if(this.remito)
                 {
                 RemitoDAO rDao = new RemitoDAOImp();
                 if(this.tipoRemito.equals("0"))
                 {
                 this.envioRemito.setVenta(this.venta);
                 this.envioRemito.setFechaRemito(fechaventa);
                 this.envioRemito.setTipoRemito("NO VALORADO");
                 this.envioRemito.setTotalRemito(this.venta.getMontoTotal());
                 }
                 else
                 {
                 this.envioRemito.setVenta(this.venta);
                 this.envioRemito.setFechaRemito(fechaventa);
                 this.envioRemito.setTipoRemito("VALORADO");
                 this.envioRemito.setTotalRemito(this.venta.getMontoTotal());
                 }
                 rDao.guardarRemito(this.session, this.envioRemito);
                 }*/
                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Pre venta registrada"));

                /*RequestContext contex = RequestContext.getCurrentInstance();
                 contex.execute("PF('dialogFacturar').show();");*/
                //this.facturar();
                //this.limpiarPreventa();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cliente requerido"));
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

    public void facturar() {
        this.session = null;
        this.transaction = null;

        java.sql.Date fechaFactura = new java.sql.Date(new java.util.Date().getTime());
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            DosificacionDAO dDao = new DosificacionDAOImp();
            DetallePreventaDAO dvDao = new DetallePreventaDAOImp();
            FacturaDAO fDao = new FacturaDAOImp();
            PreventafacturaDAO pfDao = new PreventafacturaDAOImp();
            int idsucursal = lBean.getIdSucursal();
             int idusuario = lBean.getUsuario().getIdUsuario();
            //this.transaction = this.session.beginTransaction();

            this.dosificacion = dDao.buscarDosificacionActiva(this.session, idsucursal);
            if (this.dosificacion != null) {
                //long milis1 = ultimafecha.getTime();
                //long milis2 = fechahoy.getTime();
                //long dif = (milis2 - milis1)/(60 * 1000);
                final long milisegundospordia = 24 * 60 * 60 * 1000;
                long milis1 = this.dosificacion.getFechaLimiteEmision().getTime();
                long milis2 = fechaFactura.getTime();
                long dif = ((milis1 - milis2) / milisegundospordia);

                if (dif >= 0) {
                    if (dif <= this.dosificacion.getTiempoAlerta() && dif > -1) {
                        String mensajefechalimite = "Ud. tiene " + dif + " días para solicitar nueva Dosificación";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", mensajefechalimite));
                    }
                    int cantidadProductosDetalle = listaDetallepreventa.size();
                    int cantidadProductosDetalleAuxiliar = listaDetallepreventa.size();

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
                            Detallepreventa itemdt = listaDetallepreventa.get(j);
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

                        this.preventaFactura = new Preventafactura();

                        this.preventaFactura.setPreventa(this.preventa);
                        this.preventaFactura.setFactura(fact);

                        pfDao.guardarPreventaFactura(this.session, this.preventaFactura);

                        cont = 1;
                        j = aux;

                        while (cantidadProductosDetalleAuxiliar > 0 && cont <= 19) {

                            Detallepreventa itemdt = listaDetallepreventa.get(j);
                            itemdt.setIdFactura(fact.getIdFactura());
                            dvDao.actualizarDetallePreventa(session, itemdt);
                            j++;
                            cont++;
                            cantidadProductosDetalleAuxiliar--;
                        }
                        Numero_a_Letra numeroaletra = new Numero_a_Letra();
                        String precioliteral = numeroaletra.Convertir(String.valueOf(fact.getMonto()), true);
                        
                        UsuarioDAO uDao = new UsuarioDAOImp();
                        Usuario usuario = uDao.buscarUsuarioPorId(this.session, idusuario);
                        
                        this.transaction.commit();

                        //IMPRIMIR FACTURA
                        imprimirFactura(fact.getIdFactura(), fact.getNumeroFactura(), precioliteral, fact.getFechaFactura(), fact.getRazonSocial(), fact.getMonto(), fact.getNitCliente(), this.dosificacion.getFechaLimiteEmision(), fact.getCodigoControl(), usuario.getLoginUsuario(), fact.getNumeroAutorizacion(), this.dosificacion.getTexto());
                        System.out.println("esta dentro del for");
                    }
                    //this.transaction.commit();
                    //***************TERMINA LA MODIFICACION  DE LA FACTURACION
                    //this.transaction.commit();
                    this.limpiarPreventa();
                    System.out.println("llega hasta antes del mensaje");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Factura generada"));

                } else {
                    //mensaje no se puede facturar porq ha excedido la fecha limite de facturacion
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha limite de facturacion a expirado, para facturar debe solicitar nueva dosificación"));
                    this.limpiarPreventa();
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al facturar, no tiene dosificación activa para esta sucursal"));
                this.limpiarPreventa();
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

    //metodo para imprimir la factura
    public void imprimirFactura(Integer idFactura, Integer numFactura, String precioLiteral, Date fechaFactura, String razonSocial,
            BigDecimal monto, String nit, Date fechaLimiteEmision, String codigoControl, String usuario, String numeroAutorizacion, String leyenda) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("entra a imprimir factura");
        FacturaOficialPreVenta fOficial = new FacturaOficialPreVenta();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/Reportes/FacturaOficialPreVenta.jasper");

        fOficial.getReporte(ruta, idFactura, numFactura, precioLiteral, fechaFactura, razonSocial,
                monto, nit, dosificacion.getFechaLimiteEmision(), codigoControl, usuario, numeroAutorizacion, leyenda);
        FacesContext.getCurrentInstance().responseComplete();

    }
    //metodos para activar o desactivar los controles en la preventa
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

    //metodos para activar remito
    private boolean enabledRemito;

    public boolean isEnabledRemito() {
        return enabledRemito;
    }

    public void enabledRemitoButton() {
        enabledRemito = true;
    }

    public void disableRemitoButton() {
        enabledRemito = false;
    }

    //metodo para activar tipo de remito
    public void activarRemito() {
        if (this.remito) {
            this.enabledRemitoButton();
        } else {
            this.disableRemitoButton();
        }
    }

    //metodo para cambiar datos factura
    public void prepararDatosFactura() {
        this.nitFactura = this.cliente.getCiCliente();
        this.razonSocialFactura = this.cliente.getNombresCliente();
    }

    public void listarExistenciaPorSucursal(Session session, String codigoArticulo) throws Exception {
        this.listaExistenciaSucursal = new ArrayList<>();
        SucursalDAO sDao = new SucursalDAOImp();
        ExistenciaDAO exDao = new ExistenciaDAOImp();
        this.listaSucursal = sDao.listarSucursal();
        Double cantidad;
        for (Sucursal item : listaSucursal) {
            cantidad = exDao.totalExistenciaArticuloPorSucursal(session, codigoArticulo, item.getIdSucursal());
            this.listaExistenciaSucursal.add(new ExistenciaSucursal(item.getNombreSucursal(), cantidad));
        }
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset(":formPreVenta");
    }

}

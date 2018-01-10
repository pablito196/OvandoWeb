package sys.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.primefaces.event.SelectEvent;
import sys.clasesauxiliares.DetalleRemito;
import sys.clasesauxiliares.ExistenciaSucursal;
import sys.clasesauxiliares.FacturaOficial;
import sys.clasesauxiliares.Numero_a_Letra;
import sys.dao.AlmacenDAO;
import sys.dao.ArticuloDAO;
import sys.dao.ClienteDAO;
import sys.dao.CronoventascreditoDAO;
import sys.dao.DetallePreventaDAO;
import sys.dao.DetalleventaDAO;
import sys.dao.DosificacionDAO;
import sys.dao.ExistenciaDAO;
import sys.dao.FacturaDAO;
import sys.dao.PagoventascreditoDAO;
import sys.dao.PreventafacturaDAO;
import sys.dao.RemitoDAO;
import sys.dao.SalidaDAO;
import sys.dao.SucursalDAO;
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
import sys.imp.ExistenciaDAOImp;
import sys.imp.FacturaDAOImp;
import sys.imp.PagoventascreditoDAOImp;
import sys.imp.PreventafacturaDAOImp;
import sys.imp.RemitoDAOImp;
import sys.imp.SalidaDAOImp;
import sys.imp.SucursalDAOImp;
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
import sys.model.Existencia;
import sys.model.Factura;
import sys.model.Pagoventascredito;
import sys.model.Remito;
import sys.model.Salida;
import sys.model.Sucursal;
import sys.model.Tipomoneda;
import sys.model.Tipopago;
import sys.model.Usuario;
import sys.model.Venta;
import sys.model.Ventafactura;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class VentadirectaBEAN {

    Session session = null;
    Transaction transaction = null;

    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;

    private Venta venta;
    private Cliente cliente;
    private Articulo articulo;
    private Usuario usuario;
    private Tipomoneda tipoMoneda;
    private Tipopago tipoPago;

    private Existencia existenciaSalida;
    private Cronoventascredito cronogramaVentasCred;
    private Dosificacion dosificacion;
    private Factura factura;
    private Ventafactura ventaFactura;
    private Remito envioRemito;

    private Long numeroVenta;
    private BigDecimal totalVenta;
    private String ciCliente;
    private String fechaSistema;
    private String codigoArticulo;
    private String observaciones;
    private String articuloSeleccionado;
    private BigDecimal precioVentaReal;
    private String cantidadArticulo;
    private BigDecimal precioVentaRealporCodigo;
    private String cantidadArticuloporCodigo;
    private Double existenciaPorSucursal;
    private String saldo;
    private String acuenta;
    private String observacionPagoVenta;
    private Integer numeroFactura;

    private String tipoRemito;
    private boolean remito;

    private List<Detalleventa> listaDetalleventa;

    private String nitFactura;
    private String razonSocialFactura;
    
    private List<Sucursal> listaSucursal;
    private List<ExistenciaSucursal> listaExistenciaSucursal;

    public VentadirectaBEAN() {
        this.venta = new Venta();
        this.cliente = new Cliente();
        this.articulo = new Articulo();
        this.usuario = new Usuario();
        this.tipoMoneda = new Tipomoneda();
        this.tipoPago = new Tipopago();
        this.existenciaSalida = new Existencia();

        this.listaDetalleventa = new ArrayList<>();

        this.cronogramaVentasCred = new Cronoventascredito();
        this.dosificacion = new Dosificacion();
        this.factura = new Factura();
        this.ventaFactura = new Ventafactura();
        this.envioRemito = new Remito();

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

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
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

    public Existencia getExistenciaSalida() {
        return existenciaSalida;
    }

    public void setExistenciaSalida(Existencia existenciaSalida) {
        this.existenciaSalida = existenciaSalida;
    }

    public Cronoventascredito getCronogramaVentasCred() {
        return cronogramaVentasCred;
    }

    public void setCronogramaVentasCred(Cronoventascredito cronogramaVentasCred) {
        this.cronogramaVentasCred = cronogramaVentasCred;
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

    public Remito getEnvioRemito() {
        return envioRemito;
    }

    public void setEnvioRemito(Remito envioRemito) {
        this.envioRemito = envioRemito;
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

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public String getFechaSistema() {
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        this.fechaSistema = (dia + "/" + (mes + 1) + "/" + anio);
        return fechaSistema;
    }

    public void setFechaSistema(String fechaSistema) {
        this.fechaSistema = fechaSistema;
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

    public List<Detalleventa> getListaDetalleventa() {
        return listaDetalleventa;
    }

    public void setListaDetalleventa(List<Detalleventa> listaDetalleventa) {
        this.listaDetalleventa = listaDetalleventa;
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

    public BigDecimal getPrecioVentaRealporCodigo() {
        return precioVentaRealporCodigo;
    }

    public void setPrecioVentaRealporCodigo(BigDecimal precioVentaRealporCodigo) {
        this.precioVentaRealporCodigo = precioVentaRealporCodigo;
    }

    public String getCantidadArticuloporCodigo() {
        return cantidadArticuloporCodigo;
    }

    public void setCantidadArticuloporCodigo(String cantidadArticuloporCodigo) {
        this.cantidadArticuloporCodigo = cantidadArticuloporCodigo;
    }

    public Double getExistenciaPorSucursal() {
        return existenciaPorSucursal;
    }

    public void setExistenciaPorSucursal(Double existenciaPorSucursal) {
        this.existenciaPorSucursal = existenciaPorSucursal;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getAcuenta() {
        return acuenta;
    }

    public void setAcuenta(String acuenta) {
        this.acuenta = acuenta;
    }

    public String getObservacionPagoVenta() {
        return observacionPagoVenta;
    }

    public void setObservacionPagoVenta(String observacionPagoVenta) {
        this.observacionPagoVenta = observacionPagoVenta;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getTipoRemito() {
        return tipoRemito;
    }

    public void setTipoRemito(String tipoRemito) {
        this.tipoRemito = tipoRemito;
    }

    public boolean isRemito() {
        return remito;
    }

    public void setRemito(boolean remito) {
        this.remito = remito;
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
            System.out.println("llega hasta aqui");
            this.existenciaPorSucursal = exDao.totalExistenciaArticuloPorSucursal(this.session, this.codigoArticulo, idsucursal);

            almacen = alDao.buscarAlmacenPrincipal(this.session, idsucursal);
            //Obtener los datos del articulo en la variable objeto articulo segun el codigoarticulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.codigoArticulo);
            //
            // this.existenciaPorSucursal = 0.0;
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

    //metodo para guardar la venta
    public void guardarVenta(Integer idTipopago) {

        System.out.println("entra a guardar venta ");
        this.session = null;
        this.transaction = null;

        int idusuario = lBean.getUsuario().getIdUsuario();
        int idsucursal = lBean.getIdSucursal();
        System.out.println("id sucursal:  " + idsucursal);
        this.usuario.setIdUsuario(idusuario);
        this.tipoMoneda.setIdTipomoneda(1);
        this.tipoPago.setIdTipopago(idTipopago);
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
            this.venta.setNumeroPreVenta(0);

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
            if (this.remito) {
                RemitoDAO rDao = new RemitoDAOImp();
                String tipoRemito;
                if (this.tipoRemito.equals("0")) {
                    this.envioRemito.setVenta(this.venta);
                    this.envioRemito.setFechaRemito(fechaventa);
                    this.envioRemito.setTipoRemito("NO VALORADO");
                    this.envioRemito.setTotalRemito(this.venta.getMontoTotal());
                    tipoRemito = "DetalleRemitoNoValorado";
                } else {
                    this.envioRemito.setVenta(this.venta);
                    this.envioRemito.setFechaRemito(fechaventa);
                    this.envioRemito.setTipoRemito("VALORADO");
                    this.envioRemito.setTotalRemito(this.venta.getMontoTotal());
                    
                    tipoRemito = "DetallerRemito";
                }
                rDao.guardarRemito(this.session, this.envioRemito);
                
                //verRemito(tipoRemito, this.venta.getNumeroVenta());
            }
            this.transaction.commit();

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

    public void registrarVenta() {
        if (!this.cliente.getCiCliente().equals("")) {
            this.guardarVenta(1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Venta registrada"));
            RequestContext contex = RequestContext.getCurrentInstance();
            contex.execute("PF('dialogFacturar').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe registrar el cliente"));
        }

    }

    public void facturar() {
        this.session = null;
        this.transaction = null;

        java.sql.Date fechaFactura = new java.sql.Date(new java.util.Date().getTime());
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            DosificacionDAO dDao = new DosificacionDAOImp();
            DetalleventaDAO dvDao = new DetalleventaDAOImp();
            FacturaDAO fDao = new FacturaDAOImp();
            VentafacturaDAO vfDao = new VentafacturaDAOImp();
            int idsucursal = lBean.getIdSucursal();
            int idusuario = lBean.getUsuario().getIdUsuario();
            this.dosificacion = dDao.buscarDosificacionActiva(this.session, idsucursal);
            //this.transaction = this.session.beginTransaction();
            if (this.dosificacion != null) {

                final long milisegundospordia = 24 * 60 * 60 * 1000;
                long milis1 = this.dosificacion.getFechaLimiteEmision().getTime();
                long milis2 = fechaFactura.getTime();
                long dif = ((milis1 - milis2) / milisegundospordia);

                if (dif >= 0) {

                    if (dif <= this.dosificacion.getTiempoAlerta() && dif > -1) {
                        String mensajefechalimite = "Ud. tiene " + dif + " días para solicitar nueva Dosificación";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", mensajefechalimite));
                    }

                    int cantidadProductosDetalle = listaDetalleventa.size();
                    int cantidadProductosDetalleAuxiliar = listaDetalleventa.size();

                    double cantidadFacturas = cantidadProductosDetalle / 19.0;

                    double diferencia = cantidadFacturas;

                    int j = 0;
                    int aux = 0;
                    int cantidadrealfacturas = (int) cantidadFacturas;

                    diferencia = diferencia - cantidadrealfacturas;

                    //double montoventa = 0;
                    if (diferencia > 0) {
                        cantidadrealfacturas++;
                    }
                    System.out.println("Cantidad real de facturas: " + cantidadrealfacturas);
                    //JOptionPane.showMessageDialog(null, "cant real fact: "+cantidadrealfacturas);
                    for (int i = 1; i <= cantidadrealfacturas; i++) {
                        this.transaction = this.session.beginTransaction();
                        BigDecimal montoventa = new BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN);
                        System.out.println("contador del for: " + i);
                        //JOptionPane.showMessageDialog(null, "entra por el for");
                        aux = j;
                        //JOptionPane.showMessageDialog(null, " valor aux "+aux);
                        int cont = 1;
                        //JOptionPane.showMessageDialog(null, " cantidad registros "+cantidadregistros);
                        while (cantidadProductosDetalle > 0 && cont <= 19) {
                            Detalleventa itemdt = listaDetalleventa.get(j);
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

                        this.ventaFactura.setVenta(this.venta);
                        this.ventaFactura.setFactura(fact);

                        vfDao.guardarVentaFactura(this.session, this.ventaFactura);

                        cont = 1;
                        j = aux;

                        while (cantidadProductosDetalleAuxiliar > 0 && cont <= 19) {

                            Detalleventa itemdt = listaDetalleventa.get(j);
                            itemdt.setIdFactura(fact.getIdFactura());
                            dvDao.actualizarDetalleVenta(session, itemdt);
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
                         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Factura generada"));
                    }

                    
                   // this.transaction.commit();
                   this.limpiarVenta();
                }
                else
                {
                     //mensaje no se puede facturar porq ha excedido la fecha limite de facturacion
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha limite de facturacion a expirado, para facturar debe solicitar nueva dosificación"));
                    this.limpiarVenta();
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al facturar, no tiene dosificación activa para esta sucursal"));
                this.limpiarVenta();
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
            //  dosificacion.CambiarEstadoInicialActivo(numeroautorizacion, 0); se debe realizar la funcion para cambiar el estado de la variable inicial activo

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

    //Metodo para mostrar los datos de cliente por medio del dialog articulo
    public void agregarDatosArticulo() {
        /*this.session = null;
         this.transaction = null;

         try {
         this.session = HibernateUtil.getSessionFactory().openSession();
         ArticuloDAO aDao = new ArticuloDAOImp();
            
         this.transaction = this.session.beginTransaction();
         //Obtener los datos del articulo en la variable objeto articulo segun el codigo de articulo
         this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.articuloSeleccionado);
         this.listaDetallepreventa.add(new Detallepreventa(this.articulo, null, new BigDecimal(0), this.cantidadArticulo, new BigDecimal(0)));
            
         //this.listaDetallepreventa.add(new Detallepreventa(this.articulo, null, this.precioVentaReal, this.cantidadArticulo, 
         //                                        this.precioVentaReal.multiply(new BigDecimal(this.cantidadArticulo))));
            
         this.transaction.commit();
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));
            
         this.cantidadArticulo = null;

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
         }*/

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
                this.listaDetalleventa.add(new Detalleventa(this.articulo, null, this.precioVentaReal, Float.parseFloat(this.cantidadArticulo),
                        this.precioVentaReal.multiply(new BigDecimal(this.cantidadArticulo))));

                /*this.listaDetallepreventa.add(new Detallepreventa(this.articulo, null, this.precioVentaReal, Float.parseFloat(this.cantidadArticulo),
                 new BigDecimal(Float.parseFloat(this.cantidadArticulo)*9)));*/
  
                //this.transaction.commit();
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo total preventa
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

        if (this.cantidadArticuloporCodigo.equals("0") || this.cantidadArticuloporCodigo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticuloporCodigo = "";
        } else {

            if (this.precioVentaRealporCodigo == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione el precio de venta"));
                this.precioVentaRealporCodigo = null;
            } else {
                this.listaDetalleventa.add(new Detalleventa(this.articulo, null, this.precioVentaRealporCodigo, Float.parseFloat(this.cantidadArticuloporCodigo),
                        this.precioVentaRealporCodigo.multiply(new BigDecimal(this.cantidadArticuloporCodigo))));
                //this.codigoArticulo = "";
                this.cantidadArticuloporCodigo = "";
                this.precioVentaRealporCodigo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo totalPreventa()
                this.totalVenta();
                //   this.transaction.commit();
                this.articulo = null;
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

    //metodo para limpiar la venta
    public void limpiarVenta() {
        this.cliente = new Cliente();
        this.venta = new Venta();
        this.listaDetalleventa = new ArrayList<>();
        this.numeroVenta = null;
        this.totalVenta = null;
        this.envioRemito = new Remito();
        this.tipoRemito = "0";
        this.observaciones = "";
        this.observacionPagoVenta = "";
        this.ciCliente = "";
        //invocar al metodo para desactivar controles
        this.disableButton();
        this.disableRemitoButton();
    }

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

    //Metodo para solicitar la cantidad de articulos a vender
    public void pedirCantidadProducto(String codArticulo) {

        this.articuloSeleccionado = codArticulo;
        System.out.println("articulo seleccionado: " + this.articuloSeleccionado);
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

            System.out.println("llega hasta aqui");
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

    //Metodo para editar la cantidad de articulos en el detalle de preventa
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se modificó la cantidad"));
        //llamada al metodo totalVenta para actualizar en base a los cambios en las cantidades de los productos para actualizar el total a vender
        this.totalVenta();
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se realizó la modificación de la cantidad"));
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

    //metodos para venta a credito
    public void saldoInicial() {
        this.saldo = String.valueOf(this.totalVenta);
    }
    //metodo para calcular el saldo

    public void calcularSaldo() {
        if ("".equals(this.acuenta)) {
            this.saldo = String.valueOf(this.totalVenta);
        } else {
            BigDecimal saldoCompra = this.venta.getMontoTotal().subtract(new BigDecimal(this.acuenta)).setScale(2, RoundingMode.HALF_EVEN);
            this.saldo = String.valueOf(saldoCompra);
        }
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    //metodo para registrar una venta a credito
    public void registrarVentaCredito() {
        if (!this.cliente.getCiCliente().equals("")) {
            java.sql.Date fechahoy = new java.sql.Date(new java.util.Date().getTime());
            this.guardarVenta(2);

            this.session = null;
            this.transaction = null;

            try {
                this.session = HibernateUtil.getSessionFactory().openSession();

                CronoventascreditoDAO crDao = new CronoventascreditoDAOImp();
                Cronoventascredito cronogramaVentas = new Cronoventascredito();

                cronogramaVentas.setVenta(this.venta);
                cronogramaVentas.setFechaProgramada(fechahoy);
                cronogramaVentas.setUsuario(this.usuario);

                crDao.guardarCronogramaVentas(this.session, cronogramaVentas);

                cronogramaVentas = new Cronoventascredito();
                cronogramaVentas = crDao.obtenerUltimoRegistro(this.session);

                if (this.acuenta == null || "".equals(this.acuenta)) {
                    this.acuenta = "0";
                }
                Pagoventascredito pagoVenta = new Pagoventascredito();
                PagoventascreditoDAO pcDao = new PagoventascreditoDAOImp();

                pagoVenta.setCronoventascredito(cronogramaVentas);
                pagoVenta.setFechaPago(fechahoy);
                pagoVenta.setMontoPagado(new BigDecimal(this.acuenta));
                pagoVenta.setSaldo(new BigDecimal(this.saldo));
                pagoVenta.setObservacion(this.observacionPagoVenta);
                pagoVenta.setUsuario(this.usuario);
                pcDao.guardarPagoVentas(this.session, pagoVenta);

                java.sql.Date fechaproximopago = new java.sql.Date(this.cronogramaVentasCred.getFechaProgramada().getTime());

                this.cronogramaVentasCred.setVenta(this.venta);
                this.cronogramaVentasCred.setFechaProgramada(fechaproximopago);
                this.cronogramaVentasCred.setUsuario(this.usuario);
                crDao.guardarCronogramaVentas(this.session, cronogramaVentasCred);

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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Venta a credito registrada"));
            this.limpiarVenta();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe registrar el cliente"));
        }

    }
    
    //metodo para imprimir la factura
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
        RequestContext.getCurrentInstance().reset(":formVenta");
    }
    
    //metodo para cambiar datos factura
    public void prepararDatosFactura() {
        this.nitFactura = this.cliente.getCiCliente();
        this.razonSocialFactura = this.cliente.getNombresCliente();
    }
    
    public void verRemito(String reporte, Integer numeroVenta) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

         //Instancia hacia la clase DetalleRemito        
        DetalleRemito dRemito = new DetalleRemito();

        String reporteCompleto = "/Reportes/"+reporte+".jasper";
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath(reporteCompleto);

        dRemito.getReporte(ruta, numeroVenta);
        FacesContext.getCurrentInstance().responseComplete();
               
    }
}

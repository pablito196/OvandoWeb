package sys.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import sys.dao.AlmacenDAO;
import sys.dao.ArticuloDAO;
import sys.dao.CronoventascreditoDAO;
import sys.dao.DetallePreventaDAO;
import sys.dao.DetalleventaDAO;
import sys.dao.EntradaDAO;
import sys.dao.ExistenciaDAO;
import sys.dao.FacturaDAO;
import sys.dao.PagoventascreditoDAO;
import sys.dao.PreventaDAO;
import sys.dao.VentaDAO;
import sys.dao.VentafacturaDAO;
import sys.imp.AlmacenDAOImp;
import sys.imp.ArticuloDAOImp;
import sys.imp.CronoventascreditoDAOImp;
import sys.imp.DetallePreventaDAOImp;
import sys.imp.DetalleventaDAOImp;
import sys.imp.EntradaDAOImp;
import sys.imp.ExistenciaDAOImp;
import sys.imp.FacturaDAOImp;
import sys.imp.PagoventascreditoDAOImp;
import sys.imp.PreventaDAOImp;
import sys.imp.VentaDAOImp;
import sys.imp.VentafacturaDAOImp;
import sys.model.Almacen;
import sys.model.Articulo;
import sys.model.Cronoventascredito;
import sys.model.Detallepreventa;
import sys.model.Detalleventa;
import sys.model.Entrada;
import sys.model.Existencia;
import sys.model.Factura;
import sys.model.Pagoventascredito;
import sys.model.Preventa;
import sys.model.Preventafactura;
import sys.model.Tipopago;
import sys.model.Venta;
import sys.model.Ventafactura;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class ClientesEsperaBEAN {

    Session session = null;
    Transaction transaction = null;

    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;

    private Preventa preventa;
    private Venta venta;
    private Existencia existenciaSalida;
    private Articulo articulo;
    private Cronoventascredito cronogramaVentasCred;
    private Tipopago tipoPago;

    private String codigoArticulo;
    private Double existenciaPorSucursal;
    private String saldo;
    private String acuenta;
    private String observacionPagoVenta;

    private List<Preventa> listaPreventas;
    private List<Preventa> listaFiltradaPreventas;
    private List<Detalleventa> listaDetalleventa;

    public ClientesEsperaBEAN() {
        this.preventa = new Preventa();
        this.venta = new Venta();
        this.cronogramaVentasCred = new Cronoventascredito();
        this.tipoPago = new Tipopago();
        this.listaPreventas = new ArrayList<>();
        this.listaDetalleventa = new ArrayList<>();
    }

    public loginBEAN getlBean() {
        return lBean;
    }

    public void setlBean(loginBEAN lBean) {
        this.lBean = lBean;
    }

    public Preventa getPreventa() {
        return preventa;
    }

    public void setPreventa(Preventa preventa) {
        this.preventa = preventa;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Existencia getExistenciaSalida() {
        return existenciaSalida;
    }

    public void setExistenciaSalida(Existencia existenciaSalida) {
        this.existenciaSalida = existenciaSalida;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Cronoventascredito getCronogramaVentasCred() {
        return cronogramaVentasCred;
    }

    public void setCronogramaVentasCred(Cronoventascredito cronogramaVentasCred) {
        this.cronogramaVentasCred = cronogramaVentasCred;
    }

    public Tipopago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Tipopago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
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

    public List<Preventa> getListaPreventas() {
        PreventaDAO dao = new PreventaDAOImp();
        listaPreventas = dao.listarPreventas();
        return listaPreventas;
    }

    public void setListaPreventas(List<Preventa> listaPreventas) {
        this.listaPreventas = listaPreventas;
    }

    public List<Preventa> getListaFiltradaPreventas() {
        return listaFiltradaPreventas;
    }

    public void setListaFiltradaPreventas(List<Preventa> listaFiltradaPreventas) {
        this.listaFiltradaPreventas = listaFiltradaPreventas;
    }

    public List<Detalleventa> getListaDetalleventa() {
        listarDetalleVenta();
        return listaDetalleventa;
    }

    public void setListaDetalleventa(List<Detalleventa> listaDetalleventa) {
        this.listaDetalleventa = listaDetalleventa;
    }

    //METODOS
    //metodo para cargar el detalle de venta
    public void listarDetalleVenta() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            DetallePreventaDAO dpvDao = new DetallePreventaDAOImp();
            this.transaction = this.session.beginTransaction();
            System.out.println("numero preventa para el detalle: " + this.preventa.getNumeroPreVenta());
            List<Detallepreventa> listaPreventa = new ArrayList<>();
            listaPreventa = dpvDao.listarDetallepreventaPornumeroPreventa(this.session, this.preventa.getNumeroPreVenta());
            System.out.println("tamaño lista preventa: " + listaPreventa.size());
            this.listaDetalleventa = new ArrayList<>();
            for (Detallepreventa item : listaPreventa) {
                this.listaDetalleventa.add(new Detalleventa(item.getArticulo(), null, item.getPrecioVentaReal(), item.getCantidad(), item.getTotal(), item.getIdFactura()));
            }
            System.out.println("tamaño lista de venta: " + this.listaDetalleventa.size());
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

    //metodo para guardar la venta
    public void guardarVenta(Integer idTipopago) {
        System.out.println("entra a guardar venta");
        /*this.session = null;
        this.transaction = null;*/
        
        Session session1 = null;
        Transaction transaction1 = null;
        
        //lBean.getUsuario().getIdUsuario()
        //System.out.println("id de usuario: "+lBean.getUsuario().getIdUsuario());
        this.tipoPago.setIdTipopago(idTipopago);
        long tiempo = System.currentTimeMillis();
        try {
            System.out.println("antes del open session");
            
            System.out.println("despues del open session");
            VentaDAO vDao = new VentaDAOImp();
            PreventaDAO pDao = new PreventaDAOImp();
            DetalleventaDAO dvDao = new DetalleventaDAOImp();
            VentafacturaDAO vfDao = new VentafacturaDAOImp();
            FacturaDAO fDao = new FacturaDAOImp();

            

            //datos para guardar en la tabla preventa de la bd
            this.venta.setNumeroPreVenta(this.preventa.getNumeroPreVenta());
            this.venta.setCliente(this.preventa.getCliente());
            this.venta.setUsuario(this.preventa.getUsuario());
            this.venta.setTipomoneda(this.preventa.getTipomoneda());
            this.venta.setTipopago(this.tipoPago);
            this.venta.setEstado("VENDIDO");
            this.venta.setTiempoRegistro(tiempo);
            this.venta.setFechaVenta(this.preventa.getFechaPreVenta());
            this.venta.setTipoCambio(this.preventa.getTipoCambio());
            this.venta.setImpuesto(0.00);
            this.venta.setObservaciones(this.preventa.getObservaciones());
            this.venta.setAlmacen(this.preventa.getAlmacen());
            this.venta.setMontoTotal(this.preventa.getMontoTotal());

            session1 = HibernateUtil.getSessionFactory().openSession();
            transaction1 = session1.beginTransaction();
            
            /*this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();*/
            
            //hacemos el insert a la tabla venta
            vDao.guardarVenta(session1, this.venta);

            /*this.preventa.setEstado("VENDIDO");
            pDao.actualizarPreventa(this.session, this.preventa);
            //recuperar el ultimo registro de la tabla venta
            this.venta = vDao.obtenerUltimoRegistroTiempo(this.session, tiempo);
            //recorremos el arraylist para guardar el registro del detalle de venta en la bd
            System.out.println("tamaño lista venta para guardar: " + this.listaDetalleventa.size());

            Integer idFactura = -1;
            for (Detalleventa item : listaDetalleventa) {

                item.setVenta(this.venta);
                //hacemos el insert en la tabla detalleventa de la BD
                dvDao.guardarDetalleVenta(this.session, item);

                if (!Objects.equals(idFactura, item.getIdFactura()) && item.getIdFactura() > 0) {
                    Ventafactura ventaFactura = new Ventafactura();
                    Factura factura = new Factura();
                    factura = fDao.buscarFactura(this.session, item.getIdFactura());
                    ventaFactura.setVenta(this.venta);
                    ventaFactura.setFactura(factura);
                    idFactura = item.getIdFactura();
                    vfDao.guardarVentaFactura(this.session, ventaFactura);
                }

            }*/

            //this.transaction.commit();
            transaction1.commit();

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

    //metodo para registrar una venta al contado

    public void registrarVentaContado() {
        System.out.println("entra a venta a contado");
        this.guardarVenta(1);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Venta registrada"));
        this.limpiarVenta();
    }

    //metodo para registrar una venta a credito

    public void registrarVentaCredito() {

        this.listarDetalleVenta();
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
            cronogramaVentas.setUsuario(this.venta.getUsuario());

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
            pagoVenta.setUsuario(this.venta.getUsuario());
            pcDao.guardarPagoVentas(this.session, pagoVenta);

            java.sql.Date fechaproximopago = new java.sql.Date(this.cronogramaVentasCred.getFechaProgramada().getTime());

            this.cronogramaVentasCred.setVenta(this.venta);
            this.cronogramaVentasCred.setFechaProgramada(fechaproximopago);
            this.cronogramaVentasCred.setUsuario(this.venta.getUsuario());
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

    }

    //metodo para limpiar la venta
    public void limpiarVenta() {

        this.preventa = new Preventa();
        this.listaDetalleventa = new ArrayList<>();
        this.venta = new Venta();
    }

    public void calcularSaldo() {
        if ("".equals(this.acuenta)) {
            this.saldo = String.valueOf(this.preventa.getMontoTotal());
        } else {
            BigDecimal saldoCompra = this.preventa.getMontoTotal().subtract(new BigDecimal(this.acuenta)).setScale(2, RoundingMode.HALF_EVEN);
            this.saldo = String.valueOf(saldoCompra);
        }
    }

    public void establecerSaldoInicial() {
        this.saldo = String.valueOf(this.preventa.getMontoTotal());
    }

    //metodo para anular la preventa
    public void anularPreventa() {
        //this.listarDetalleVenta();
        this.session = null;
        this.transaction = null;

        if (!"ANULADA".equals(this.preventa.getEstado())) {
            java.sql.Date fechahoy = new java.sql.Date(new java.util.Date().getTime());
            try {
                this.session = HibernateUtil.getSessionFactory().openSession();
                DetallePreventaDAO dpvDao = new DetallePreventaDAOImp();
                PreventaDAO pvDao = new PreventaDAOImp();
                EntradaDAO eDao = new EntradaDAOImp();
                ExistenciaDAO exDao = new ExistenciaDAOImp();
                FacturaDAO fDao = new FacturaDAOImp();
                List<Detallepreventa> listaPreventa = new ArrayList<>();
                Integer idFactura = -1;
                this.transaction = this.session.beginTransaction();
                this.preventa.setEstado("ANULADA");
                pvDao.actualizarPreventa(this.session, this.preventa);
                listaPreventa = dpvDao.listarDetallepreventaPornumeroPreventa(this.session, this.preventa.getNumeroPreVenta());
                for (Detallepreventa item : listaPreventa) {
                    //ENTRADA ALMACEN
                    Entrada entrada = new Entrada();
                    entrada.setArticulo(item.getArticulo());
                    entrada.setFechaEntrada(fechahoy);
                    entrada.setCantidad(item.getCantidad());
                    entrada.setObservacion("POR ANULAR PRE VENTA Nº " + item.getPreventa().getNumeroPreVenta());
                    eDao.guardarEntrada(this.session, entrada);

                    //EXISTENCIA
                    Existencia existencia = new Existencia();

                    existencia = exDao.buscarExistenciaPorAlmacen(this.session, this.preventa.getAlmacen().getIdAlmacen(), item.getArticulo().getCodigoArticulo());

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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Pre venta anulada"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La pre venta ya se encuentra anulada"));
        }
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
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

}

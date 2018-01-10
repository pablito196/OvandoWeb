package sys.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import sys.clasesauxiliares.FacturaOficial;
import sys.clasesauxiliares.Numero_a_Letra;
import sys.dao.ClienteDAO;
import sys.dao.DetalleventaDAO;
import sys.dao.DosificacionDAO;
import sys.dao.FacturaDAO;
import sys.dao.UsuarioDAO;
import sys.dao.VentaDAO;
import sys.dao.VentafacturaDAO;
import sys.imp.ClienteDAOImp;
import sys.imp.DetalleventaDAOImp;
import sys.imp.DosificacionDAOImp;
import sys.imp.FacturaDAOImp;
import sys.imp.UsuarioDAOImp;
import sys.imp.VentaDAOImp;
import sys.imp.VentafacturaDAOImp;
import sys.impuestos.CodigoControl7;
import sys.model.Cliente;
import sys.model.Detalleventa;
import sys.model.Dosificacion;
import sys.model.Factura;
import sys.model.Usuario;
import sys.model.Venta;
import sys.model.Ventafactura;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class FacturacionAgrupadaBEAN {

    Session session = null;
    Transaction transaction = null;
    
    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;
    
    private Cliente cliente;
    private Dosificacion dosificacion;
    private Factura factura;
    private Ventafactura ventaFactura;
    
    private String ciCliente;
    private BigDecimal totalVentasNoFacturadas;
    
    private List<Venta> listaVentas;
    private List<Venta> listaVentasSeleccionadas;
    
    private Integer numeroFactura;
    private String nitFactura;
    private String razonSocialFactura;
    
    public FacturacionAgrupadaBEAN() {
        this.cliente = new Cliente();
        this.dosificacion = new Dosificacion();
        this.factura = new Factura();
        this.ventaFactura = new Ventafactura();
        this.listaVentas = new ArrayList<>();
        this.listaVentasSeleccionadas = new ArrayList<>();
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

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public BigDecimal getTotalVentasNoFacturadas() {
        return totalVentasNoFacturadas;
    }

    public void setTotalVentasNoFacturadas(BigDecimal totalVentasNoFacturadas) {
        this.totalVentasNoFacturadas = totalVentasNoFacturadas;
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

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<Venta> getListaVentasSeleccionadas() {
        return listaVentasSeleccionadas;
    }

    public void setListaVentasSeleccionadas(List<Venta> listaVentasSeleccionadas) {
        this.listaVentasSeleccionadas = listaVentasSeleccionadas;
    }
    
    
    //METODOS
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
                listarVentaNoFacturadas(this.session);
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
            listarVentaNoFacturadas(this.session);
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
    //metodo para listar ventas no facturadas
    public void listarVentaNoFacturadas(Session session) throws Exception {
        VentaDAO vDao = new VentaDAOImp();
        
        this.listaVentas = vDao.listarVentasNoFacturadas(this.session, this.cliente.getCiCliente());
        
            
        if (this.listaVentas.size() > 0) {

             
            this.enabledButton();
            this.calcularTotalVentasNoFacturadas();
        } else {
            
            this.disableButton();
            this.calcularTotalVentasNoFacturadas();
        }
    }
    
    //metodo para calcular el total de ventas no facturadas
    public void calcularTotalVentasNoFacturadas() {
        this.totalVentasNoFacturadas = new BigDecimal(0);
        try {
            for (Venta item : listaVentas) {
                totalVentasNoFacturadas = totalVentasNoFacturadas.add(item.getMontoTotal());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    //metodo para limpiar ventas no facturadas
    public void limpiarVentasNoFacturadas() {
        this.cliente = new Cliente();
        this.ciCliente = "";
        this.listaVentas = new ArrayList<>();
        this.totalVentasNoFacturadas = new BigDecimal(0);
        
        //invocar al metodo para desactivar controles
        this.disableButton();
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
    
    public void facturar() {
        this.session = null;
        this.transaction = null;

        java.sql.Date fechaFactura = new java.sql.Date(new java.util.Date().getTime());
        try {
            
            int idsucursal = lBean.getIdSucursal();
            int idusuario = lBean.getUsuario().getIdUsuario();
            
            VentafacturaDAO vfDao = new VentafacturaDAOImp();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            DosificacionDAO dDao = new DosificacionDAOImp();
            DetalleventaDAO dvDao = new DetalleventaDAOImp();
            FacturaDAO fDao = new FacturaDAOImp();
            VentaDAO vDao = new VentaDAOImp();
            
            
            //this.transaction = this.session.beginTransaction();
           
                dosificacion = dDao.buscarDosificacionActiva(this.session, idsucursal);
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
                        List<Detalleventa> listaParaFactura = new ArrayList<>();
                        for (Venta item : listaVentasSeleccionadas) {
                            List<Detalleventa> listaDetalleVenta = dvDao.listarDetalleVentaPorVenta(this.session, item.getNumeroVenta());
                            listaParaFactura.addAll(listaDetalleVenta);
                        }
                                               
                        
                        int cantidadProductosDetalle = listaParaFactura.size();
                        int cantidadProductosDetalleAuxiliar = listaParaFactura.size();

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
                            // cometado 10/08 this.transaction = this.session.beginTransaction();

                            BigDecimal montoventa = new BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN);
                            System.out.println("contador del for: " + i);
                            //JOptionPane.showMessageDialog(null, "entra por el for");
                            aux = j;
                            //JOptionPane.showMessageDialog(null, " valor aux "+aux);
                            int cont = 1;
                            //JOptionPane.showMessageDialog(null, " cantidad registros "+cantidadregistros);
                            while (cantidadProductosDetalle > 0 && cont <= 19) {
                                Detalleventa itemdt = listaParaFactura.get(j);
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
                            System.out.println("luego de numeracion factura");
                            System.out.println("dosificacion: "+this.dosificacion.getNumeroAutorizacion());
                            System.out.println("numero de facura: "+this.numeroFactura);
                            System.out.println("nit factura: "+this.nitFactura);
                            System.out.println("fecha factura: "+fechaFactura);
                            System.out.println("monto venta: "+montoventa);
                            System.out.println("llave dosificacion: "+this.dosificacion.getLlaveDosificacion());
                            String codigoControl = ObtenerCodigoControl(this.dosificacion.getNumeroAutorizacion(), this.numeroFactura, this.nitFactura, fechaFactura, montoventa, this.dosificacion.getLlaveDosificacion());
                            System.out.println("luego de obtener codigo de control");
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
                            
                            /*Venta vent =new Venta();
                            vent = vDao.buscarVenta(this.session, this.ventaFormateada.getNumeroVenta());
                            this.ventaFactura.setVenta(vent);
                            this.ventaFactura.setFactura(fact);

                            vfDao.guardarVentaFactura(this.session, this.ventaFactura);*/

                            cont = 1;
                            j = aux;
                            
                            int[] facturas = new int[100];  
                            int cantele = 0;
                            facturas[0] = 0;

                            while (cantidadProductosDetalleAuxiliar > 0 && cont <= 19) {

                                this.transaction = this.session.beginTransaction();
                                Detalleventa itemdt = listaParaFactura.get(j);
                                itemdt.setIdFactura(fact.getIdFactura());
                                dvDao.actualizarDetalleVenta(session, itemdt);
                                
                                if(!buscarventa(itemdt.getVenta().getNumeroVenta(), facturas, cantele))
                                {
                                    this.ventaFactura.setVenta(itemdt.getVenta());
                                    this.ventaFactura.setFactura(fact);
                                    
                                    vfDao.guardarVentaFactura(this.session, this.ventaFactura);
                                    
                                    facturas[cantele] = itemdt.getVenta().getNumeroVenta();
                                    cantele++;
                                }
                               
                                j++;
                                cont++;
                                cantidadProductosDetalleAuxiliar--;
                                
                                this.transaction.commit();
                            }
                            Numero_a_Letra numeroaletra = new Numero_a_Letra();
                            String precioliteral = numeroaletra.Convertir(String.valueOf(fact.getMonto()), true);

                            //this.transaction.commit();

                             UsuarioDAO uDao = new UsuarioDAOImp();
                        Usuario usuario = uDao.buscarUsuarioPorId(this.session, idusuario);
                        //IMPRIMIR FACTURA
                            imprimirFactura(fact.getIdFactura(), fact.getNumeroFactura(), precioliteral, fact.getFechaFactura(), fact.getRazonSocial(), fact.getMonto(), fact.getNitCliente(), this.dosificacion.getFechaLimiteEmision(), fact.getCodigoControl(), usuario.getLoginUsuario() , fact.getNumeroAutorizacion(), this.dosificacion.getTexto());
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
    boolean buscarventa(int elemento, int[] lista, int cant)
    {
        boolean encontrado = false;
        int i = 0;
        while(encontrado == false && cant >= 0)
        {
            if(elemento == lista[i])
            {
                encontrado = true;
            }
            i++;
            cant--;
        }
        return encontrado;
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
     public void prepararDatosFactura() {
        this.nitFactura = this.cliente.getCiCliente();
        this.razonSocialFactura = this.cliente.getNombresCliente();
    }
     
     public void reset() {
        RequestContext.getCurrentInstance().reset(":formVenta");
    }
}

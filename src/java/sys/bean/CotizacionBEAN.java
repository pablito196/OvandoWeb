
package sys.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import sys.dao.ArticuloDAO;
import sys.dao.ClienteDAO;
import sys.dao.CotizacionDAO;
import sys.dao.DetallecotizacionDAO;
import sys.imp.ArticuloDAOImp;
import sys.imp.ClienteDAOImp;
import sys.imp.CotizacionDAOImp;
import sys.imp.DetallecotizacionDAOImp;
import sys.model.Articulo;
import sys.model.Cliente;
import sys.model.Cotizacion;
import sys.model.Detallecotizacion;
import sys.model.Tipomoneda;
import sys.model.Usuario;
import sys.util.HibernateUtil;


@ManagedBean
@ViewScoped
public class CotizacionBEAN {

    Session session = null;
    Transaction transaction = null;
    
    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;
    
    private Cotizacion cotizacion;
    private Cliente cliente;
    private Articulo articulo;
    private Usuario usuario;
    private Tipomoneda tipoMoneda;
    
    private Long numeroCotizacion;
    private BigDecimal totalCotizacion;
    private String ciCliente;
    private String fechaSistema;
    private String codigoArticulo;
    private String observaciones;
    private String vigencia;
    private String articuloSeleccionado;
    private BigDecimal precioVentaReal;
    private String cantidadArticulo;
    private BigDecimal precioVentaRealporCodigo;
    private String cantidadArticuloporCodigo;
    
    private List<Detallecotizacion> listaDetallecotizacion;
    private List<Cotizacion> listaCotizaciones;
    private List<Cotizacion> listaFiltradaCotizaciones;
    private List<Detallecotizacion> listaDetallecotizacionPorCotizacion;
    
    public CotizacionBEAN() {
        this.cotizacion = new Cotizacion();
        this.cliente = new Cliente();
        this.usuario = new Usuario();
        this.tipoMoneda = new Tipomoneda();
        this.listaDetallecotizacion = new ArrayList<>();
        this.listaCotizaciones = new ArrayList<>();
        this.listaDetallecotizacionPorCotizacion = new ArrayList<>();
    }

    public loginBEAN getlBean() {
        return lBean;
    }

    public void setlBean(loginBEAN lBean) {
        this.lBean = lBean;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
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

    public Long getNumeroCotizacion() {
        return numeroCotizacion;
    }

    public void setNumeroCotizacion(Long numeroCotizacion) {
        this.numeroCotizacion = numeroCotizacion;
    }

    public BigDecimal getTotalCotizacion() {
        return totalCotizacion;
    }

    public void setTotalCotizacion(BigDecimal totalCotizacion) {
        this.totalCotizacion = totalCotizacion;
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
        
        this.fechaSistema = (dia + "/" + (mes+1) +"/"+ anio);
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

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
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

    public List<Detallecotizacion> getListaDetallecotizacion() {
        return listaDetallecotizacion;
    }

    public void setListaDetallecotizacion(List<Detallecotizacion> listaDetallecotizacion) {
        this.listaDetallecotizacion = listaDetallecotizacion;
    }

    public List<Cotizacion> getListaCotizaciones() {
        CotizacionDAO dao = new CotizacionDAOImp();
        listaCotizaciones = dao.listarCotizacion();
        return listaCotizaciones;
    }

    public void setListaCotizaciones(List<Cotizacion> listaCotizaciones) {
        this.listaCotizaciones = listaCotizaciones;
    }

    public List<Cotizacion> getListaFiltradaCotizaciones() {
        return listaFiltradaCotizaciones;
    }

    public void setListaFiltradaCotizaciones(List<Cotizacion> listaFiltradaCotizaciones) {
        this.listaFiltradaCotizaciones = listaFiltradaCotizaciones;
    }

    public List<Detallecotizacion> getListaDetallecotizacionPorCotizacion() {
        listarDetalleCotizacionPorCotizacion();
        return listaDetallecotizacionPorCotizacion;
    }

    public void setListaDetallecotizacionPorCotizacion(List<Detallecotizacion> listaDetallecotizacionPorCotizacion) {
        this.listaDetallecotizacionPorCotizacion = listaDetallecotizacionPorCotizacion;
    }
    
    
    
    //METODOS
     //metodo para generar el numero de Cotizacion
    public void numeracionCotizacion() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            CotizacionDAO cDao = new CotizacionDAOImp();
            //verificar si hay registros en la tabla Preventa de la BD
            this.numeroCotizacion = cDao.obtenerTotalRegistrosCotizacion(session);

            if (this.numeroCotizacion <= 0 || this.numeroCotizacion == null) {
                this.numeroCotizacion = Long.valueOf("1");
            } else {
                //recuperamos el ultimo registro q existe en la bd
                this.cotizacion = cDao.obtenerUltimoRegistro(this.session);
                this.numeroCotizacion = Long.valueOf(this.cotizacion.getNumeroCotizacion() + 1);

                //limpiar la variable totalPreventa
                this.totalCotizacion = new BigDecimal("0");
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
    
    //metodo para calcular el total de la cotizacion
    public void totalCotizacion() {
        this.totalCotizacion = new BigDecimal(0);
        try {
            for (Detallecotizacion item : listaDetallecotizacion) {
                BigDecimal totalCotizacionPorArticulo = item.getPrecio().multiply(new BigDecimal(item.getCantidad()));
                item.setMonto(totalCotizacionPorArticulo);
                totalCotizacion = totalCotizacion.add(totalCotizacionPorArticulo);
            }
            this.cotizacion.setMontoTotal(totalCotizacion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    //metodo para quitar articulo del detalle de cotizacion
    public void quitarArticuloDetalleCotizacion(String codArticulo, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Detallecotizacion item : this.listaDetallecotizacion) {
                if (item.getArticulo().getCodigoArticulo().equals(codArticulo) && filaSeleccionada.equals(i)) {
                    this.listaDetallecotizacion.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Artículo quitado de la cotización"));
            //llamar al metodo totalCotizacion para actualizar el total de la cotizacion
            this.totalCotizacion();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }
    
    //metodo para guardar la Cotizacion
    public void guardarCotizacion() {
        this.session = null;
        this.transaction = null;
        //lBean.getUsuario().getIdUsuario()
        //System.out.println("id de usuario: "+lBean.getUsuario().getIdUsuario());
        int idusuario = lBean.getUsuario().getIdUsuario();
        this.usuario.setIdUsuario(idusuario);
        this.tipoMoneda.setIdTipomoneda(1);
        
        
        java.sql.Date fechacotizacion = new java.sql.Date(new java.util.Date().getTime());
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();
            CotizacionDAO cDao = new CotizacionDAOImp();

            DetallecotizacionDAO dcDao = new DetallecotizacionDAOImp();
            this.transaction = this.session.beginTransaction();
            //datos para guardar en la tabla cotizacion de la bd
            this.cotizacion.setCliente(this.cliente);
            this.cotizacion.setUsuario(this.usuario);
            this.cotizacion.setIdTipomoneda(1);
            this.cotizacion.setEstado("VIGENTE");
            this.cotizacion.setFechaCotizacion(fechacotizacion);
            this.cotizacion.setTipoCambio(new BigDecimal(6.96));
            this.cotizacion.setObservaciones(this.observaciones);
            this.cotizacion.setVigencia(this.vigencia);
            //hacemos el insert a la tabla preventa
            cDao.guardarCotizacion(this.session, this.cotizacion);
            //recuperar el ultimo registro de la tabla cotizacion
            this.cotizacion = cDao.obtenerUltimoRegistro(this.session);
            //recorremos el arraylist para guardar el registro del detalle de cotizacion en la bd
            for (Detallecotizacion item : listaDetallecotizacion) {

                this.articulo = aDao.obtenerArticuloPorCodigo(this.session, item.getArticulo().getCodigoArticulo());
                
                item.setCotizacion(this.cotizacion);
                item.setArticulo(this.articulo);
                
                //hacemos el insert en la tabla detallecotizacion de la BD
                dcDao.guardarDetalleCotizacion(this.session, item);
            }
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cotización registrada"));

            //this.facturar();
            this.limpiarCotizacion();
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
    
    //metodo para limpiar la cotizacion
    public void limpiarCotizacion() {
        this.cliente = new Cliente();
        this.cotizacion = new Cotizacion();
        this.listaDetallecotizacion = new ArrayList<>();
        this.numeroCotizacion = null;
        this.totalCotizacion = null;
        this.ciCliente = "";
        this.observaciones = "";
        this.vigencia = "";
        //invocar al metodo para desactivar controles
        this.disableButton();
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
    
    //Metodo para solicitar la cantidad de articulos a cotizar
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione el precio"));
                this.precioVentaReal = null;
            } else {
                this.listaDetallecotizacion.add(new Detallecotizacion(this.articulo, null,  Float.parseFloat(this.cantidadArticulo), this.precioVentaReal,
                        this.precioVentaReal.multiply(new BigDecimal(this.cantidadArticulo))));

               this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo total cotizacion
                this.totalCotizacion();

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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione el precio"));
                this.precioVentaRealporCodigo = null;
            } else {
                this.listaDetallecotizacion.add(new Detallecotizacion(this.articulo, null, Float.parseFloat(this.cantidadArticuloporCodigo), this.precioVentaRealporCodigo,
                        this.precioVentaRealporCodigo.multiply(new BigDecimal(this.cantidadArticuloporCodigo))));
                //this.codigoArticulo = "";
                this.cantidadArticuloporCodigo = "";
                this.precioVentaRealporCodigo = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle"));

                //llamada al metodo totalCotizacion()
                this.totalCotizacion();
                //   this.transaction.commit();
                this.articulo = null;
            }

        }

    }
    
     //PARA ADMINISTRAR COTIZACION
    public void listarDetalleCotizacionPorCotizacion() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            DetallecotizacionDAO dcDao = new DetallecotizacionDAOImp();

            this.transaction = this.session.beginTransaction();
            this.listaDetallecotizacionPorCotizacion = dcDao.listarDetalleCotizacionPorCotizacion(this.session, this.cotizacion.getNumeroCotizacion());

            for (Detallecotizacion l : listaDetallecotizacionPorCotizacion) {
                Hibernate.initialize(l.getArticulo());
            }
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
     //Metodo para editar la cantidad de articulos en el detalle de cotizacion
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se modificó la cantidad"));
        //llamada al metodo totall para actualizar en base a los cambios en las cantidades de los productos para actualizar el total a vender
        this.totalCotizacion();
    }

    
    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se realizó la modificación de la cantidad"));
    }
    //metodos para activar o desactivar los controles en cotizacion
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }
    
    public void enabledButton()
    {
        enabled = true;
    }
    
    public void disableButton()
    {
        enabled = false;
    }
}

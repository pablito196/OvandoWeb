package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import sys.dao.AlmacenDAO;
import sys.dao.ArticuloDAO;
import sys.dao.EntradaDAO;
import sys.dao.ExistenciaDAO;
import sys.dao.SalidaDAO;
import sys.imp.AlmacenDAOImp;
import sys.imp.ArticuloDAOImp;
import sys.imp.EntradaDAOImp;
import sys.imp.ExistenciaDAOImp;
import sys.imp.SalidaDAOImp;
import sys.model.Almacen;
import sys.model.Articulo;
import sys.model.Entrada;
import sys.model.Existencia;
import sys.model.Salida;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class MovimientoAlmacenesBEAN {

    Session session = null;
    Transaction transaction = null;

    private Salida salida;
    private Existencia existenciaSalida;
    private Existencia existenciaEntrada;
    private Almacen almacen;
    private Entrada entrada;
    private Articulo articulo;

    private Integer idAlmacenSalida;
    private Integer idAlmacenEntrada;
    private String codigoArticulo;
    private String cantidadArticuloporCodigo;
    private String articuloSeleccionado;
    private String cantidadArticulo;
    private String observacion;

    private List<Salida> listaDetalleMovimiento;

    public MovimientoAlmacenesBEAN() {
        articulo = new Articulo();
        almacen = new Almacen();
        idAlmacenEntrada = 0;
        idAlmacenSalida = 0;
        this.listaDetalleMovimiento = new ArrayList<>();
    }

    public Salida getSalida() {
        return salida;
    }

    public void setSalida(Salida salida) {
        this.salida = salida;
    }

    public Existencia getExistenciaSalida() {
        return existenciaSalida;
    }

    public void setExistenciaSalida(Existencia existenciaSalida) {
        this.existenciaSalida = existenciaSalida;
    }

    public Existencia getExistenciaEntrada() {
        return existenciaEntrada;
    }

    public void setExistenciaEntrada(Existencia existenciaEntrada) {
        this.existenciaEntrada = existenciaEntrada;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getIdAlmacenSalida() {
        return idAlmacenSalida;
    }

    public void setIdAlmacenSalida(Integer idAlmacenSalida) {
        this.idAlmacenSalida = idAlmacenSalida;
    }

    public Integer getIdAlmacenEntrada() {
        return idAlmacenEntrada;
    }

    public void setIdAlmacenEntrada(Integer idAlmacenEntrada) {
        this.idAlmacenEntrada = idAlmacenEntrada;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getCantidadArticuloporCodigo() {
        return cantidadArticuloporCodigo;
    }

    public void setCantidadArticuloporCodigo(String cantidadArticuloporCodigo) {
        this.cantidadArticuloporCodigo = cantidadArticuloporCodigo;
    }

    public String getArticuloSeleccionado() {
        return articuloSeleccionado;
    }

    public void setArticuloSeleccionado(String articuloSeleccionado) {
        this.articuloSeleccionado = articuloSeleccionado;
    }

    public String getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(String cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<Salida> getListaDetalleMovimiento() {
        return listaDetalleMovimiento;
    }

    public void setListaDetalleMovimiento(List<Salida> listaDetalleMovimiento) {
        this.listaDetalleMovimiento = listaDetalleMovimiento;
    }

    //metodos
    public void prepararNuevoMovimiento() {
        salida = new Salida();
        existenciaSalida = new Existencia();
        existenciaEntrada = new Existencia();
        almacen = new Almacen();
        entrada = new Entrada();
    }

    //Metodo para solicitar la cantidad de articulos 
    public void pedirCantidadProducto(String codArticulo) {
        this.articuloSeleccionado = codArticulo;

        //********
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();
            ExistenciaDAO eDao = new ExistenciaDAOImp();

            this.transaction = this.session.beginTransaction();
            //Obtener los datos del articulo en la variable objeto articulo segun el codigo de articulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.articuloSeleccionado);

            this.existenciaSalida = eDao.buscarExistenciaPorAlmacen(this.session, this.idAlmacenSalida, this.articuloSeleccionado);
            this.existenciaEntrada = eDao.buscarExistenciaPorAlmacen(this.session, this.idAlmacenEntrada, this.articuloSeleccionado);
            if (this.existenciaSalida == null) {
                this.existenciaSalida.setCantidad(0);
            }

            if (this.existenciaEntrada == null) {
                this.existenciaEntrada.setCantidad(0);
            }

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
            this.transaction = this.session.beginTransaction();
            //Obtener los datos del articulo en la variable objeto articulo segun el codigoarticulo
            this.articulo = aDao.obtenerArticuloPorCodigo(this.session, this.codigoArticulo);

            System.out.println("idAlmacen Salida: " + this.idAlmacenSalida);
            System.out.println("idAlmacen Entrada: " + this.idAlmacenEntrada);

            this.existenciaSalida = eDao.buscarExistenciaPorAlmacen(this.session, this.idAlmacenSalida, this.codigoArticulo);
            this.existenciaEntrada = eDao.buscarExistenciaPorAlmacen(this.session, this.idAlmacenEntrada, this.codigoArticulo);

            if (this.articulo != null) {
                //solicitar mostrar dialogCantidadArticuloporCodigo
                RequestContext contex = RequestContext.getCurrentInstance();
                contex.execute("PF('dialogCantidadArticuloporCodigo').show();");

                this.codigoArticulo = "";
                //this.idAlmacen = 0;
            } else {
                this.codigoArticulo = "";
                //this.idAlmacen = 0;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Artículo no encontrado"));
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

    //Metodo para mostrar los datos de articulo por medio del campo de texto codigoarticulo
    public void agregarDatosArticulotxtcodigoArticulo() {

        if (this.cantidadArticuloporCodigo.equals("0") || this.cantidadArticuloporCodigo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticuloporCodigo = "";
        } else {

            this.listaDetalleMovimiento.add(new Salida(this.articulo, Float.parseFloat(cantidadArticuloporCodigo), null));

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle de movimiento de artículos"));
            this.cantidadArticuloporCodigo = "";
            this.articulo = null;

        }

    }

    //Metodo para mostrar los datos de cliente por medio del dialog articulo
    public void agregarDatosArticulo() {

        if (this.cantidadArticulo.equals("0") || this.cantidadArticulo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticulo = "";
        } else {

            this.listaDetalleMovimiento.add(new Salida(this.articulo, Float.parseFloat(cantidadArticulo), null));

            //this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle de movimiento de artículos"));

            this.cantidadArticulo = "";
            this.articulo = null;

        }

        ///*****
    }

    //metodo para quitar articulo del detalle de salida
    public void quitarArticuloDetalleMovimiento(String codArticulo, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Salida item : this.listaDetalleMovimiento) {
                if (item.getArticulo().getCodigoArticulo().equals(codArticulo) && filaSeleccionada.equals(i)) {
                    this.listaDetalleMovimiento.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Artículo eliminado"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    //metodo para guardar el movimiento de articulos
    public void guardarMovimiento() {
        this.session = null;
        this.transaction = null;

        Existencia existeEnAlmacen;

        java.sql.Date fechaMovimiento = new java.sql.Date(new java.util.Date().getTime());
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();

            AlmacenDAO aDao = new AlmacenDAOImp();
            SalidaDAO sDao = new SalidaDAOImp();
            ExistenciaDAO exDao = new ExistenciaDAOImp();
            EntradaDAO eDao = new EntradaDAOImp();

            this.transaction = this.session.beginTransaction();

            for (Salida item : listaDetalleMovimiento) {

                //SALIDA DE ALMACEN
                item.setFechaSalida(fechaMovimiento);
                item.setObservacion(observacion);

                sDao.guardarSalida(this.session, item);

                this.almacen = aDao.buscarAlmacen(this.session, this.idAlmacenSalida);
                existenciaSalida = exDao.buscarExistenciaPorAlmacen(this.session, this.almacen.getIdAlmacen(), item.getArticulo().getCodigoArticulo());

                if (existenciaSalida == null) {
                    existeEnAlmacen = new Existencia();
                    existeEnAlmacen.setAlmacen(almacen);
                    existeEnAlmacen.setArticulo(item.getArticulo());
                    existeEnAlmacen.setCantidad(-item.getCantidad());
                    exDao.guardarExistencia(this.session, existeEnAlmacen);
                } else {
                    Float cantidadActualizada = existenciaSalida.getCantidad() - item.getCantidad();
                    existenciaSalida.setCantidad(cantidadActualizada);
                    exDao.actualizarExistencia(this.session, existenciaSalida);
                }

                //ENTRADA DE ALMACEN
                
                this.entrada = new Entrada();
                
                this.entrada.setArticulo(item.getArticulo());
                this.entrada.setFechaEntrada(fechaMovimiento);
                this.entrada.setObservacion(this.observacion);
                this.entrada.setCantidad(item.getCantidad());

                System.out.println("codigo articulo entrada: "+this.entrada.getArticulo().getCodigoArticulo());
                System.out.println("cantidad entrada: "+this.entrada.getFechaEntrada());
                
                eDao.guardarEntrada(this.session, this.entrada);

                //System.out.println("id almacen para busqueda: " + this.idAlmacenEntrada);
                this.almacen = aDao.buscarAlmacen(this.session, this.idAlmacenEntrada);

                //System.out.println("almacen: " + almacen.getIdAlmacen());
                //System.out.println("codigo articulo almcen: " + item.getArticulo().getCodigoArticulo());
                this.existenciaEntrada = exDao.buscarExistenciaPorAlmacen(this.session, almacen.getIdAlmacen(), this.entrada.getArticulo().getCodigoArticulo());

                //existeEnAlmacen = exDao.buscarExistenciaPorAlmacen(this.session, this.almacen.getIdAlmacen(), item.getArticulo().getCodigoArticulo());
                if (this.existenciaEntrada == null) {
                    existeEnAlmacen = new Existencia();
                    //System.out.println("codigo articulo no existe en almacen");
                    existeEnAlmacen.setAlmacen(almacen);
                    existeEnAlmacen.setArticulo(this.entrada.getArticulo());
                    existeEnAlmacen.setCantidad(this.entrada.getCantidad());

                    //System.out.println("codigo articulo existente: " + existeEnAlmacen.getArticulo().getCodigoArticulo());

                    exDao.guardarExistencia(this.session, existeEnAlmacen);
                } else {
                    //System.out.println("codigo articulo existente: " + this.existenciaEntrada.getArticulo().getCodigoArticulo());
                    Float cantidadActualizada = this.existenciaEntrada.getCantidad() + item.getCantidad();
                    this.existenciaEntrada.setCantidad(cantidadActualizada);

                    //System.out.println("nueva cantidad: " + this.existenciaEntrada.getCantidad());

                    exDao.actualizarExistencia(this.session, this.existenciaEntrada);
                }

            }
            
           /* for (Salida item1 : listaDetalleMovimiento) {
                
            }*/
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Movimiento de artículos entre almacenes registrado"));

            this.limpiarMovimiento();
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

    //metodo para limpiar la salida
    public void limpiarMovimiento() {
        this.almacen = new Almacen();
        this.salida = new Salida();
        this.entrada = new Entrada();
        this.listaDetalleMovimiento = new ArrayList<>();
        this.codigoArticulo = "";
        this.observacion = "";
        this.idAlmacenEntrada = 0;
        this.idAlmacenSalida = 0;
        //invocar al metodo para desactivar controles
        this.disableButton();
    }

    //metodos para activar o desactivar los controles en el movimiento de articulos
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

    //Metodo para editar la cantidad de articulos en el detalle de salida
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se modificó la cantidad"));

    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se realizó la modificación de la cantidad"));
    }
}

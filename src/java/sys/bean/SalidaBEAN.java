
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
import sys.dao.ExistenciaDAO;
import sys.dao.SalidaDAO;
import sys.imp.AlmacenDAOImp;
import sys.imp.ArticuloDAOImp;
import sys.imp.ExistenciaDAOImp;
import sys.imp.SalidaDAOImp;
import sys.model.Almacen;
import sys.model.Articulo;
import sys.model.Existencia;
import sys.model.Salida;
import sys.util.HibernateUtil;


@ManagedBean
@ViewScoped
public class SalidaBEAN {

    Session session = null;
    Transaction transaction = null;
    
    private String codigoArticulo;
    private String cantidadArticuloporCodigo;
    
    private String articuloSeleccionado;
    private String cantidadArticulo;
    private String observacion;
    
    private Integer idAlmacen;
    
    private Salida salida;
    private Existencia existencia;
    private Almacen almacen;
    private Articulo articulo;
    
    private List<Salida> listaDetalleSalida;
    
    public SalidaBEAN() {
        articulo = new Articulo();
        almacen = new Almacen();
        
        this.listaDetalleSalida = new ArrayList<>();
    }

    public Salida getSalida() {
        return salida;
    }

    public void setSalida(Salida salida) {
        this.salida = salida;
    }

    public Existencia getExistencia() {
        return existencia;
    }

    public void setExistencia(Existencia existencia) {
        this.existencia = existencia;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public List<Salida> getListaDetalleSalida() {
        return listaDetalleSalida;
    }

    public void setListaDetalleSalida(List<Salida> listaDetalleSalida) {
        this.listaDetalleSalida = listaDetalleSalida;
    }

       
    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
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
    
    //metodos
    public void prepararNuevaSalida() {
        salida = new Salida();
        existencia = new Existencia();
        almacen = new Almacen();
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

            System.out.println("idAlmacen: " + this.idAlmacen);

            this.existencia = eDao.buscarExistenciaPorAlmacen(this.session, this.idAlmacen, this.codigoArticulo);

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

            this.listaDetalleSalida.add(new Salida(this.articulo, Float.parseFloat(cantidadArticuloporCodigo), null));
            
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle de salida"));
            this.cantidadArticuloporCodigo = "";
            this.articulo = null;

        }

    }
    
    //metodo para quitar articulo del detalle de salida
    public void quitarArticuloDetalleSalida(String codArticulo, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Salida item : this.listaDetalleSalida) {
                if (item.getArticulo().getCodigoArticulo().equals(codArticulo) && filaSeleccionada.equals(i)) {
                    this.listaDetalleSalida.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Artículo eliminado"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
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

            this.existencia = eDao.buscarExistenciaPorAlmacen(this.session, this.idAlmacen, this.articuloSeleccionado);
            if (this.existencia == null) {
                this.existencia.setCantidad(0);
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
    
    //Metodo para mostrar los datos de cliente por medio del dialog articulo
    public void agregarDatosArticulo() {

        if (this.cantidadArticulo.equals("0") || this.cantidadArticulo.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadArticulo = "";
        } else {

            this.listaDetalleSalida.add(new Salida(this.articulo, Float.parseFloat(cantidadArticulo), null));

            //this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Artículo agregado al detalle de salida"));

            this.cantidadArticulo = "";
            this.articulo = null;

        }

        ///*****
    }
    
    //metodo para guardar la salida
    public void guardarSalida() {
        this.session = null;
        this.transaction = null;

        Existencia existeEnAlmacen;

        java.sql.Date fechasalida = new java.sql.Date(new java.util.Date().getTime());
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            AlmacenDAO aDao = new AlmacenDAOImp();
            SalidaDAO sDao = new SalidaDAOImp();
            ExistenciaDAO exDao = new ExistenciaDAOImp();

            this.transaction = this.session.beginTransaction();

            for (Salida item : listaDetalleSalida) {

                //this.articulo = aDao.obtenerArticuloPorCodigo(this.session, item.getArticulo().getCodigoArticulo());
                item.setFechaSalida(fechasalida);
                item.setObservacion(this.observacion);

                /*System.out.println("codigo articulo: "+item.getArticulo().getCodigoArticulo());
                 System.out.println("fecha entrada: "+item.getFechaEntrada());*/
                sDao.guardarSalida(this.session, item);

                System.out.println("id almacen para busqueda: " + this.idAlmacen);

                this.almacen = aDao.buscarAlmacen(this.session, this.idAlmacen);

                System.out.println("almacen: " + almacen.getIdAlmacen());
                System.out.println("codigo articulo almcen: " + item.getArticulo().getCodigoArticulo());

                this.existencia = exDao.buscarExistenciaPorAlmacen(this.session, almacen.getIdAlmacen(), item.getArticulo().getCodigoArticulo());

                //existeEnAlmacen = exDao.buscarExistenciaPorAlmacen(this.session, this.almacen.getIdAlmacen(), item.getArticulo().getCodigoArticulo());
                if (this.existencia == null) {
                    existeEnAlmacen = new Existencia();
                    System.out.println("codigo articulo no existe en almacen");
                    existeEnAlmacen.setAlmacen(almacen);
                    existeEnAlmacen.setArticulo(item.getArticulo());
                    existeEnAlmacen.setCantidad(-item.getCantidad());

                    System.out.println("codigo articulo existente: " + existeEnAlmacen.getArticulo().getCodigoArticulo());

                    exDao.guardarExistencia(this.session, existeEnAlmacen);
                } else {
                    System.out.println("codigo articulo existente: " + this.existencia.getArticulo().getCodigoArticulo());
                    Float cantidadActualizada = this.existencia.getCantidad() - item.getCantidad();
                    this.existencia.setCantidad(cantidadActualizada);

                    System.out.println("nueva cantidad: " + this.existencia.getCantidad());

                    exDao.actualizarExistencia(this.session, this.existencia);
                }

            }
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Salida registrada"));

            //this.facturar();
            this.limpiarSalida();
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
    
    //metodos para activar o desactivar los controles en la salida de articulos
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
    
    //metodo para limpiar la salida
    public void limpiarSalida() {
        this.almacen = new Almacen();
        this.salida = new Salida();
        this.listaDetalleSalida = new ArrayList<>();
        this.codigoArticulo = "";
        this.observacion = "";
        //invocar al metodo para desactivar controles
        this.disableButton();
    }
}

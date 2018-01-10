package sys.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.clasesauxiliares.ListadePrecios;
import sys.dao.ArticuloDAO;
import sys.dao.CategoriaDAO;
import sys.dao.SubcategoriaDAO;
import sys.dao.UnidadDAO;
import sys.imp.UnidadDAOImp;
import sys.imp.ArticuloDAOImp;
import sys.imp.CategoriaDAOImp;
import sys.imp.SubcategoriaDAOImp;
import sys.model.Articulo;
import sys.model.Categoriaarticulo;
import sys.model.Subcategoriaarticulo;
import sys.model.Usuario;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class ArticuloBEAN {

    Session session;
    Transaction transaction;

    @ManagedProperty("#{loginBEAN}")
    private loginBEAN lBean;

    private List<Articulo> listaArticulos;
    private Articulo articulo;
    private List<Articulo> listaFiltradaArticulo;

    private Integer idSubcategoria;
    private Integer idUnidad;

    private Usuario usuario;

    public ArticuloBEAN() {
        articulo = new Articulo();
        usuario = new Usuario();
        categoria = new Categoriaarticulo();
        subcategoria = new Subcategoriaarticulo();
        idCategoria = 0;
        idSubcategoria = 0;
        codigoArticulo = "";

        //lstCategorias = new ArrayList<>();
        listaPrecios = new ArrayList<>();
    }

    public void setListaArticulos(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public List<Articulo> getListaFiltradaArticulo() {
        return listaFiltradaArticulo;
    }

    public void setListaFiltradaArticulo(List<Articulo> listaFiltradaArticulo) {
        this.listaFiltradaArticulo = listaFiltradaArticulo;
    }

    public List<Articulo> getListaArticulos() {
        ArticuloDAO dao = new ArticuloDAOImp();
        listaArticulos = dao.listarArticulos();
        return listaArticulos;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
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

    //metodos   
    public void prepararNuevoArticulo() {
        articulo = new Articulo();
    }

    public void nuevoArticulo() {
        this.session = null;
        this.transaction = null;
        int idusuario = lBean.getUsuario().getIdUsuario();
        this.usuario.setIdUsuario(idusuario);
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            SubcategoriaDAO scDao = new SubcategoriaDAOImp();
            UnidadDAO uDao = new UnidadDAOImp();
            ArticuloDAO aDao = new ArticuloDAOImp();
            this.transaction = this.session.beginTransaction();
            this.articulo.setSubcategoriaarticulo(scDao.buscarSubcategoria(this.session, this.idSubcategoria));
            this.articulo.setUnidad(uDao.buscarUnidad(this.session, this.idUnidad));
            this.articulo.setUsuario(this.usuario);
            aDao.nuevoArticulo(this.session, this.articulo);
            this.transaction.commit();
            this.articulo = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Articulo registrado"));
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

    public void actualizarArticulo() {
        this.session = null;
        this.transaction = null;
        int idusuario = lBean.getUsuario().getIdUsuario();
        this.usuario.setIdUsuario(idusuario);
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            SubcategoriaDAO scDao = new SubcategoriaDAOImp();
            UnidadDAO uDao = new UnidadDAOImp();
            ArticuloDAO aDao = new ArticuloDAOImp();
            this.transaction = this.session.beginTransaction();
            this.articulo.setSubcategoriaarticulo(scDao.buscarSubcategoria(this.session, this.idSubcategoria));
            this.articulo.setUnidad(uDao.buscarUnidad(this.session, this.idUnidad));
            this.articulo.setUsuario(this.usuario);
            aDao.actualizarArticulo(this.session, this.articulo);
            this.transaction.commit();
            this.articulo = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Articulo modificado"));
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

        articulo = new Articulo();
    }

    public void eliminarArticulo() {
        ArticuloDAO dao = new ArticuloDAOImp();
        dao.eliminarArticulo(articulo);
        articulo = new Articulo();
    }

    //para lista de precios
    private Categoriaarticulo categoria;
    private Subcategoriaarticulo subcategoria;
    private Articulo articulos;
    private Integer idCategoria;
    private String codigoArticulo;
    private Integer idSubcategorias;

    private List<SelectItem> lstCategorias;
    private List<Categoriaarticulo> listaCategorias;
    private List<SelectItem> lstSubCategorias;
    private List<Subcategoriaarticulo> listaSubcategorias;
    private List<SelectItem> lstArticulos;
    private List<Articulo> listArticulos;
    private List<Articulo> listaPrecios;

    //GETERS AND SETERS
    public Categoriaarticulo getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoriaarticulo categoria) {
        this.categoria = categoria;
    }

    public Subcategoriaarticulo getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoriaarticulo subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Articulo getArticulos() {
        return articulos;
    }

    public void setArticulos(Articulo articulos) {
        this.articulos = articulos;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public Integer getIdSubcategorias() {
        return idSubcategorias;
    }

    public void setIdSubcategorias(Integer idSubcategorias) {
        this.idSubcategorias = idSubcategorias;
    }

    public List<SelectItem> getLstCategorias() throws Exception {
        this.lstCategorias = new ArrayList<SelectItem>();
        listarCategoria();
        lstCategorias.clear();
        for (Categoriaarticulo categorias : listaCategorias) {
            SelectItem categoriaItem = new SelectItem(categorias.getIdCategoria(), categorias.getNombreCategoria());
            this.lstCategorias.add(categoriaItem);
        }
        return lstCategorias;

    }

    public void setLstCategorias(List<SelectItem> lstCategorias) {
        this.lstCategorias = lstCategorias;
    }

    public List<Categoriaarticulo> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoriaarticulo> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<SelectItem> getLstSubCategorias() {
        this.lstSubCategorias = new ArrayList<SelectItem>();
        listarSubcategoriaPorCategoria();
        lstSubCategorias.clear();
        for (Subcategoriaarticulo subcategorias : listaSubcategorias) {
            SelectItem subcategoriaItem = new SelectItem(subcategorias.getIdSubcategoria(), subcategorias.getNombreSubCategoria());
            this.lstSubCategorias.add(subcategoriaItem);
        }
        return lstSubCategorias;
    }

    public void setLstSubCategorias(List<SelectItem> lstSubCategorias) {
        this.lstSubCategorias = lstSubCategorias;
    }

    public List<Subcategoriaarticulo> getListaSubcategorias() {
        return listaSubcategorias;
    }

    public void setListaSubcategorias(List<Subcategoriaarticulo> listaSubcategorias) {
        this.listaSubcategorias = listaSubcategorias;
    }

    public List<SelectItem> getLstArticulos() {
        return lstArticulos;
    }

    public void setLstArticulos(List<SelectItem> lstArticulos) {
        this.lstArticulos = new ArrayList<SelectItem>();
        listarArticulosPorSubcategoria();
        lstArticulos.clear();
        for (Articulo articulos : listArticulos) {
            SelectItem articuloItem = new SelectItem(articulos.getCodigoArticulo(), articulos.getNombre());
            this.lstArticulos.add(articuloItem);
        }
        this.lstArticulos = lstArticulos;
    }

    public List<Articulo> getListArticulos() {
        return listArticulos;
    }

    public void setListArticulos(List<Articulo> listArticulos) {
        this.listArticulos = listArticulos;
    }

    public List<Articulo> getListaPrecios() {
        return listaPrecios;
    }

    public void setListaPrecios(List<Articulo> listaPrecios) {
        this.listaPrecios = listaPrecios;
    }

    
//metodos
    public void listarCategoria() throws Exception {
        CategoriaDAO dao;
        try {
            dao = new CategoriaDAOImp();
            listaCategorias = dao.listarCategorias();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarSubcategoriaPorCategoria() {

        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            SubcategoriaDAO sDao = new SubcategoriaDAOImp();
            this.transaction = this.session.beginTransaction();
            this.listaSubcategorias = sDao.listarSubcategoriaPorPais(this.session, this.idCategoria);
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

    public void listarArticulosPorSubcategoria() {

        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ArticuloDAO aDao = new ArticuloDAOImp();
            this.transaction = this.session.beginTransaction();
            System.out.println("id subcategoria: " + idSubcategorias);
            this.listArticulos = aDao.listarArticulosPorSubcategoria(this.session, this.idSubcategorias);
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

    public void listarPrecios() {
        ArticuloDAO dao = new ArticuloDAOImp();
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            System.out.println("id categoria: " + this.idCategoria);
            System.out.println("id subcategoria: " + this.idSubcategorias);
            System.out.println("codigo articulo: " + this.codigoArticulo);
            
            this.listaPrecios = dao.listarPrecios(this.session, this.idCategoria, this.idSubcategorias, this.codigoArticulo);
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
    
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

         //Instancia hacia la clase ListadePrecios        
        ListadePrecios pDatos = new ListadePrecios();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/Reportes/ListadePrecios.jasper");

        pDatos.getReporte(ruta, this.idCategoria, this.idSubcategorias, this.codigoArticulo);
        FacesContext.getCurrentInstance().responseComplete();
               
    }
}

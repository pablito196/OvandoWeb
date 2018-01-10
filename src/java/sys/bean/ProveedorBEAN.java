package sys.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.RowEditEvent;
import sys.clasesauxiliares.CuentaBancariaFormateada;
import sys.clasesauxiliares.ProveedorDatos;
import sys.dao.ArticuloDAO;
import sys.dao.BancoDAO;
import sys.dao.CategoriaDAO;
import sys.dao.CiudadDAO;
import sys.dao.ContactoDAO;
import sys.dao.CuentabancariaDAO;
import sys.dao.ProveedorDAO;
import sys.dao.SubcategoriaDAO;
import sys.dao.TipomonedaDAO;
import sys.imp.ArticuloDAOImp;
import sys.imp.BancoDAOImp;
import sys.imp.CategoriaDAOImp;
import sys.imp.CiudadDAOImp;
import sys.imp.ContactoDAOImp;
import sys.imp.CuentabancariaDAOImp;
import sys.imp.ProveedorDAOImp;
import sys.imp.SubcategoriaDAOImp;
import sys.imp.TipomonedaDAOImp;
import sys.model.Articulo;
import sys.model.Banco;
import sys.model.Categoriaarticulo;
import sys.model.Ciudad;
import sys.model.Contacto;
import sys.model.Cuentabancaria;
import sys.model.Proveedor;
import sys.model.Subcategoriaarticulo;
import sys.model.Tipomoneda;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class ProveedorBEAN {

    Session session = null;
    Transaction transaction = null;

    private List<Proveedor> listaProveedores;
    private List<Proveedor> listaProveedoresInforme;
    private List<Proveedor> listaFiltradaProveedores;
    private Proveedor proveedor;
    private List<SelectItem> lstProveedores;

    private List<Cuentabancaria> listaCuentabancaria;
    private String cuentaBancaria;

    private Banco banco;
    private Tipomoneda tipomoneda;

    private Integer idBanco;
    private Integer idTipomoneda;
    private Integer idCiudad;
    
    

    private Ciudad ciudad;

    private List<Contacto> listaContactosPorProveedor;
    private Contacto contacto;

    private List<Cuentabancaria> listaCuentabancariaPorProveedor;
    private List<CuentaBancariaFormateada> listaCtabancariaFormateada;

    //para informe proveedor
    private Categoriaarticulo categoria;
    private Subcategoriaarticulo subcategoria;
    private Articulo articulo;
    private boolean todosLosProveedores;
    private Integer idCategoria;
    private Integer idSubcategoria;
    private String codigoArticulo;
    private List<SelectItem> lstCategorias;
    private List<Categoriaarticulo> listaCategorias;
    private List<SelectItem> lstSubCategorias;
    private List<Subcategoriaarticulo> listaSubcategorias;
    private List<Articulo> listaArticulos;
    private List<SelectItem> lstArticulos;

    public ProveedorBEAN() {
        this.proveedor = new Proveedor();
        this.ciudad = new Ciudad();
        this.contacto = new Contacto();
        this.listaCuentabancaria = new ArrayList<>();
        this.listaCuentabancariaPorProveedor = new ArrayList<>();
        this.listaCtabancariaFormateada = new ArrayList<>();
        this.listaProveedores = new ArrayList<>();
        this.listaProveedoresInforme = new ArrayList<>();
        
        this.idCategoria = 0;
        this.idSubcategoria = 0;
        this.codigoArticulo = "";
        
        this.categoria = new Categoriaarticulo();
        this.subcategoria = new Subcategoriaarticulo();
        this.articulo = new Articulo();
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<Proveedor> getListaFiltradaProveedores() {
        return listaFiltradaProveedores;
    }

    public void setListaFiltradaProveedores(List<Proveedor> listaFiltradaProveedores) {
        this.listaFiltradaProveedores = listaFiltradaProveedores;
    }

    public List<Proveedor> getListaProveedores() {
        ProveedorDAO dao = new ProveedorDAOImp();
        listaProveedores = dao.listarProveedores();
        return listaProveedores;
    }

    public List<Cuentabancaria> getListaCuentabancaria() {
        return listaCuentabancaria;
    }

    public void setListaCuentabancaria(List<Cuentabancaria> listaCuentabancaria) {
        this.listaCuentabancaria = listaCuentabancaria;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Tipomoneda getTipomoneda() {
        return tipomoneda;
    }

    public void setTipomoneda(Tipomoneda tipomoneda) {
        this.tipomoneda = tipomoneda;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public Integer getIdTipomoneda() {
        return idTipomoneda;
    }

    public void setIdTipomoneda(Integer idTipomoneda) {
        this.idTipomoneda = idTipomoneda;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    
    public List<Contacto> getListaContactosPorProveedor() {
        listarContactosPorProveedor();
        return listaContactosPorProveedor;
    }

    public void setListaContactosPorProveedor(List<Contacto> listaContactosPorProveedor) {
        this.listaContactosPorProveedor = listaContactosPorProveedor;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public List<Cuentabancaria> getListaCuentabancariaPorProveedor() {
        return listaCuentabancariaPorProveedor;
    }

    public void setListaCuentabancariaPorProveedor(List<Cuentabancaria> listaCuentabancariaPorProveedor) {
        this.listaCuentabancariaPorProveedor = listaCuentabancariaPorProveedor;
    }

    public List<CuentaBancariaFormateada> getListaCtabancariaFormateada() {
        listarCuentasBancariasPorProveedor();
        return listaCtabancariaFormateada;
    }

    public void setListaCtabancariaFormateada(List<CuentaBancariaFormateada> listaCtabancariaFormateada) {
        this.listaCtabancariaFormateada = listaCtabancariaFormateada;
    }
    
    //para informe de proveedores
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

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public boolean isTodosLosProveedores() {
        return todosLosProveedores;
    }

    public void setTodosLosProveedores(boolean todosLosProveedores) {
        this.todosLosProveedores = todosLosProveedores;
    }

    public List<Categoriaarticulo> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoriaarticulo> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    
    public List<SelectItem> getLstCategorias() throws Exception {
        this.lstCategorias = new ArrayList<SelectItem>();
        listarCategoria();
        lstCategorias.clear();
        for (Categoriaarticulo categorias :listaCategorias) 
        {
            SelectItem categoriaItem = new SelectItem(categorias.getIdCategoria(),categorias.getNombreCategoria());
            this.lstCategorias.add(categoriaItem);
        }
        return lstCategorias;
    }

    public List<Subcategoriaarticulo> getListaSubcategorias() {
        return listaSubcategorias;
    }

    public void setListaSubcategorias(List<Subcategoriaarticulo> listaSubcategorias) {
        this.listaSubcategorias = listaSubcategorias;
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

    public List<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public List<SelectItem> getLstArticulos() {
        this.lstArticulos = new ArrayList<SelectItem>();
        listarArticulosPorSubcategoria();
        lstArticulos.clear();
        for (Articulo articulos : listaArticulos) {
            SelectItem articuloItem = new SelectItem(articulos.getCodigoArticulo(), articulos.getNombre());
            this.lstArticulos.add(articuloItem);
        }
        return lstArticulos;
    }
    
    

    public List<Proveedor> getListaProveedoresInforme() {
        return listaProveedoresInforme;
    }

    public void setListaProveedoresInforme(List<Proveedor> listaProveedoresInforme) {
        this.listaProveedoresInforme = listaProveedoresInforme;
    }
    
    

    //metodos
    public void prepararNuevoProveedor() {
        proveedor = new Proveedor();
    }

    public List<SelectItem> getLstProveedores() throws Exception {
        this.lstProveedores = new ArrayList<SelectItem>();
        listar();
        lstProveedores.clear();
        for (Proveedor proveedores : listaProveedores) {
            SelectItem categoriaItem = new SelectItem(proveedores.getIdProveedor(), proveedores.getNombre());
            this.lstProveedores.add(categoriaItem);
        }
        return lstProveedores;
    }

    public void listar() throws Exception {
        ProveedorDAO dao;
        try {
            dao = new ProveedorDAOImp();
            listaProveedores = dao.listarProveedores();
        } catch (Exception e) {
            throw e;
        }
    }

    //metodo para guardar proveedor
    public void guardarProveedor() {
        this.session = null;
        this.transaction = null;
        try {

            this.session = HibernateUtil.getSessionFactory().openSession();
            ProveedorDAO pdao = new ProveedorDAOImp();
            CuentabancariaDAO cbDao = new CuentabancariaDAOImp();
            CiudadDAO cDao = new CiudadDAOImp();

            this.transaction = this.session.beginTransaction();

            this.ciudad = cDao.buscarCiudad(this.session, this.idCiudad);
            this.proveedor.setCiudad(this.ciudad);

            pdao.guardarProveedor(this.session, this.proveedor);

            this.proveedor = pdao.obtenerUltimoRegistro(this.session);
            for (Cuentabancaria item : listaCuentabancaria) {

                item.setProveedor(this.proveedor);

                cbDao.guardarCuentabancaria(this.session, item);
            }
            this.transaction.commit();
            this.listaCuentabancaria = null;
            this.ciudad = null;
            this.idCiudad = 0;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proveedor registrado"));
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

    public void agregarCuentabancaria() {
        if (this.cuentaBancaria.equals("0") || this.cuentaBancaria.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Escriba una cuenta bancaria"));
            this.cuentaBancaria = "";
        } else {
            try {
                this.session = HibernateUtil.getSessionFactory().openSession();
                BancoDAO bDao = new BancoDAOImp();
                TipomonedaDAO tmDao = new TipomonedaDAOImp();
                this.transaction = this.session.beginTransaction();

                this.banco = bDao.buscarBanco(this.session, this.idBanco);
                this.tipomoneda = tmDao.buscarMoneda(this.session, this.idTipomoneda);

                this.listaCuentabancaria.add(new Cuentabancaria(banco, null, tipomoneda, this.cuentaBancaria));

                this.transaction.commit();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Cuenta bancaria agregada al detalle"));
                this.cuentaBancaria = "";
                this.banco = null;
                this.tipomoneda = null;
                //this.listaCuentabancaria = null;
            } catch (Exception e) {
            }

        }
    }

    //metodo para quitar cuenta bancaria del detalle de cuentas bancarias
    public void quitarCuentaBancaria(String cuentaBancaria, Integer filaSeleccionada) {
        try {
            int i = 0;
            for (Cuentabancaria item : this.listaCuentabancaria) {
                if (item.getNumeroCuenta().equals(cuentaBancaria) && filaSeleccionada.equals(i)) {
                    this.listaCuentabancaria.remove(i);
                    break;
                }
                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Cuenta Bancaria eliminada"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    //Metodo para editar la cuenta bancaria en el detalle de cuentas bancarias
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se modificó la Cuenta Bancaria"));

    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se realizó la modificación en la Cuenta Bancaria"));
    }

    //metodo para listar contactos por proveedor
    public void listarContactosPorProveedor() {
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            ContactoDAO cDao = new ContactoDAOImp();

            this.transaction = this.session.beginTransaction();

            this.listaContactosPorProveedor = cDao.listarContactosPorProveedor(this.session, this.proveedor.getIdProveedor());

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

    //metodo para guardar contactos
    public void guardarContacto() {
        this.session = null;
        this.transaction = null;
        try {

            this.session = HibernateUtil.getSessionFactory().openSession();

            ContactoDAO ctDao = new ContactoDAOImp();
            CiudadDAO cDao = new CiudadDAOImp();

            this.transaction = this.session.beginTransaction();

            this.ciudad = cDao.buscarCiudad(this.session, this.idCiudad);

            this.contacto.setProveedor(this.proveedor);
            this.contacto.setCiudad(this.ciudad);

            ctDao.guardarContacto(this.session, this.contacto);

            this.transaction.commit();
            this.contacto = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Contacto registrado"));
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

    //metodo para listar las cuentas bancarias de un determinado proveedor
    public void listarCuentasBancariasPorProveedor() {
        this.session = null;
        this.transaction = null;
        //this.listaCtabancariaFormateada = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            CuentabancariaDAO ctaDao = new CuentabancariaDAOImp();

            this.transaction = this.session.beginTransaction();

            //**
            System.out.println("idProveedor: " + this.proveedor.getIdProveedor());
            //**

            this.listaCuentabancariaPorProveedor = ctaDao.listarCuentasbancariasPorProveedor(this.session, this.proveedor.getIdProveedor());

            if (this.listaCuentabancariaPorProveedor.size() > 0) {
                this.listaCtabancariaFormateada = new ArrayList<>();
                for (Cuentabancaria item : listaCuentabancariaPorProveedor) {

                    this.listaCtabancariaFormateada.add(new CuentaBancariaFormateada(item.getIdCuentabancaria(), item.getBanco().getIdBanco(), item.getBanco().getNombreBanco(),
                            item.getTipomoneda().getIdTipomoneda(), item.getTipomoneda().getDescripcion(), item.getNumeroCuenta(),
                            item.getProveedor().getIdProveedor(), item.getProveedor().getNombre()));
                }
            } else {
                this.listaCtabancariaFormateada = new ArrayList<>();
            }
            this.listaCuentabancariaPorProveedor = null;

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

    //metodo para modificar proveedor
    public void modificarProveedor() {
        this.session = null;
        this.transaction = null;
        try {

            this.session = HibernateUtil.getSessionFactory().openSession();
            ProveedorDAO pdao = new ProveedorDAOImp();
            CuentabancariaDAO cbDao = new CuentabancariaDAOImp();
            CiudadDAO cDao = new CiudadDAOImp();

            this.transaction = this.session.beginTransaction();

            this.ciudad = cDao.buscarCiudad(this.session, this.idCiudad);
            this.proveedor.setCiudad(this.ciudad);

            pdao.actualizarProveedor(this.session, this.proveedor);

            for (Cuentabancaria item : listaCuentabancaria) {

                item.setProveedor(this.proveedor);

                cbDao.guardarCuentabancaria(this.session, item);
            }
            this.transaction.commit();
            this.listaCuentabancaria = null;
            this.ciudad = null;
            this.idCiudad = 0;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Proveedor modificado"));
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

    //METODOS PARA INFORME DE PROVEEDOR
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

    //metodo para activar datos busqueda de proveedor

    public void activarControlesBusqueda() {
        if (this.todosLosProveedores) {
            this.disableControlesBusquedaButton();

        } else {
            this.enabledControlesBusquedaButton();
        }
    }
    
    public void listarCategoria() throws Exception
    {
        CategoriaDAO dao;
        try
        {
            dao = new CategoriaDAOImp();
            listaCategorias = dao.listarCategorias();
        }
        catch(Exception e)
        {
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
        }finally {
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
            this.listaArticulos = aDao.listarArticulosPorSubcategoria(this.session, this.idSubcategoria);
            this.transaction.commit();
        } catch (Exception e) {
            if (this.transaction != null) {
                this.transaction.rollback();
                
            }
            System.out.println(e.getMessage());
        }finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void BuscarProveedores() {
        ProveedorDAO dao = new ProveedorDAOImp();
        if (todosLosProveedores) {

            listaProveedoresInforme = dao.listarProveedores();
        } else {
            this.session = null;
            this.transaction = null;
            try {
                this.session = HibernateUtil.getSessionFactory().openSession();
                this.transaction = this.session.beginTransaction();
                System.out.println("id ciudad: "+this.idCiudad);
                System.out.println("id categoria: "+this.idCategoria);
                System.out.println("id subcategoria: "+this.idSubcategoria);
                System.out.println("codigo articulo: "+this.codigoArticulo);
                this.listaProveedoresInforme = dao.buscarProveedores(this.session, this.idCiudad, this.idCategoria, this.idSubcategoria, this.codigoArticulo);
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
    }
    
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

         //Instancia hacia la clase ProveedorDatos        
        ProveedorDatos pDatos = new ProveedorDatos();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/Reportes/ProveedorDatos.jasper");

        pDatos.getReporte(ruta, this.idCiudad, this.idCategoria, this.idSubcategoria, this.codigoArticulo, this.todosLosProveedores);
        FacesContext.getCurrentInstance().responseComplete();
               
    }
}

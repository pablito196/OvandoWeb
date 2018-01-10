package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.CategoriaDAO;
import sys.dao.SubcategoriaDAO;
import sys.imp.CategoriaDAOImp;
import sys.imp.SubcategoriaDAOImp;
import sys.model.Subcategoriaarticulo;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class SubcategoriaBEAN {

    Session session;
    Transaction transaction;
    
    private List<Subcategoriaarticulo> listaSubcategorias;
    private Subcategoriaarticulo subcategoria;
    private List<SelectItem> lstSubCategorias;

    private List<Subcategoriaarticulo> listaFiltradaSubcategorias;
    
    private Integer idCategoria;

    public SubcategoriaBEAN() {
        this.subcategoria = new Subcategoriaarticulo();
        
    }

    public void setListaSubcategorias(List<Subcategoriaarticulo> listaSubcategorias) {
        this.listaSubcategorias = listaSubcategorias;
    }

    public Subcategoriaarticulo getSubcategoria() {
        return subcategoria;
    }

    public void setCategoria(Subcategoriaarticulo subcategoria) {
        this.subcategoria = subcategoria;
    }

    public List<Subcategoriaarticulo> getListaSubcategorias() {
        SubcategoriaDAO dao = new SubcategoriaDAOImp();
        listaSubcategorias = dao.listarSubcategorias();
        return listaSubcategorias;
    }

    public List<Subcategoriaarticulo> getListaFiltradaSubcategorias() {
        return listaFiltradaSubcategorias;
    }

    public void setListaFiltradaSubcategorias(List<Subcategoriaarticulo> listaFiltradaSubcategorias) {
        this.listaFiltradaSubcategorias = listaFiltradaSubcategorias;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    
    
    //metodos
    public void prepararNuevaSubcategoria() {
        this.subcategoria = new Subcategoriaarticulo();
    }

    public void nuevaSubcategoria() {
        
        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            CategoriaDAO cDao = new CategoriaDAOImp();
            SubcategoriaDAO scDao = new SubcategoriaDAOImp();
            this.transaction = this.session.beginTransaction();
            this.subcategoria.setCategoriaarticulo(cDao.buscarCategoria(this.session, this.idCategoria));
            scDao.nuevaSubcategoria(this.session, this.subcategoria);
            this.transaction.commit();
            this.subcategoria = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Subcategoria registrada"));
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

    public void actualizarSubcategoria() {
        SubcategoriaDAO dao = new SubcategoriaDAOImp();
        dao.actualizarSubcategoria(subcategoria);
        subcategoria = new Subcategoriaarticulo();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Subcategoria modificada"));
    }

    public void eliminarSubcategoria() {
        SubcategoriaDAO dao = new SubcategoriaDAOImp();
        dao.eliminarSubcategoria(subcategoria);
        subcategoria = new Subcategoriaarticulo();
    }

    public List<SelectItem> getLstSubCategorias() {
        this.lstSubCategorias = new ArrayList<SelectItem>();
        listarSubcategoriaPorPais();
        lstSubCategorias.clear();
        for (Subcategoriaarticulo subcategorias : listaSubcategorias) {
            SelectItem subcategoriaItem = new SelectItem(subcategorias.getIdSubcategoria(), subcategorias.getNombreSubCategoria());
            this.lstSubCategorias.add(subcategoriaItem);
        }
        return lstSubCategorias;
       }

    public void listarSubcategoriaPorPais() {
        
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
    
    
}

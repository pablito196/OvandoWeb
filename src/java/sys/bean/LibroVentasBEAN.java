package sys.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.clasesauxiliares.LibroVentas;
import sys.dao.FacturaDAO;
import sys.imp.FacturaDAOImp;
import sys.model.Factura;
import sys.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class LibroVentasBEAN {

    Session session = null;
    Transaction transaction = null;
    
    private Date fechaInicio;
    private Date fechaFin;
    
    private List<Factura> listaFacturas;
    private List<LibroVentas> listaLibroVentas;
    
    public LibroVentasBEAN() {
        this.fechaInicio = null;
        this.fechaFin = null;
        
        this.listaFacturas = new ArrayList<>();
        this.listaLibroVentas = new ArrayList<>();
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Factura> getListaFacturas() {
        return listaFacturas;
    }

    public void setListaFacturas(List<Factura> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

    public List<LibroVentas> getListaLibroVentas() {
        return listaLibroVentas;
    }

    public void setListaLibroVentas(List<LibroVentas> listaLibroVentas) {
        this.listaLibroVentas = listaLibroVentas;
    }
    
    //METODOS
    public void generarLibro() {
        this.session = null;
        this.transaction = null;
        
        this.listaLibroVentas = new ArrayList<>();
        FacturaDAO fDao = new FacturaDAOImp();
        
        try {
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
           
            this.listaFacturas = fDao.listarFacturasPorFecha(this.session, this.fechaInicio, this.fechaFin);
            
            Integer numero = 1;
            char estado = ' ';
            BigDecimal debito =new BigDecimal("0.13");
            for (Factura item : listaFacturas) {
                if(item.getEstado().equals("VÁLIDA"))
                {
                    estado = 'V';
                }
                else
                {
                    if(item.getEstado().equals("ANULADA"))
                    {
                        estado = 'A';
                    }
                }
                listaLibroVentas.add(new LibroVentas(numero, item.getFechaFactura(), item.getNumeroFactura(), item.getNumeroAutorizacion(), estado, item.getNitCliente(), item.getRazonSocial(), 
                        item.getMonto(), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), item.getMonto(), new BigDecimal(0), item.getMonto(), item.getMonto().multiply(debito).setScale(2, BigDecimal.ROUND_HALF_UP), item.getCodigoControl()));
                numero++;
            }
                //this.listaVentasInforme = vDao.buscarVenta(this.session, this.fechaInicio, this.fechaFin, this.estado, this.tipoVenta, idAlmacen);
                //System.out.println("tamaño lista de ventas: " + this.listaVentasInforme.size());
            
//            totalVentaInforme();
            //this.listaVentasInforme = new ArrayList<>();
            this.transaction.commit();
            /*this.fechaInicio = null;
             this.fechaFin = null;*/
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

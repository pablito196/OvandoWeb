
package sys.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.FacturaDAO;
import sys.model.Factura;
import sys.util.HibernateUtil;

public class FacturaDAOImp implements FacturaDAO
{

    @Override
    public List<Factura> listarFacturas() 
    {
        List<Factura> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Factura";
        try
        {
            lista = session.createQuery(hql).list();
            t.commit();
            session.close();
        }
        catch(Exception e)
        {
            t.rollback();
        }
        return lista;
    }

    @Override
    public void nuevaFactura(Factura factura) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(factura);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        }
        finally
        {
            if(session != null)
            session.close();
        }
    }

    @Override
    public boolean actualizarFactura(Session session, Factura factura) throws Exception 
    {
        session.update(factura);
        return true;    
    }

    @Override
    public void eliminarFactura(Factura factura) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(factura);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        }
        finally
        {
            if(session != null)
                session.close();
        }
    }

    @Override
    public Factura obtenerUltimaFactura(Session session, String numeroAutorizacion) throws Exception 
    {
        String hql = "FROM Factura WHERE numeroAutorizacion = :numeroAutorizacion ORDER BY idFactura DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        q.setParameter("numeroAutorizacion", numeroAutorizacion);
        
        return (Factura) q.uniqueResult();
    }

    @Override
    public boolean guardarFactura(Session session, Factura factura) throws Exception {
        session.save(factura);
        return true;
    }

    @Override
    public Factura obtenerUltimaFacturaTiempo(Session session, Long tiempo) throws Exception {
        String hql = "FROM Factura WHERE tiempoRegistro = :tiempo";
        Query q = session.createQuery(hql);
        q.setParameter("tiempo", tiempo);
        return (Factura) q.uniqueResult();
    }

    @Override
    public Factura buscarFactura(Session session, Integer idFactura) throws Exception {
        String hql = "FROM Factura WHERE idFactura = :idFactura";
        Query q = session.createQuery(hql);
        q.setParameter("idFactura", idFactura);
        return (Factura) q.uniqueResult();
    }

    @Override
    public List<Factura> listarFacturasPorVenta(Session session, Integer numeroVenta) {
        String hql = "FROM Factura WHERE idFactura in (SELECT factura.idFactura FROM Ventafactura WHERE venta.numeroVenta = :numeroVenta)";
        Query q = session.createQuery(hql);
        q.setParameter("numeroVenta", numeroVenta);
        return q.list();
    }

    @Override
    public List<Factura> listarFacturasPorFecha(Session session, Date fechaInicio, Date fechaFin) throws Exception {
        List<Factura> lista = null;
        String hql = "FROM Factura WHERE fechaFactura BETWEEN :fechaInicio AND :fechaFin ORDER BY numeroFactura ASC";
        Query q = session.createQuery(hql);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        lista = q.list();
        return lista;
    }

   

}

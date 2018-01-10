package sys.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.ComprasDAO;
import sys.model.Compra;
import sys.util.HibernateUtil;

public class ComprasDAOImp implements ComprasDAO
{

    @Override
    public List<Compra> listarCompra() 
    {
        List<Compra> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Compra ORDER BY fechaCompra desc, idCompra desc";
        try
        {
            lista = session.createQuery(hql).list();
            for (Compra l : lista) {
                Hibernate.initialize(l.getProveedor());
            }
            for (Compra l : lista) {
                Hibernate.initialize(l.getTipopago());
            }
            for (Compra l : lista) {
                Hibernate.initialize(l.getAlmacen().getSucursal());
            }
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
    public void nuevaCompra(Compra compra) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(compra);
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
    public void actualizarCompra(Compra compra) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(compra);
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
    public void eliminarCompra(Compra compra) 
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(compra);
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
    public List<Compra> listarCompraCredito() {
        List<Compra> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Compra WHERE tipopago.idTipopago = 2 ORDER BY fechaCompra desc, idCompra desc";
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
    public Long obtenerTotalRegistrosCompra(Session session) {
        String hql = "SELECT COUNT(*) FROM Compra";
        Query q = session.createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public Compra obtenerUltimoRegistro(Session session) throws Exception {
        String hql = "FROM Compra ORDER BY idCompra DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        return (Compra) q.uniqueResult();
    }

    @Override
    public boolean guardarCompra(Session session, Compra compra) throws Exception {
        session.save(compra);
        return true;
    }

    @Override
    public List<Compra> listarCompraCreditoVigentePorProveedor(Session session, Integer idProveedor) throws Exception {
       
        StringBuilder hql = new StringBuilder();
        
        hql.append("SELECT c");
        hql.append(" FROM Compra c, Cronocomprascred cronocompras, Pagocompracredito pcc");
        hql.append(" WHERE c.idCompra = cronocompras.compra.idCompra");
        hql.append(" AND c.proveedor.idProveedor = :idProveedor");
        hql.append(" AND cronocompras.idCronogramaC = (Select Max(cr.idCronogramaC)");
        hql.append(" From Cronocomprascred cr, Compra comp, Pagocompracredito pc");
        hql.append(" Where comp.idCompra = cr.compra.idCompra");
        hql.append(" and cr.idCronogramaC = pc.cronocomprascred.idCronogramaC");
        hql.append(" and comp.idCompra = c.idCompra)");
        hql.append(" AND cronocompras.idCronogramaC = pcc.cronocomprascred.idCronogramaC");
        hql.append(" AND pcc.idPagocredito = (Select Max(pagcc.idPagocredito)");
        hql.append(" From Pagocompracredito pagcc, Cronocomprascred cronc");
        hql.append(" Where cronc.idCronogramaC = cronocompras.idCronogramaC");
        hql.append(" and pagcc.cronocomprascred.idCronogramaC = cronc.idCronogramaC");
        hql.append(" group by pagcc.cronocomprascred.idCronogramaC");
        hql.append(" )");
        hql.append(" AND c.estado = 'VIGENTE'");
        hql.append(" AND pcc.saldo > 0");
        /*hql.append("FROM Compra");
        hql.append(" WHERE idCompra  in (SELECT cr.compra.idCompra FROM Pagocompracredito pc, Cronocomprascred cr, Compra c");
        hql.append(" WHERE cr.compra.proveedor.idProveedor = :idProveedor");
        hql.append(" AND pc.saldo > 0)");
        hql.append(" AND estado = 'VIGENTE' ORDER BY fechaCompra ASC ");*/
        
        Query q = session.createQuery(hql.toString());
        q.setParameter("idProveedor", idProveedor);
        return q.list();
    }

    @Override
    public Compra buscarCompra(Session session, Integer idCompra) throws Exception {
        String hql = "FROM Compra WHERE idCompra = :idCompra";
        Query q = session.createQuery(hql);
        q.setParameter("idCompra", idCompra);
        return (Compra) q.uniqueResult();
    }

    @Override
    public List<Compra> buscarCompra(Session session, Date fechaInicio, Date fechaFin, String estado, Integer idTipopago) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaIni = dateFormat.format(fechaInicio);
        String fechaFi = dateFormat.format(fechaFin);
        System.out.println("entra a buscar compra imp");
        
        List<Compra> lista = null;
        StringBuilder hql = new StringBuilder();
        System.out.println("estado: "+estado);
        System.out.println("id tipo pago: "+idTipopago);
        
        hql.append("FROM Compra");
        hql.append(" WHERE fechaCompra >= '").append(fechaIni).append("' AND fechaCompra <= '").append(fechaFi).append("'");
        if(!"ANULADA".equals(estado))
        {
            hql.append(" AND estado <> 'ANULADA'");
        }
        else
        {
            hql.append(" AND estado = 'ANULADA'");
        }
        if(idTipopago > 0)
        {
            hql.append(" AND tipopago.idTipopago = ").append(idTipopago);
        }
        hql.append(" ORDER BY fechaCompra desc, idCompra desc");
        Query q = session.createQuery(hql.toString());
        System.out.println("consulta: "+hql.toString());
        
       
        lista = q.list();
        for (Compra l : lista) {
            Hibernate.initialize(l.getTipopago());
        }
        for (Compra l : lista) {
            Hibernate.initialize(l.getProveedor());
        }
        /*for(Compra l : lista){
            Hibernate.initialize(l.getAlmacen().getSucursal());
        } */
        //***
        for (Compra item : lista) {
            System.out.println("numero compra: "+item.getIdCompra());
        }
        //***
        return lista;
    }

    @Override
    public List<Compra> buscarComprasPorProveedor(Session session, Date fechaInicio, Date fechaFin, String estado, Integer idTipopago, String clientes) throws Exception {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaIni = dateFormat.format(fechaInicio);
        String fechaFi = dateFormat.format(fechaFin);
        //System.out.println("entra a buscar venta imp");
        List<Compra> lista = null;
        StringBuilder hql = new StringBuilder();
        
        /*System.out.println("estado: "+estado);
        System.out.println("id tipo pago: "+idTipopago);*/
        
        hql.append("FROM Compra");
        hql.append(" WHERE fechaCompra >= '").append(fechaIni).append("' AND fechaCompra <= '").append(fechaFi).append("'");
        hql.append(" AND proveedor.nit in ").append(clientes);
        if(!"ANULADA".equals(estado))
        {
            hql.append(" AND estado <> 'ANULADA'");
        }
        else
        {
            hql.append(" AND estado = 'ANULADA'");
        }
        if(idTipopago > 0)
        {
            hql.append(" AND tipopago.idTipopago = ").append(idTipopago);
        }
        
        hql.append(" ORDER BY fechaCompra desc, idCompra desc");
        Query q = session.createQuery(hql.toString());
        System.out.println("consulta: "+hql.toString());
        
       
        lista = q.list();
        for (Compra l : lista) {
            Hibernate.initialize(l.getTipopago());
        }
        for (Compra l : lista) {
            Hibernate.initialize(l.getProveedor());
        }
        /*for(Compra l : lista)
        {
            Hibernate.initialize(l.getAlmacen().getSucursal());
        }*/
        return lista;
    }

    @Override
    public List<Compra> listarComprasPorFecha(Session session, Date fechaInicio, Date fechaFin) throws Exception {
        List<Compra> lista = null;
        String hql = "FROM Compra WHERE fechaCompra BETWEEN :fechaInicio AND :fechaFin AND estado <> 'ANULADA' AND tipopago.idTipopago = 1 ORDER BY fechaCompra desc, idCompra desc";
        Query q = session.createQuery(hql);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        lista = q.list();
        for (Compra l : lista) {
            Hibernate.initialize(l.getTipopago());
        }
        for (Compra l : lista) {
            Hibernate.initialize(l.getProveedor());
        }
        return lista;
    }
    
}

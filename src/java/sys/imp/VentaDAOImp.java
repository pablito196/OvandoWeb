package sys.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.VentaDAO;
import sys.model.Venta;
import sys.util.HibernateUtil;

public class VentaDAOImp implements VentaDAO {

    @Override
    public List<Venta> listarVenta() {
        List<Venta> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Venta ORDER BY fechaVenta desc, numeroVenta desc";
        try {
            Query q = session.createQuery(hql).setMaxResults(200);
            lista = q.list();
            for (Venta l : lista) {
                Hibernate.initialize(l.getCliente());
            }
            for (Venta l : lista) {
                Hibernate.initialize(l.getTipopago());
            }
            for (Venta l : lista) {
                Hibernate.initialize(l.getAlmacen().getSucursal());
            }
            t.commit();
            session.close();
        } catch (Exception e) {
            t.rollback();
        }
        return lista;
    }

    @Override
    public List<Venta> listarVentaCredito() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevaVenta(Venta venta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarVenta(Session session, Venta venta) throws Exception {
        session.update(venta);
        return true;
    }

    @Override
    public void eliminarVenta(Venta venta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Venta> listarVentaCreditoVigentePorCliente(Session session, String ciCliente) throws Exception {
        StringBuilder hql = new StringBuilder();

        hql.append("SELECT v");
        hql.append(" FROM Venta v, Cronoventascredito cronoventas, Pagoventascredito pvc");
        hql.append(" WHERE v.numeroVenta = cronoventas.venta.numeroVenta");
        hql.append(" AND v.cliente.ciCliente = :ciCliente");
        hql.append(" AND cronoventas.idCronoV = (Select Max(cr.idCronoV)");
        hql.append(" From Cronoventascredito cr, Venta vent, Pagoventascredito pc");
        hql.append(" Where vent.numeroVenta = cr.venta.numeroVenta");
        hql.append(" and cr.idCronoV = pc.cronoventascredito.idCronoV");
        hql.append(" and vent.numeroVenta = v.numeroVenta)");
        hql.append(" AND cronoventas.idCronoV = pvc.cronoventascredito.idCronoV");
        hql.append(" AND pvc.idPagoVentaCred = (Select Max(pagvc.idPagoVentaCred)");
        hql.append(" From Pagoventascredito pagvc, Cronoventascredito cronv");
        hql.append(" Where cronv.idCronoV = cronoventas.idCronoV");
        hql.append(" and pagvc.cronoventascredito.idCronoV = cronv.idCronoV");
        hql.append(" group by pagvc.cronoventascredito.idCronoV");
        hql.append(" )");
        hql.append(" AND v.estado = 'VENDIDO'");
        hql.append(" AND pvc.saldo > 0");

        Query q = session.createQuery(hql.toString());
        q.setParameter("ciCliente", ciCliente);
        return q.list();
    }

    @Override
    public Venta buscarVenta(Session session, Integer numeroVenta) throws Exception {
        Venta venta;
        String hql = "FROM Venta WHERE numeroVenta = :numeroVenta";
        Query q = session.createQuery(hql);
        q.setParameter("numeroVenta", numeroVenta);
        venta = (Venta) q.uniqueResult();
        Hibernate.initialize(venta.getUsuario());
        return venta;
    }

    @Override
    public Long obtenerTotalRegistrosVenta(Session session) {
        String hql = "SELECT COUNT(*) FROM Venta";
        Query q = session.createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public Venta obtenerUltimoRegistro(Session session) throws Exception {
        String hql = "FROM Venta ORDER BY numeroVenta DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        return (Venta) q.uniqueResult();
    }

    @Override
    public boolean guardarVenta(Session session, Venta venta) throws Exception {
        session.save(venta);
        return true;
    }

    @Override
    public Venta obtenerUltimoRegistroTiempo(Session session, Long tiempo) throws Exception {
        String hql = "FROM Venta WHERE tiempoRegistro = :tiempo";
        Query q = session.createQuery(hql);
        q.setParameter("tiempo", tiempo);

        return (Venta) q.uniqueResult();
    }

    @Override
    public List<Venta> listarVentasNoFacturadas(Session session, String ciCliente) throws Exception {
        List<Venta> lista = null;
        String hql = "FROM Venta WHERE numeroVenta not in (SELECT venta.numeroVenta FROM Ventafactura) AND estado <> 'ANULADA' AND cliente.ciCliente = :ciCliente ORDER BY numeroVenta ASC";
        Query q = session.createQuery(hql);
        q.setParameter("ciCliente", ciCliente);
        lista = q.list();
        for (Venta l : lista) {
            Hibernate.initialize(l.getTipopago());
        }
        return lista;
    }

    @Override
    public List<Venta> listarVentasPorFecha(Session session, Date fechaInicio, Date fechaFin) throws Exception {
        List<Venta> lista = null;
        String hql = "FROM Venta WHERE fechaVenta BETWEEN :fechaInicio AND :fechaFin AND estado <> 'ANULADA' AND tipopago.idTipopago = 1 ORDER BY fechaVenta desc, numeroVenta desc";
        Query q = session.createQuery(hql);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        lista = q.list();
        for (Venta l : lista) {
            Hibernate.initialize(l.getTipopago());
        }
        for (Venta l : lista) {
            Hibernate.initialize(l.getCliente());
        }
        return lista;
    }

    @Override
    public List<Venta> listarVentasPorFechaClientes(Session session, Date fechaInicio, Date fechaFin, String clientes) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String fechaIni = dateFormat.format(fechaInicio);
        String fechaFi = dateFormat.format(fechaFin);
        
        List<Venta> lista = null;
        //System.out.println("fecha inicio transformada: "+fechaIni);
        //System.out.println("fecha fin transformada: "+fechaFi);
        //String sql = "FROM Venta WHERE cliente.ciCliente in  "+ clientes +" AND fechaVenta BETWEEN '"+ fechaIni +"' AND '"+ fechaFi +"' AND estado <> 'ANULADA' ORDER BY fechaVenta desc, numeroVenta desc";
        StringBuilder hql = new StringBuilder();
        hql.append("FROM Venta");
        hql.append(" WHERE cliente.ciCliente in ").append(clientes);
        hql.append(" AND fechaVenta BETWEEN '").append(fechaIni).append("' AND '").append(fechaFi).append("'");
        hql.append(" AND estado <> 'ANULADA'");
        hql.append(" AND tipopago.idTipopago = 1");
        hql.append(" ORDER BY fechaVenta desc, numeroVenta desc");
        System.out.println("consulta hql: "+hql.toString());
        Query q = session.createQuery(hql.toString());
        //q.setParameter("clientes", clientes);
        lista = q.list();
        for (Venta l : lista) {
            Hibernate.initialize(l.getTipopago());
        }
        for (Venta l : lista) {
            Hibernate.initialize(l.getCliente());
        }
        return lista;
    }

    @Override
    public List<Venta> buscarVenta(Session session, Date fechaInicio, Date fechaFin, String estado, Integer idTipopago, Integer idAlmacen) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaIni = dateFormat.format(fechaInicio);
        String fechaFi = dateFormat.format(fechaFin);
        System.out.println("entra a buscar venta imp");
        List<Venta> lista = null;
        StringBuilder hql = new StringBuilder();
        System.out.println("estado: "+estado);
        System.out.println("id tipo pago: "+idTipopago);
        System.out.println("id almacen: "+idAlmacen);
        hql.append("FROM Venta");
        hql.append(" WHERE fechaVenta >= '").append(fechaIni).append("' AND fechaVenta <= '").append(fechaFi).append("'");
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
        if(idAlmacen > 0)
        {
            hql.append(" AND almacen.idAlmacen = ").append(idAlmacen);
        }
        hql.append(" ORDER BY fechaVenta desc, numeroVenta desc");
        Query q = session.createQuery(hql.toString());
        System.out.println("consulta: "+hql.toString());
        
       
        lista = q.list();
        for (Venta l : lista) {
            Hibernate.initialize(l.getTipopago());
        }
        for (Venta l : lista) {
            Hibernate.initialize(l.getCliente());
        }
        //***
        for (Venta item : lista) {
            System.out.println("numero venta: "+item.getNumeroVenta());
        }
        //***
        return lista;
    }

    @Override
    public List<Venta> buscarVentasPorClientes(Session session, Date fechaInicio, Date fechaFin, String estado, Integer idTipopago, Integer idAlmacen, String clientes) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaIni = dateFormat.format(fechaInicio);
        String fechaFi = dateFormat.format(fechaFin);
        System.out.println("entra a buscar venta imp");
        List<Venta> lista = null;
        StringBuilder hql = new StringBuilder();
        System.out.println("estado: "+estado);
        System.out.println("id tipo pago: "+idTipopago);
        System.out.println("id almacen: "+idAlmacen);
        hql.append("FROM Venta");
        hql.append(" WHERE fechaVenta >= '").append(fechaIni).append("' AND fechaVenta <= '").append(fechaFi).append("'");
        hql.append(" AND cliente.ciCliente in ").append(clientes);
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
        if(idAlmacen > 0)
        {
            hql.append(" AND almacen.idAlmacen = ").append(idAlmacen);
        }
        hql.append(" ORDER BY fechaVenta desc, numeroVenta desc");
        Query q = session.createQuery(hql.toString());
        System.out.println("consulta: "+hql.toString());
        
       
        lista = q.list();
        for (Venta l : lista) {
            Hibernate.initialize(l.getTipopago());
        }
        for (Venta l : lista) {
            Hibernate.initialize(l.getCliente());
        }
        //***
        for (Venta item : lista) {
            System.out.println("numero venta: "+item.getNumeroVenta());
        }
        //***
        return lista;
    }

    @Override
    public List<Venta> buscarVentasCreditoPorClientes(Session session, Date fechaInicio, Date fechaFin, String clientes) throws Exception {
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaIni = dateFormat.format(fechaInicio);
        String fechaFi = dateFormat.format(fechaFin);
        //System.out.println("entra a buscar venta imp");*/
        List<Venta> lista = null;
        /*StringBuilder hql = new StringBuilder();
        hql.append("FROM Venta");
        hql.append(" WHERE ");*/
        return lista;
    }

}

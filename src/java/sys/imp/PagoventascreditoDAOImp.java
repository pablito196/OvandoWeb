package sys.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.PagoventascreditoDAO;
import sys.model.Pagoventascredito;


public class PagoventascreditoDAOImp implements PagoventascreditoDAO{

    @Override
    public Pagoventascredito buscarPagoVenta(Session session, Integer idCronoV) throws Exception {
        String hql = "FROM Pagoventascredito WHERE cronoventascredito.idCronoV = :idCronoV";
        Query q = session.createQuery(hql);
        q.setParameter("idCronoV",  idCronoV);
        return (Pagoventascredito) q.uniqueResult();
    }

    @Override
    public BigDecimal calcularMontoPagado(Session session, Integer numeroVenta) throws Exception {
        String hql = "SELECT SUM(montoPagado) FROM Pagoventascredito WHERE cronoventascredito.idCronoV in ( SELECT idCronoV FROM Cronoventascredito WHERE venta.numeroVenta = :numeroVenta)";
        Query q = session.createQuery(hql);
        q.setParameter("numeroVenta", numeroVenta);
        return (BigDecimal) q.uniqueResult();
    }

    @Override
    public boolean guardarPagoVentas(Session session, Pagoventascredito pagoVentas) throws Exception {
        session.save(pagoVentas);
        return true;
    }

    @Override
    public BigDecimal buscarUltimoSaldoPorVenta(Session session, Integer numeroVenta) throws Exception {
        String hql = "SELECT MIN(saldo) FROM Pagoventascredito WHERE cronoventascredito.idCronoV in ( SELECT idCronoV FROM Cronoventascredito WHERE venta.numeroVenta = :numeroVenta)";
        Query q = session.createQuery(hql);
        q.setParameter("numeroVenta", numeroVenta);
        return (BigDecimal) q.uniqueResult();
    }

    @Override
    public List<Pagoventascredito> listarPagosPorFechas(Session session, Date fechaInicio, Date fechaFin) throws Exception {
        List<Pagoventascredito> lista = null;
        String hql = "FROM Pagoventascredito WHERE fechaPago BETWEEN :fechaInicio AND :fechaFin ORDER BY fechaPago desc";
        Query q = session.createQuery(hql);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        lista = q.list();
        for (Pagoventascredito l : lista) {
            Hibernate.initialize(l.getCronoventascredito().getVenta());
        }
        for (Pagoventascredito l : lista) {
            Hibernate.initialize(l.getCronoventascredito().getVenta().getCliente());
        }
        return lista;
    }
    
}

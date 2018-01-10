
package sys.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.PagocompracreditoDAO;
import sys.model.Pagocompracredito;

public class PagocompracreditoDAOImp implements PagocompracreditoDAO {

    @Override
    public boolean guardarPagoCompras(Session session, Pagocompracredito pagoCompras) throws Exception {
        session.save(pagoCompras);
        return true;
    }

    @Override
    public Pagocompracredito buscarPagoCompra(Session session, Integer idCronogramaC) throws Exception {
        String hql = "FROM Pagocompracredito WHERE cronocomprascred.idCronogramaC = :idCronogramaC";
        Query q = session.createQuery(hql);
        q.setParameter("idCronogramaC",  idCronogramaC);
        return (Pagocompracredito) q.uniqueResult();
    }

    @Override
    public BigDecimal calcularMontoPagado(Session session, Integer idCompra) throws Exception {
        String hql = "SELECT SUM(montoPagado) FROM Pagocompracredito WHERE cronocomprascred.idCronogramaC in ( SELECT idCronogramaC FROM Cronocomprascred WHERE compra.idCompra = :idCompra)";
        Query q = session.createQuery(hql);
        q.setParameter("idCompra", idCompra);
        return (BigDecimal) q.uniqueResult();
    }

    @Override
    public List<Pagocompracredito> listarPagosPorFechas(Session session, Date fechaInicio, Date fechaFin) throws Exception {
        List<Pagocompracredito> lista = null;
        String hql = "FROM Pagocompracredito WHERE cast(fechaPago as date) BETWEEN :fechaInicio AND :fechaFin ORDER BY fechaPago desc";
        Query q = session.createQuery(hql);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        lista = q.list();
        for (Pagocompracredito l : lista) {
            Hibernate.initialize(l.getCronocomprascred().getCompra());
        }
        for (Pagocompracredito l : lista) {
            Hibernate.initialize(l.getCronocomprascred().getCompra().getProveedor());
        }
        return lista;
    }
    
}

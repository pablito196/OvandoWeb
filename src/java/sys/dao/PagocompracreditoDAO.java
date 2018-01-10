
package sys.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import sys.model.Pagocompracredito;

public interface PagocompracreditoDAO {
    public boolean guardarPagoCompras(Session session, Pagocompracredito pagoCompras) throws Exception;
    public Pagocompracredito buscarPagoCompra(Session session, Integer idCronogramaC) throws Exception;
    public BigDecimal calcularMontoPagado(Session session, Integer idCompra) throws Exception;
    public List<Pagocompracredito> listarPagosPorFechas(Session session, Date fechaInicio, Date fechaFin) throws Exception;
}


package sys.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import sys.model.Pagoventascredito;


public interface PagoventascreditoDAO {
    public Pagoventascredito buscarPagoVenta(Session session, Integer idCronoV) throws Exception;
    public BigDecimal calcularMontoPagado(Session session, Integer numeroVenta) throws Exception;
    public boolean guardarPagoVentas(Session session, Pagoventascredito pagoVentas) throws Exception;
    //metodo para determinar el ultimo saldo de una venta a credito
    public BigDecimal buscarUltimoSaldoPorVenta(Session session, Integer numeroVenta) throws Exception;
    //metodo para buscar pagos entre fechas determinadas
    public List<Pagoventascredito> listarPagosPorFechas(Session session, Date fechaInicio, Date fechaFin) throws Exception;
}

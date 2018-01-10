
package sys.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import sys.model.Venta;


public interface VentaDAO 
{
    public List<Venta> listarVenta();
    public List<Venta> listarVentaCredito();
    public List<Venta> listarVentaCreditoVigentePorCliente(Session session, String ciCliente) throws Exception;
    public void nuevaVenta(Venta venta);
    public boolean actualizarVenta(Session session, Venta venta) throws Exception;
    public void eliminarVenta(Venta venta);
    public Venta buscarVenta(Session session, Integer numeroVenta) throws Exception;
    //Averiguar si la tabla venta tiene registros
    public Long obtenerTotalRegistrosVenta(Session session);
    //obtener el ultimo registro de la tabla venta en la BD
    public Venta obtenerUltimoRegistro(Session session)throws Exception;
    //metodo para guardar el registro en la tabla venta de la BD
    public boolean guardarVenta(Session session, Venta venta) throws Exception;
    //obtener el ultimo registro de la tabla preventa en la BD por tiempo de registro
    public Venta obtenerUltimoRegistroTiempo(Session session, Long tiempo)throws Exception;
    //metodo para listar las ventas no facturadas
    public List<Venta> listarVentasNoFacturadas(Session session, String ciCliente) throws Exception;
    //metodo para listar ventas entre dos fechas
    public List<Venta> listarVentasPorFecha(Session session, Date fechaInicio, Date fechaFin) throws Exception;
    //metodo para listar ventas entre dos fechas y por clientes
    public List<Venta> listarVentasPorFechaClientes(Session session, Date fechaInicio, Date fechaFin, String clientes) throws Exception;
    //metodo para buscar ventas
    public List<Venta> buscarVenta(Session session, Date fechaInicio, Date fechaFin, String estado, Integer idTipopago, Integer idAlmacen) throws Exception;
    //metodo para buscar ventas por proveedor
    public List<Venta> buscarVentasPorClientes(Session session, Date fechaInicio, Date fechaFin, String estado, Integer idTipopago, Integer idAlmacen, String clientes) throws Exception;
    //metodo para buscar ventas a credito por cliente
    public List<Venta> buscarVentasCreditoPorClientes(Session session, Date fechaInicio, Date fechaFin, String clientes) throws Exception;
}

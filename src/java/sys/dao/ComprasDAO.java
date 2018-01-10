package sys.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import sys.model.Compra;

public interface ComprasDAO 
{
    public List<Compra> listarCompra();
    public List<Compra> listarCompraCredito();
    public List<Compra> listarCompraCreditoVigentePorProveedor(Session session, Integer idProveedor) throws Exception;
    public void nuevaCompra(Compra compra);
    public void actualizarCompra(Compra compra);
    public void eliminarCompra(Compra compra);
    public Long obtenerTotalRegistrosCompra(Session session);
    public Compra obtenerUltimoRegistro(Session session) throws Exception;
    public boolean guardarCompra(Session session, Compra compra) throws Exception;
    public Compra buscarCompra(Session session, Integer idCompra) throws Exception;
    public List<Compra> buscarCompra(Session session, Date fechaInicio, Date fechaFin, String estado, Integer idTipopago) throws Exception;
    public List<Compra> buscarComprasPorProveedor(Session session, Date fechaInicio, Date fechaFin, String estado, Integer idTipopago, String clientes) throws Exception;
    //metodo para listar compras entre fechas
    public List<Compra> listarComprasPorFecha(Session session, Date fechaInicio, Date fechaFin) throws Exception;
}

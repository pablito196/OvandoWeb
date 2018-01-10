package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Detallecompra;

public interface DetallecompraDAO 
{
    public List<DetallecompraDAO> listarDetalleCompra(Session session, Integer idCompra) throws Exception;
    public void actualizarDetalleCompra(DetallecompraDAO categoria);
    public Detallecompra buscarUltimaCompraDetallePorArticulo(Session session, String codigoArticulo) throws Exception;
    //metodo para guardar el registro en la tabla preventa de la BD
    public boolean guardarDetalleCompra(Session session, Detallecompra detalleCompra) throws Exception;
    public List<Detallecompra> listarDetalleCompraPorCompra(Session session, Integer idCompra);
}

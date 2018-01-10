package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Ventafactura;


public interface VentafacturaDAO {
    public boolean guardarVentaFactura(Session session, Ventafactura ventaFactura) throws Exception;
    //metodo para listar las facturas por ventas
    public List<Ventafactura> listarVentafactura();
    public List<Ventafactura> buscarFacturaPorVenta(Session session, Integer numeroVenta);
}

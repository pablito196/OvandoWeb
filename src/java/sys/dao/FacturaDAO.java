package sys.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import sys.model.Factura;

public interface FacturaDAO 
{
    public List<Factura> listarFacturas();
    public void nuevaFactura(Factura factura);
    public boolean actualizarFactura(Session session, Factura factura) throws Exception;
    public void eliminarFactura(Factura factura);
    //obtener el ultimo registro de la tabla factura en la BD de acuerdo al numero de autorizacion
    public Factura obtenerUltimaFactura(Session session, String numeroAutorizacion)throws Exception;
    //obtener el ultimo registro de la tabla factura en la BD 
    public Factura obtenerUltimaFacturaTiempo(Session session, Long tiempo)throws Exception;
    //metodo para guardar el registro en la tabla factura de la BD
    public boolean guardarFactura(Session session, Factura factura) throws Exception;
    //metodo para buscar la Factura por id
    public Factura buscarFactura(Session session, Integer idFactura) throws Exception;
    //metodo para listar facturas por venta
    public List<Factura> listarFacturasPorVenta(Session session, Integer numeroVenta);
    //metodo para listar faacturas por fechas
    public List<Factura> listarFacturasPorFecha(Session session, Date fechaInicio, Date fechaFin) throws Exception;
}

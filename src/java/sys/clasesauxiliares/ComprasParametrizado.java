
package sys.clasesauxiliares;

import ConexionBD.ConexionMySql;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ComprasParametrizado {
    Connection conexion = null;
    public void getReporte(String ruta, String fechaInicio, String fechaFin, String estado, Integer idTipopago, String usuario, String proveedores) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            conexion = ConexionMySql.conectar("localhost", "root", "1c0n37", "ovandos");
            System.out.println("se conecto");
        } catch (Exception ex) {
            System.out.println("No se logro conectar");
            Logger.getLogger(FacturaOficial.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT IdCompra, FechaCompra, MontoCompra, Estado, p.DescPago,");
        sql.append(" (SELECT sum(p.MontoPagado) as total");
        sql.append(" FROM pagocompracredito p inner join");
        sql.append(" cronocomprascred c ON c.idCronogramaC = p.idCronogramaC");
        sql.append(" inner join compra v ON c.IdCompra = v.IdCompra");
        sql.append(" where p.idCronogramaC in (SELECT IdCronogramaC");
        sql.append(" FROM cronocomprascred)");
        sql.append(" and v.IdCompra=compra.IdCompra) as PagadoAlCredito,");
        sql.append(" 0 as PagosAlContado, pr.Nombre, u.LoginUsuario, FacturaCompra, NumeroRecibo, compra.idTipopago,");
        sql.append(" (SELECT NombreSucursal FROM Sucursal WHERE idSucursal = compra.idAlmacen) as sucursal");
        sql.append(" FROM");
        sql.append(" compra inner join proveedor pr ON pr.IdProveedor = compra.IdProveedor");
        sql.append(" inner join usuario u ON u.IdUsuario = compra.IdUsuario");
        sql.append(" inner join tipopago p ON p.idTipoPago = compra.idTipoPago");
        sql.append(" WHERE");
        sql.append(" IdCompra IN (SELECT IdCompra FROM cronocomprascred)");
        sql.append(" AND compra.FechaCompra >= '").append(fechaInicio).append("' AND compra.FechaCompra <= '").append(fechaFin).append("'");
        if(!"ANULADA".equals(estado))
        {
            sql.append(" AND compra.estado <> 'ANULADA'");
        }
        else
        {
            sql.append(" AND compra.estado = 'ANULADA'");
        }
        if(idTipopago > 0)
        {
            sql.append(" AND compra.idTipopago = ").append(idTipopago);
        }
        if(proveedores != null)
        {
            sql.append(" AND pr.nit in ").append(proveedores);
        }
        sql.append(" UNION ALL");
        sql.append(" SELECT IdCompra, FechaCompra, MontoCompra, Estado, p.DescPago, 0 as PagadoAlCredito,");
        sql.append(" (SELECT sum(CostoTotal) AS CostoTotal");
        sql.append(" FROM detallecompra dv inner join");
        sql.append(" compra v ON dv.IdCompra = v.IdCompra");
        sql.append(" WHERE dv.IdCompra = compra.IdCompra) AS PagosAlContado,");
        sql.append(" pr.Nombre, u.LoginUsuario, FacturaCompra, NumeroRecibo, compra.idTipopago,");
        sql.append(" (SELECT NombreSucursal FROM Sucursal WHERE idSucursal = compra.idAlmacen) as sucursal");
        sql.append(" FROM");
        sql.append(" compra inner join proveedor pr ON pr.IdProveedor = compra.IdProveedor");
        sql.append(" inner join usuario u ON u.IdUsuario = compra.IdUsuario");
        sql.append(" inner join tipopago p ON p.idTipoPago = compra.idTipoPago");
        sql.append(" WHERE");
        sql.append(" IdCompra NOT IN (SELECT IdCompra FROM cronocomprascred)");
        sql.append(" AND compra.FechaCompra >= '").append(fechaInicio).append("' AND compra.FechaCompra <= '").append(fechaFin).append("'");
        if(!"ANULADA".equals(estado))
        {
            sql.append(" AND compra.estado <> 'ANULADA'");
        }
        else
        {
            sql.append(" AND compra.estado = 'ANULADA'");
        }
        if(idTipopago > 0)
        {
            sql.append(" AND compra.idTipopago = ").append(idTipopago);
        }
        if(proveedores != null)
        {
            sql.append(" AND pr.nit in ").append(proveedores);
        }
        sql.append(" ORDER BY FechaCompra desc, IdCompra desc");
        
        System.out.println("consulta: "+sql.toString());
        ResultSet resu = ConexionMySql.Consulta(sql.toString());

        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        //Se definen los parametros que el reporte necesita
       // String[][] veParameters=new String[][]{ {"consulta", registros},{"FechaI",fechaIni },{"FechaF",fechaFi},{"usuario",Usuario.getCurrentUser()[1].toString()}};
        String[][] veParameters = new String[][]{{"consulta", ConexionMySql.getCampoUnicoStrin(sql.toString())},{"FechaI",fechaInicio },{"FechaF", fechaFin},{"usuario",usuario}};
        
        // String[][] veParameters=new String[][]{ {"consulta", registros},{"FechaI",fechaIni },{"FechaF",fechaFi},{"usuario",Usuario.getCurrentUser()[1].toString()}};
        HashMap parameters = new HashMap();

        for (int x = 0; x < veParameters.length; x++) {
            //el orden para el uso correcto de estos parametros es como sigue
            //parameters.put(key_ParametroJasper, valuePersonalizado);
            if (veParameters[x][0].equals("logo")) {
                parameters.put(veParameters[x][0], this.getClass().getResourceAsStream(veParameters[x][1]));
            } else {
                parameters.put(veParameters[x][0], veParameters[x][1]);
            }
        }

        try {
            File file = new File(ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.addHeader("Content-Type", "application/pdf");

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrRS);

            JRExporter jrExporter = null;
            jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

            if (jrExporter != null) {
                try {
                    jrExporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

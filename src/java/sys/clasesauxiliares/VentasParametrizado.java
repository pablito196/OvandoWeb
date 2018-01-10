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

public class VentasParametrizado {
    Connection conexion = null;
    public void getReporte(String ruta, String fechaInicio, String fechaFin, String estado, Integer idTipopago, Integer idAlmacen, String usuario, String clientes) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            conexion = ConexionMySql.conectar("localhost", "root", "1c0n37", "ovandos");
            System.out.println("se conecto");
        } catch (Exception ex) {
            System.out.println("No se logro conectar");
            Logger.getLogger(FacturaOficial.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringBuilder sqlQuery1 = new StringBuilder();
        sqlQuery1.append("SELECT DISTINCT");
        sqlQuery1.append(" venta.NumeroVenta, venta.NumeroPreventa, FechaVenta, MontoTotal, venta.Estado,");
        sqlQuery1.append(" (SELECT sum(p.MontoPagado) as total");
        sqlQuery1.append(" FROM pagoventascredito p inner join");
        sqlQuery1.append(" cronoventascredito c ON c.idCronoV = p.idCronoV");
        sqlQuery1.append(" inner join venta v ON c.numeroventa = v.numeroventa");
        sqlQuery1.append(" where p.idCronoV in (SELECT IdCronoV");
        sqlQuery1.append(" FROM cronoventascredito WHERE p.FechaPago >= '").append( fechaInicio).append("' AND p.FechaPago <= '").append(fechaFin).append("') and venta.NumeroVenta = c.NumeroVenta");
        sqlQuery1.append(" ) as PagadoAlCredito,");
        sqlQuery1.append(" (SELECT sum(p.MontoPagado) as total");
        sqlQuery1.append(" FROM pagoventascredito p inner join");
        sqlQuery1.append(" cronoventascredito c ON c.idCronoV = p.idCronoV");
        sqlQuery1.append(" inner join venta v ON c.numeroventa = v.numeroventa");
        sqlQuery1.append(" where p.idCronoV in (SELECT IdCronoV");
        sqlQuery1.append(" FROM cronoventascredito where p.FechaPago <= '").append(fechaFin).append("') and venta.NumeroVenta = c.NumeroVenta) as PagadosCredito,");
        sqlQuery1.append(" 0 as PagosAlContado, cl.NombresCliente, u.LoginUsuario, TipoCambio, tp.idTipopago, tp.DescPago");
        sqlQuery1.append(" FROM venta inner join");
        sqlQuery1.append(" cliente cl ON cl.CiCliente = venta.CiCliente");
        sqlQuery1.append(" inner join usuario u ON u.IdUsuario = venta.IdUsuario");
        sqlQuery1.append(" inner join tipomoneda tm ON tm.idTipomoneda = venta.idTipomoneda");
        sqlQuery1.append(" inner join tipopago tp ON tp.idTipopago = venta.idTipopago");
        sqlQuery1.append(" inner join cronoventascredito crv On venta.NumeroVenta = crv.NumeroVenta");
        sqlQuery1.append(" WHERE venta.Estado <> 'EN ESPERA'");
        sqlQuery1.append(" AND venta.FechaVenta >= '").append(fechaInicio).append("' AND venta.FechaVenta <= '").append(fechaFin).append("'");
        if(!"ANULADA".equals(estado))
        {
            sqlQuery1.append(" AND venta.estado <> 'ANULADA'");
        }
        else
        {
            sqlQuery1.append(" AND venta.estado = 'ANULADA'");
        }
        if(idTipopago > 0)
        {
            sqlQuery1.append(" AND venta.idTipopago = ").append(idTipopago);
        }
        if(idAlmacen > 0)
        {
            sqlQuery1.append(" AND venta.idAlmacen = ").append(idAlmacen);
        }
        if(clientes != null)
        {
            sqlQuery1.append(" AND venta.ciCliente in ").append(clientes);
        }
        sqlQuery1.append(" UNION ALL SELECT");
        sqlQuery1.append(" NumeroVenta, venta.NumeroPreventa, FechaVenta, MontoTotal, venta.Estado, 0 as PagadoAlCredito, 0 as PagosCredito,");
        sqlQuery1.append(" (SELECT sum(Total) AS total");
        sqlQuery1.append(" FROM detalleventa dv inner join");
        sqlQuery1.append(" venta v ON dv.numeroventa = v.numeroventa");
        sqlQuery1.append(" WHERE dv.NumeroVenta = venta.numeroventa) AS PagosAlContado,");
        sqlQuery1.append(" cl.NombresCliente, u.LoginUsuario, TipoCambio, tp.idTipopago, tp.DescPago");
        sqlQuery1.append(" FROM venta inner join");
        sqlQuery1.append(" cliente cl ON cl.CiCliente = venta.CiCliente");
        sqlQuery1.append(" inner join usuario u ON u.IdUsuario = venta.IdUsuario");
        sqlQuery1.append(" inner join tipomoneda tm ON tm.idTipomoneda = venta.idTipomoneda");
        sqlQuery1.append(" inner join tipopago tp ON tp.idTipopago = venta.idTipopago");
        sqlQuery1.append(" WHERE");
        sqlQuery1.append(" NumeroVenta NOT IN (SELECT NumeroVenta FROM cronoventascredito)");
        sqlQuery1.append(" AND venta.FechaVenta >= '").append(fechaInicio).append("' AND venta.FechaVenta <= '").append(fechaFin).append("'");
        if(!"ANULADA".equals(estado))
        {
            sqlQuery1.append(" AND venta.estado <> 'ANULADA'");
        }
        else
        {
            sqlQuery1.append(" AND venta.estado = 'ANULADA'");
        }
        if(idTipopago > 0)
        {
            sqlQuery1.append(" AND venta.idTipopago = ").append(idTipopago);
        }
        if(idAlmacen > 0)
        {
            sqlQuery1.append(" AND venta.idAlmacen = ").append(idAlmacen);
        }
        if(clientes != null)
        {
            sqlQuery1.append(" AND venta.ciCliente in ").append(clientes);
        }
        sqlQuery1.append(" ORDER BY Fechaventa DESC, NumeroVenta DESC");
                
        System.out.println("consulta: "+sqlQuery1.toString());
        ResultSet resu = ConexionMySql.Consulta(sqlQuery1.toString());

        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        //Se definen los parametros que el reporte necesita
       // String[][] veParameters=new String[][]{ {"consulta", registros},{"FechaI",fechaIni },{"FechaF",fechaFi},{"usuario",Usuario.getCurrentUser()[1].toString()}};
        String[][] veParameters = new String[][]{{"consulta", ConexionMySql.getCampoUnicoStrin(sqlQuery1.toString())},{"FechaI",fechaInicio },{"FechaF", fechaFin},{"usuario",usuario}};
        
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

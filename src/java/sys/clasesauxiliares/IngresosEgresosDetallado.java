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

public class IngresosEgresosDetallado {
    Connection conexion = null;
    public void getReporte(String ruta, String fechaInicio, String fechaFin) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            conexion = ConexionMySql.conectar("localhost", "root", "1c0n37", "ovandos");
            System.out.println("se conecto");
        } catch (Exception ex) {
            System.out.println("No se logro conectar");
            Logger.getLogger(FacturaOficial.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT v.FechaVenta as Fecha,");
        sql.append(" CONCAT('Vent Nro. ',v.NumeroVenta, ' Pre. Vent Nro. ' ,pv.NumeroPreVenta,' Cliente: ',cl.NombresCliente) as concepto,");
        sql.append(" v.MontoTotal as Venta, 0 as Cobros, 0 as Compras, 0 as Gastos");
        sql.append(" FROM venta v");
        sql.append(" inner join preventa pv on v.NumeroPreVenta = pv.NumeroPreVenta");
        sql.append(" inner join cliente cl on pv.CiCliente = cl.CiCliente");
        sql.append(" WHERE v.Estado <> 'ANULADA'");
        sql.append(" and v.IdTipopago < 2");
        sql.append(" and CAST(v.FechaVenta AS DATE) >= '").append( fechaInicio).append("' and CAST(v.FechaVenta AS DATE) <= '").append(fechaFin).append("'");
        sql.append(" UNION ALL");
        sql.append(" SELECT v.FechaVenta as Fecha,");
        sql.append(" CONCAT('Vent Nro. ',v.NumeroVenta, ' Cliente: ',cl.NombresCliente) as concepto,");
        sql.append(" v.MontoTotal as Venta, 0 as Cobros, 0 as Compras, 0 as Gastos");
        sql.append(" FROM venta v");
        sql.append(" inner join cliente cl on v.CiCliente = cl.CiCliente");
        sql.append(" WHERE v.Estado <> 'ANULADA'");
        sql.append(" and v.IdTipopago < 2");
        sql.append(" and CAST(v.FechaVenta AS DATE) >= '").append( fechaInicio).append("' and CAST(v.FechaVenta AS DATE) <= '").append(fechaFin).append("'");
        sql.append(" and v.NumeroPreVenta = 0");
        sql.append(" UNION ALL");
        sql.append(" SELECT pvc.FechaPago as Fecha,");
        sql.append(" CONCAT('Cred Nro. ',v.NumeroVenta, ' Cliente: ', cl.NombresCliente) as concepto,");
        sql.append(" 0 as montoventa, pvc.MontoPagado, 0 as Compras, 0 as Gastos");
        sql.append(" FROM pagoventascredito pvc");
        sql.append(" inner join cronoventascredito cvc on pvc.IdCronoV = cvc.IdCronoV");
        sql.append(" inner join venta v on v.NumeroVenta = cvc.NumeroVenta");
        sql.append(" inner join cliente cl on v.CiCliente = cl.CiCliente");
        sql.append(" WHERE v.Estado <> 'ANULADA'");
        sql.append(" and v.IdTipopago = 2");
        sql.append(" and pvc.MontoPagado > 0");
        sql.append(" and CAST(pvc.FechaPago as DATE) >= '").append(fechaInicio).append("' and CAST(pvc.FechaPago as DATE) <= '").append(fechaFin).append("'");
        sql.append(" UNION ALL");
        sql.append(" SELECT c.FechaCompra as Fecha,");
        sql.append(" CONCAT('Compra Nro ',c.IdCompra, ' Proveedor: ' ,prov.Nombre) as concepto,");
        sql.append(" 0, 0, c.MontoCompra, 0");
        sql.append(" FROM compra c");
        sql.append(" inner join proveedor prov on c.IdProveedor = prov.IdProveedor");
        sql.append(" WHERE c.Estado <> 'ANULADA'");
        sql.append(" and c.IdTipopago < 2");
        sql.append(" and  CAST(c.FechaCompra AS DATE) >= '").append(fechaInicio).append("' and CAST(c.FechaCompra AS DATE) <= '").append(fechaFin).append("'");
        sql.append(" UNION ALL");
        sql.append(" SELECT pcc.FechaPago as Fecha,");
        sql.append(" CONCAT('Compra al credito, Comp N° ', c.IdCompra, ' Proveedor: ', prov.Nombre) as concepto,");
        sql.append(" 0, 0, pcc.MontoPagado, 0");
        sql.append(" FROM pagocompracredito pcc");
        sql.append(" inner join cronocomprascred ccc on pcc.IdCronogramaC = ccc.IdCronogramaC");
        sql.append(" inner join compra c on ccc.IdCompra = c.IdCompra");
        sql.append(" inner join proveedor prov on c.IdProveedor = prov.IdProveedor");
        sql.append(" WHERE c.Estado <> 'ANULADA'");
        sql.append(" and c.IdTipopago = 2");
        sql.append(" and pcc.MontoPagado > 0");
        sql.append(" and CAST(pcc.FechaPago AS DATE) >= '").append(fechaInicio).append("' and CAST(pcc.FechaPago AS DATE) <= '").append(fechaFin).append("'");
        sql.append(" UNION ALL");
        sql.append(" SELECT g.FechaHoraRegistro as Fecha,");
        sql.append(" CONCAT('Gasto Nro ',g.IdGasto, '  Concepto: ',  g.Concepto) as concepto,");
        sql.append(" 0, 0, 0, g.Monto");
        sql.append(" FROM gasto g");
        sql.append(" WHERE CAST(g.FechaHoraRegistro AS DATE) >= '").append(fechaInicio).append("' and CAST(g.FechaHoraRegistro AS DATE) <= '").append(fechaFin).append("'");
        sql.append(" ORDER BY Fecha desc");
        
                
        System.out.println("consulta: "+sql.toString());
        ResultSet resu = ConexionMySql.Consulta(sql.toString());

        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        //Se definen los parametros que el reporte necesita
       // String[][] veParameters=new String[][]{ {"consulta", registros},{"FechaI",fechaIni },{"FechaF",fechaFi},{"usuario",Usuario.getCurrentUser()[1].toString()}};
        String[][] veParameters = new String[][]{{"consulta", ConexionMySql.getCampoUnicoStrin(sql.toString())},{"fechaini",fechaInicio },{"fechafin", fechaFin}};
        
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

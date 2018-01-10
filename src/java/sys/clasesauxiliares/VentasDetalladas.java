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

public class VentasDetalladas {

    Connection conexion = null;

    public void getReporte(String ruta, String fechaInicio, String fechaFin) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            conexion = ConexionMySql.conectar("localhost", "root", "1c0n37", "ovandos");
            System.out.println("se conecto");
        } catch (Exception ex) {
            System.out.println("No se logro conectar");
            Logger.getLogger(FacturaOficial.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("Select v.NumeroVenta as venta, a.CodigoArticulo, a.Nombre, a.Fabricante, dv.Cantidad, dv.PrecioVentaReal,");
        sqlQuery.append(" (");
        sqlQuery.append(" dv.PrecioVentaReal - (select distinct case when dc.costounitario is null then 0 else dc.costounitario end");
        sqlQuery.append(" from detallecompra dc, compra c");
        sqlQuery.append(" where");
        sqlQuery.append(" c.idcompra = dc.idcompra");
        sqlQuery.append(" and dc.CodigoArticulo = a.CodigoArticulo");
        sqlQuery.append(" order by c.idcompra desc limit 1)");
        sqlQuery.append(" )* dv.Cantidad as ganancia,");
        sqlQuery.append(" 0 as Saldo,");
        sqlQuery.append(" dv.Total, v.FechaVenta as Fecha,");
        sqlQuery.append(" (select case when dv.IdFactura = 0 then 0 else NumeroFactura end");
        sqlQuery.append(" From factura Where IdFactura = dv.IdFactura");
        sqlQuery.append(" ) as NumFactura");
        sqlQuery.append(" From articulo a, venta v, detalleventa dv, tipopago tp");
        sqlQuery.append(" where dv.CodigoArticulo = a.CodigoArticulo");
        sqlQuery.append(" and dv.NumeroVenta = v.NumeroVenta");
        sqlQuery.append(" and v.idTipopago = tp.idTipopago");
        sqlQuery.append(" and tp.DescPago = 'CONTADO'");
        sqlQuery.append(" and v.Estado = 'VENDIDO'");
        sqlQuery.append(" and v.FechaVenta between '").append(fechaInicio).append("' and '").append(fechaFin).append("'");
        sqlQuery.append(" UNION ALL");
        sqlQuery.append(" Select v.NumeroVenta as venta, a.CodigoArticulo, a.Nombre, a.Fabricante, dv.Cantidad, pvc.MontoPagado, 0 as costo, pvc.Saldo,");
        sqlQuery.append(" pvc.MontoPagado,pvc.FechaPago as Fecha, 0 as NumFactura");
        sqlQuery.append(" From articulo a, venta v, detalleventa dv, tipopago tp, cronoventascredito cvc, pagoventascredito pvc");
        sqlQuery.append(" where dv.CodigoArticulo = a.CodigoArticulo");
        sqlQuery.append(" and dv.NumeroVenta = v.NumeroVenta");
        sqlQuery.append(" and v.idTipopago = tp.idTipopago");
        sqlQuery.append(" and tp.DescPago = 'CRÉDITO'");
        sqlQuery.append(" and v.NumeroVenta = cvc.NumeroVenta");
        sqlQuery.append(" and cvc.IdCronoV = pvc.IdCronoV");
        sqlQuery.append(" and v.Estado = 'VENDIDO'");
        sqlQuery.append(" and pvc.FechaPago between '").append(fechaInicio).append("' and '").append(fechaFin).append("'");
        sqlQuery.append(" order by Fecha asc, venta asc");

        System.out.println("consulta: "+sqlQuery.toString());
        ResultSet resu = ConexionMySql.Consulta(sqlQuery.toString());

        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        //Se definen los parametros que el reporte necesita
        String[][] veParameters = new String[][]{{"consulta", ConexionMySql.getCampoUnicoStrin(sqlQuery.toString())},{"fechaini",fechaInicio },{"fechafin", fechaFin}};
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

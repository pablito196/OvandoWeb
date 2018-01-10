package sys.clasesauxiliares;

import ConexionBD.ConexionMySql;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

public class VisualizarFactura {

    Connection conexion = null;

    public void getReporte(String ruta, Integer idFactura, Integer numeroFactura, String precioLiteral, Date fechaFactura, String razonSocial, BigDecimal totalVenta, String nit,
            Date fechaLimiteEmision, String codigoControl, String usuario, String numeroAutorizacion) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            conexion = ConexionMySql.conectar("localhost", "root", "1c0n37", "ovandos");
            System.out.println("se conecto");
        } catch (Exception ex) {
            System.out.println("No se logro conectar");
            Logger.getLogger(FacturaOficial.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Entra a visualizar factura");

        Map parametros = new HashMap();
        parametros.clear();
        parametros.put("idfactura", idFactura);
        parametros.put("numerofactura", numeroFactura);
        parametros.put("precioliteral", precioLiteral);
        /*parametros.put("logo", this.getClass().getResourceAsStream(logotipo));
         parametros.put("logo1", this.getClass().getResourceAsStream(logotipo1));*/
        parametros.put("fecha", fechaFactura);
        parametros.put("razonsocial", razonSocial);
        parametros.put("totalventa", totalVenta);
        parametros.put("nit", nit);
        parametros.put("fechalimiteemision", fechaLimiteEmision);
        parametros.put("codigocontrol", codigoControl);
        parametros.put("usuario", usuario);
        parametros.put("autorizacion", numeroAutorizacion);

        try {
            File file = new File(ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.addHeader("Content-Type", "application/pdf");

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);

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

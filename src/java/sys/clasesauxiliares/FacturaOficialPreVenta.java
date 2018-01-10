
package sys.clasesauxiliares;

import ConexionBD.ConexionMySql;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class FacturaOficialPreVenta {
    public void getReporte(String ruta, Integer idFactura, Integer numeroFactura, String precioLiteral, Date fechaFactura, String razonSocial, BigDecimal totalVenta, String nit, Date fechaLimiteEmision, String codigoControl, String usuario, String numeroAutorizacion, String leyenda) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Connection conexion = null;
        
        try {
            conexion = ConexionMySql.conectar("localhost", "root", "1c0n37", "ovandos");
            System.out.println("se conecto");
        } catch (Exception ex) {
            System.out.println("No se logro conectar");
            Logger.getLogger(FacturaOficial.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("id factura: "+idFactura);
        System.out.println("numero factura: "+numeroFactura);
        System.out.println("precio literal: "+precioLiteral);
        System.out.println("fecha factura: "+fechaFactura);
        System.out.println("razon social: "+razonSocial);
        System.out.println("total venta: "+totalVenta);
        System.out.println("nit: "+nit);
        System.out.println("fecha emision: "+fechaLimiteEmision);
        System.out.println("codigo control: "+codigoControl);
        System.out.println("usuario: "+usuario);
        System.out.println("numero autorizacion: "+numeroAutorizacion);
        //Se definen los parametros que el reporte necesita
        Map parameter = new HashMap();
        parameter.clear();
        parameter.put("idfactura", idFactura);
        parameter.put("numerofactura", numeroFactura);
        parameter.put("precioliteral", precioLiteral);
        parameter.put("fechafactura", fechaFactura);
        parameter.put("razonsocial", razonSocial);
        parameter.put("totalventa", totalVenta);
        parameter.put("nit", nit);
        parameter.put("fechalimiteemision", fechaLimiteEmision);
        parameter.put("codigocontrol", codigoControl);
        parameter.put("usuario", usuario);
        parameter.put("numeroautorizacion", numeroAutorizacion);
        parameter.put("leyenda", leyenda);

        try {
            File file = new File(ruta);

            /*HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.addHeader("Content-Type", "application/pdf");*/

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

             JasperPrintManager.printReport(jasperPrint, false);
            
            /*JRExporter jrExporter = null;
            jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

            if (jrExporter != null) {
                try {
                    jrExporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }
            }*/

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


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

public class ListadePrecios {
    Connection conexion = null;
    public void getReporte(String ruta, Integer idCategoria, Integer idSubcategoria, String codigoArticulo) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            conexion = ConexionMySql.conectar("localhost", "root", "1c0n37", "ovandos");
            System.out.println("se conecto");
        } catch (Exception ex) {
            System.out.println("No se logro conectar");
            Logger.getLogger(FacturaOficial.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("Select a.CodigoArticulo, a.Nombre, a.PrecioVenta, a.PrecioDocena, a.PrecioEspecial");
        sqlQuery.append(" From articulo a");
        if(idCategoria != null)
        {
            sqlQuery.append(", categoriaarticulo cart, subcategoriaarticulo scart");
            sqlQuery.append(" Where cart.IdCategoria = scart.IdCategoria");
            sqlQuery.append(" and scart.IdSubcategoria = a.IdSubcategoria");
            sqlQuery.append(" and cart.IdCategoria = ").append(idCategoria);
            if(idSubcategoria != null)
            {
                sqlQuery.append(" and scart.IdSubCategoria = ").append(idSubcategoria);
            }
            /*if(codigoArticulo != null || !"".equals(codigoArticulo))
            {
                sqlQuery.append(" and a.codigoArticulo = '").append(codigoArticulo).append("'");
            }*/
        }
        sqlQuery.append(" Order by a.CodigoArticulo asc");
        
                
        System.out.println("consulta: "+sqlQuery.toString());
        ResultSet resu = ConexionMySql.Consulta(sqlQuery.toString());

        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        //Se definen los parametros que el reporte necesita
       // String[][] veParameters=new String[][]{ {"consulta", registros},{"FechaI",fechaIni },{"FechaF",fechaFi},{"usuario",Usuario.getCurrentUser()[1].toString()}};
        String[][] veParameters = new String[][]{{"consulta", ConexionMySql.getCampoUnicoStrin(sqlQuery.toString())}};
        
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

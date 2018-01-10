package sys.clasesauxiliares;

import ConexionBD.ConexionMySql;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ProveedorDatos {

    Connection conexion = null;

    public void getReporte(String ruta, Integer idCiudad, Integer idCategoria, Integer idSubcategoria, String codigoArticulo, boolean todosLosProveedores) throws ClassNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        try {
            conexion = ConexionMySql.conectar("localhost", "root", "1c0n37", "ovandos");
            System.out.println("se conecto");
        } catch (Exception ex) {
            System.out.println("No se logro conectar");
            Logger.getLogger(FacturaOficial.class.getName()).log(Level.SEVERE, null, ex);
        }

        StringBuilder sqlQuery = new StringBuilder();
        System.out.println("Entra a buscar provedores con parametro");
        if (todosLosProveedores) {
            sqlQuery.append("SELECT p.IdProveedor, p.nombre, p.direccion, p.nit, p.telefono, p.celular, ci.nombreciudad as Ciudad");
            sqlQuery.append(" FROM Proveedor p, Ciudad ci");
            sqlQuery.append(" WHERE p.idCiudad = ci.idCiudad");
            sqlQuery.append(" ORDER BY p.IdProveedor asc");
        } else {
            sqlQuery.append("SELECT DISTINCT p.IdProveedor, p.nombre, p.direccion, p.nit, p.telefono, p.celular, ci.nombreciudad as Ciudad");
            sqlQuery.append(" FROM Proveedor p, Ciudad ci, Compra comp, Detallecompra dc, Articulo art, Categoriaarticulo ca, Subcategoriaarticulo sc");
            sqlQuery.append(" WHERE p.idCiudad = ci.idCiudad");
            sqlQuery.append(" AND p.idProveedor = comp.idProveedor");
            sqlQuery.append(" AND comp.idCompra = dc.idCompra");
            sqlQuery.append(" AND dc.codigoArticulo = art.codigoArticulo");
            sqlQuery.append(" AND sc.idSubcategoria = art.idSubcategoria");
            sqlQuery.append(" AND sc.idCategoria = ca.idCategoria");
            if (idCiudad != null) {
                sqlQuery.append(" AND ci.idCiudad = '").append(idCiudad).append("'");
            }
            if (idCategoria != null) {
                sqlQuery.append(" AND ca.idCategoria = '").append(idCategoria).append("'");
            }
            if (idSubcategoria != null) {
                sqlQuery.append(" AND sc.idSubcategoria = '").append(idSubcategoria).append("'");
            }
            if (codigoArticulo != null) {
                sqlQuery.append(" AND art.codigoArticulo = '").append(codigoArticulo).append("'");
            }
            sqlQuery.append(" ORDER BY p.IdProveedor asc");
        }

        ResultSet resu = ConexionMySql.Consulta(sqlQuery.toString());

        JRResultSetDataSource jrRS = new JRResultSetDataSource(resu);
        //Se definen los parametros que el reporte necesita
        String[][] veParameters = new String[][]{{"consulta", ConexionMySql.getCampoUnicoStrin(sqlQuery.toString())}};
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

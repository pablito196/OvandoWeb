/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionBD;

/**
 *
 * @author ING. JUAN PABLO CORDERO ROMERO
 */
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
public class ConexionMySql 
{
    public static Connection con_mysql;
    public static Statement st;
    public static Statement st1;
    public static Statement st2;
    public static Statement st3;
    public static Statement st4;
    public static Statement stm_bloqueo;
	public static Connection conectar(String Host, String Usuario, String Password, String BD) throws Exception 
	{
		try 
		{
			String BdURL = "jdbc:mysql://" + Host + "/" + BD;
			Class.forName("com.mysql.jdbc.Driver");
			con_mysql = java.sql.DriverManager.getConnection(BdURL, Usuario, Password);
                        con_mysql.setAutoCommit(false);
			st = (Statement) con_mysql.createStatement();
			st1 = (Statement) con_mysql.createStatement();
                        st2 = (Statement) con_mysql.createStatement();
                        st3 = (Statement) con_mysql.createStatement();
                        st4 = (Statement) con_mysql.createStatement();
                        stm_bloqueo = (Statement) con_mysql.createStatement();
                        //JOptionPane.showMessageDialog(null,"Conexion MySql establecida");
                        
		} 
		catch (Exception e) 
		{
                    e.printStackTrace();
                    throw new Exception(e);
		}
		return con_mysql;
	}
        
        public static synchronized Connection getConnection()
        {
            return con_mysql;
        }
        public static synchronized void aceptarcambios() throws SQLException
        {
           // try
            //{
                con_mysql.commit();
            //}
            //catch(Exception e)
            //{
             //   System.err.println(e);
           // }
        }
        
        public static synchronized void cerrarCall() 
        {
            try
            {
                con_mysql.close();
                
            }
            catch(Exception e)
            {
            }
        }
        //Metodo utilizado para cerrar el resulset de datos
        public static synchronized void cerrarConexion(ResultSet rs) 
        {
            try
            {
                rs.close();
            } 
            catch(Exception e)
            {
            }
        }
        //Metodo utilizado para cerrar la conexion
        public static synchronized void cerrarConexion() 
        {
            try
            {
                con_mysql.close();
                
            } 
            catch(Exception e)
            {
            }
        }
        //Metodo utilizado para deshacer los cambios en la base de datos
        public static synchronized void deshacerCambios() 
        {
            try
            {
                con_mysql.rollback();
            }
            catch(Exception e)
            {
            }
        }
        
       public static ResultSet Consulta(String consulta)
       {
		ResultSet rs=null;
		
                try
		{
			rs = st.executeQuery(consulta);
                        //rs.close();
                        //st.close();    
  		}
                catch(Exception e)
		{
			System.out.println(e);
			
		}
		return rs;
        }
       
       public static ResultSet ConsultaAuxiliar(String consulta)
       {
		ResultSet rs=null;
		
                try
		{
			rs = st2.executeQuery(consulta);
                        //rs.close();
                        //st.close();    
  		}
                catch(Exception e)
		{
			System.out.println(e);
			
		}
		return rs;
        }
       
       public static ResultSet ConsultaAuxiliar2(String consulta)
       {
		ResultSet rs=null;
		
                try
		{
			rs = st3.executeQuery(consulta);
                        //rs.close();
                        //st.close();    
  		}
                catch(Exception e)
		{
			System.out.println(e);
			
		}
		return rs;
       }
       
       
       public static ResultSet ConsultaAuxiliar3(String consulta)
       {
		ResultSet rs=null;
		
                try
		{
			rs = st4.executeQuery(consulta);
                        //rs.close();
                        //st.close();    
  		}
                catch(Exception e)
		{
			System.out.println(e);
			
		}
		return rs;
        }
       
       public static void ConsultaActiva(String consulta)
       {
		//ResultSet rs=null;
		
                try
		{
			st1.executeUpdate(consulta);
                        //con_mysql.commit();
                        
  		}
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		//return rs;
        }
       
       public static void BloquearTabla(String tabla, String accion) throws SQLException
       {
           try
           {
               stm_bloqueo.execute("LOCK TABLES "+tabla+" "+accion);
           }
           catch(SQLException ex)
           {
               System.out.println("SQL Exception caught "+ ex.getMessage() );
               throw new SQLException(ex.getMessage() ,ex.getSQLState() );
           }
       }
       
       public static void DesbloquearTabla() throws SQLException
       {
           try
           {
               stm_bloqueo.execute("UNLOCK TABLES");
               //stm_bloqueo.close();
           }
           catch(SQLException ex)
           {
               System.out.println("SQL Exception caught "+ ex.getMessage() );
               throw new SQLException(ex.getMessage() ,ex.getSQLState() );
           }
       }
       
       /*Statement stmt = null;
        try
        {
            stmt = connection.createStatement();
            stmt.execute("LOCK TABLES mytablename WRITE");
            int rowNumber = stmt.executeUpdate(sql);
        }
        catch(SQLException ex) 
        {
            System.out.println("SQL Exception caught + ex.getMessage() );
            throw new SQLException(ex.getMessage() ,ex.getSQLState() );
        }
        finally
        {
            if(stmt != null)
            {
                stmt.execute("UNLOCK TABLES");
                stmt.close();
            }
        }*/
       public static String getCampoUnicoStrin(String sql)
    {
        String xTmp="";
        ResultSet rs = ConsultaAuxiliar(sql);
        try 
        {
            while (rs.next()) 
            {
                xTmp=rs.getString(1);
                break;
            }
        } catch (Exception e) 
        {
            
            return xTmp="";
        }
        //cerrarConexion();
        //d.cierraConexion();
        return xTmp;
    }
    }

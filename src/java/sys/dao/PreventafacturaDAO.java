
package sys.dao;

import org.hibernate.Session;
import sys.model.Preventafactura;


public interface PreventafacturaDAO {
    //metodo para guardar el registro en la tabla preventafactura de la BD
    public boolean guardarPreventaFactura(Session session, Preventafactura preventafactura) throws Exception;
}

package sys.imp;

import org.hibernate.Session;
import sys.dao.PreventafacturaDAO;
import sys.model.Preventafactura;


public class PreventafacturaDAOImp implements PreventafacturaDAO{

    @Override
    public boolean guardarPreventaFactura(Session session, Preventafactura preventafactura) throws Exception {
        session.save(preventafactura);
        return true;
    }
    
}

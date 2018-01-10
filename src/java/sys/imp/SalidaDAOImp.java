package sys.imp;

import org.hibernate.Session;
import sys.dao.SalidaDAO;
import sys.model.Salida;


public class SalidaDAOImp implements SalidaDAO{

    @Override
    public boolean guardarSalida(Session session, Salida salida) throws Exception {
        session.save(salida);
        return true;
    }
    
}

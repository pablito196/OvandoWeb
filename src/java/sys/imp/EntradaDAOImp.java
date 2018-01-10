
package sys.imp;

import org.hibernate.Session;
import sys.dao.EntradaDAO;
import sys.model.Entrada;

public class EntradaDAOImp implements EntradaDAO{

    @Override
    public boolean guardarEntrada(Session session, Entrada entrada) throws Exception {
        session.save(entrada);
        return true;
    }
    
}

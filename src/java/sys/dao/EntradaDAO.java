
package sys.dao;

import org.hibernate.Session;
import sys.model.Entrada;


public interface EntradaDAO {
    public boolean guardarEntrada(Session session, Entrada entrada) throws Exception;
}

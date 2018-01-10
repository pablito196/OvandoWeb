
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Banco;


public interface BancoDAO {
    public List<Banco> listarBancos();
    public void nuevoBanco(Banco banco);
    public void actualizarBanco(Banco banco);
    public void eliminarBanco(Banco banco);
    public Banco buscarBanco(Session session, Integer idBanco) throws Exception;
}

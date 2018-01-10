
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Tipousuario;

public interface TipousuarioDAO {
    public List<Tipousuario> listarTipousuario();
    public void nuevoTipousuario(Tipousuario tipousuario);
    public void actualizarTipousuario(Tipousuario tipousuario);
    public void eliminarTipousuario(Tipousuario tipousuario);
    public Tipousuario buscarusuario(Session session, Integer idTipousuario) throws Exception;
}

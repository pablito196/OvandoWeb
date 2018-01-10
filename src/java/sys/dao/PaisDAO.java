
package sys.dao;

import java.util.List;
import sys.model.Pais;


public interface PaisDAO {
    public List<Pais> listarPaises();
    public void nuevoPais(Pais pais);
    public void actualizarPais(Pais pais);
    public void eliminarPais(Pais pais);
}

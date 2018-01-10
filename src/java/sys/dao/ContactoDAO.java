
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Contacto;

public interface ContactoDAO {
    public List<Contacto> listarContactos();
    public void actualizarCategoria(Contacto contacto);
    public void eliminarCategoria(Contacto contacto);
    
    public List<Contacto> listarContactosPorProveedor(Session session, Integer idProveedor);
    public boolean guardarContacto(Session session, Contacto contacto);
    
}

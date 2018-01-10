
package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Empleado;
import sys.model.Usuario;


public interface UsuarioDAO 
{
    public Usuario obtenerDatosPorUsuario(Usuario usuario, Integer idSucursal);
    public Usuario login(Usuario usuario, Integer idSucursal);
    public boolean guardarUsuario(Session session, Usuario usuario) throws Exception;
    public boolean actualizarUsuario(Session session, Usuario usuario) throws Exception;
    public Usuario buscarUsuario(Session session, Integer idEmpleado) throws Exception;
    public List<Usuario> listarUsuarios();
    public Usuario buscarUsuarioPorId(Session session, Integer idUsuario) throws Exception;

}

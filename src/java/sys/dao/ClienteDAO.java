package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Cliente;

public interface ClienteDAO 
{
    public List<Cliente> listarClientes();
    public void nuevoCliente(Cliente cliente);
    public void actualizarCliente(Cliente cliente);
    public void eliminarCliente(Cliente cliente);
    
    //este metodo se utilizara en la preventa y venta en el bean
    public Cliente obtenerClientePorCi(Session session, String ciCliente) throws Exception;
}

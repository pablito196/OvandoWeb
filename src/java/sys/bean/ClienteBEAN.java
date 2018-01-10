package sys.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sys.dao.ClienteDAO;
import sys.imp.ClienteDAOImp;
import sys.model.Cliente;


@ManagedBean
@ViewScoped
public class ClienteBEAN {

   private List<Cliente> listaClientes;
    private Cliente cliente;
     private List<Cliente> listaFiltradaClientes;
    
    public ClienteBEAN() 
    {
        cliente = new Cliente();
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaFiltradaClientes() {
        return listaFiltradaClientes;
    }

    public void setListaFiltradaClientes(List<Cliente> listaFiltradaClientes) {
        this.listaFiltradaClientes = listaFiltradaClientes;
    }
    
    
    
    public List<Cliente> getListaClientes() {
        ClienteDAO dao =  new ClienteDAOImp();
        listaClientes = dao.listarClientes();
        return listaClientes;
    }
    
    public void prepararNuevoCliente()
    {
        cliente = new Cliente();
    }
    
    public void nuevoCliente()
    {
        ClienteDAO dao = new ClienteDAOImp();
        dao.nuevoCliente(cliente);
    }
    
    public void actualizarCliente()
    {
        ClienteDAO dao =  new ClienteDAOImp();
        dao.actualizarCliente(cliente);
        cliente = new Cliente();
    }
    
    public void eliminarCategoria()
    {
        ClienteDAO dao = new ClienteDAOImp();
        dao.eliminarCliente(cliente);
        cliente = new Cliente();
    }
}


package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Cuentabancaria;


public interface CuentabancariaDAO {
    public List<Cuentabancaria> listarCuentasbancariasPorProveedor(Session session, Integer idProveedor);
    public boolean guardarCuentabancaria(Session session, Cuentabancaria cuentaBancaria);
    public void actualizarCuentabancaria(Cuentabancaria cuentaBancaria);
    public void eliminarCuentabancaria(Cuentabancaria cuentaBancaria);
}

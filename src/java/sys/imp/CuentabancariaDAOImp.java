package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.CuentabancariaDAO;
import sys.model.Cuentabancaria;

public class CuentabancariaDAOImp implements CuentabancariaDAO{

    @Override
    public boolean guardarCuentabancaria(Session session, Cuentabancaria cuentaBancaria) {
        session.save(cuentaBancaria);
        return true;
    }

    @Override
    public void actualizarCuentabancaria(Cuentabancaria cuentaBancaria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarCuentabancaria(Cuentabancaria cuentaBancaria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuentabancaria> listarCuentasbancariasPorProveedor(Session session, Integer idProveedor) {
        String hql = "FROM Cuentabancaria WHERE idProveedor = :idProveedor";
        Query q = session.createQuery(hql);
        q.setParameter("idProveedor", idProveedor);
        return q.list();
    }
    
}

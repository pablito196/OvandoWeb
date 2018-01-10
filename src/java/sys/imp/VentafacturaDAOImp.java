
package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.VentafacturaDAO;
import sys.model.Ventafactura;
import sys.util.HibernateUtil;

public class VentafacturaDAOImp implements VentafacturaDAO{

    @Override
    public boolean guardarVentaFactura(Session session, Ventafactura ventaFactura) throws Exception {
        session.save(ventaFactura);
        return true;
    }

    @Override
    public List<Ventafactura> listarVentafactura() {
        List<Ventafactura> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Ventafactura ORDER BY idVentaFactura desc, factura.idFactura desc";
        try
        {
            Query q = session.createQuery(hql).setMaxResults(200);
            lista =  q.list();
             for (Ventafactura l : lista) {
                Hibernate.initialize(l.getVenta());
            }
             for (Ventafactura l : lista) {
                Hibernate.initialize(l.getFactura());
            }
            t.commit();
            session.close();
        }
        catch(Exception e)
        {
            t.rollback();
        }
        return lista;
    }

    @Override
    public List<Ventafactura> buscarFacturaPorVenta(Session session, Integer numeroVenta) {
        String hql = "FROM Ventafactura WHERE venta.numeroVenta = :numeroVenta";
        Query q = session.createQuery(hql);
        q.setParameter("numeroVenta", numeroVenta);
        return q.list();
    }
    
}

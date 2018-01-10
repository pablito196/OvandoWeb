
package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import sys.dao.DetallePreventaDAO;
import sys.model.Detallepreventa;

public class DetallePreventaDAOImp implements DetallePreventaDAO 
{

    @Override
    public boolean guardarDetallePreventa(Session session, Detallepreventa detallePreventa) throws Exception {
        session.save(detallePreventa);
        return true;
    }

    @Override
    public boolean actualizarDetallePreventa(Session session, Detallepreventa detallePreventa) throws Exception {
        session.update(detallePreventa);
        return true;
    }

    @Override
    public List<Detallepreventa> listarDetallepreventaPornumeroPreventa(Session session, Integer numeroPreVenta) {
        List <Detallepreventa> lista = null;
        String hql = "FROM Detallepreventa WHERE preventa.numeroPreVenta = :numeroPreVenta";
        Query q = session.createQuery(hql);
        q.setParameter("numeroPreVenta", numeroPreVenta);
        lista =  q.list();
        for(Detallepreventa l : lista)
        {
            Hibernate.initialize(l.getArticulo());
        }
        return lista;
    }
    
}

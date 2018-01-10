
package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.PreventaDAO;
import sys.model.Preventa;
import sys.util.HibernateUtil;

public class PreventaDAOImp  implements PreventaDAO
{

    @Override
    public Preventa obtenerUltimoRegistro(Session session) throws Exception {
        String hql = "FROM Preventa ORDER BY numeroPreVenta DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        return (Preventa) q.uniqueResult();
    }

    @Override
    public Long obtenerTotalRegistrosPreventa(Session session) {
        String hql = "SELECT COUNT(*) FROM Preventa";
        Query q = session.createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public boolean guardarPreventa(Session session, Preventa preventa) throws Exception {
        session.save(preventa);
        return true;
    }

    @Override
    public Preventa obtenerUltimoRegistroTiempo(Session session, Long tiempo) throws Exception {
        String hql = "FROM Preventa WHERE tiempoRegistro = :tiempo";
        Query q = session.createQuery(hql);
        q.setParameter("tiempo", tiempo);
        
        return (Preventa) q.uniqueResult();
    }

    @Override
    public List<Preventa> listarPreventas() {
        List<Preventa> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Preventa WHERE estado = 'VIGENTE' ORDER BY fechaPreVenta desc, numeroPreVenta desc";
        try
        {
            lista = session.createQuery(hql).list();
            for (Preventa l : lista) {
                Hibernate.initialize(l.getCliente());
            }
            for (Preventa l : lista) {
                Hibernate.initialize(l.getUsuario().getEmpleado());
            }
            for (Preventa l : lista) {
                Hibernate.initialize(l.getAlmacen().getSucursal());
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
    public boolean actualizarPreventa(Session session, Preventa preventa) throws Exception {
        session.update(preventa);
        return true;
    }
    
}

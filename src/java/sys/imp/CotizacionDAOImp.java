package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.CotizacionDAO;
import sys.model.Cotizacion;
import sys.util.HibernateUtil;


public class CotizacionDAOImp implements CotizacionDAO {

    @Override
    public Long obtenerTotalRegistrosCotizacion(Session session) {
        String hql = "SELECT COUNT(*) FROM Cotizacion";
        Query q = session.createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public Cotizacion obtenerUltimoRegistro(Session session) throws Exception {
        String hql = "FROM Cotizacion ORDER BY numeroCotizacion DESC";
        Query q = session.createQuery(hql).setMaxResults(1);
        return (Cotizacion) q.uniqueResult();
    }

    @Override
    public boolean guardarCotizacion(Session session, Cotizacion cotizacion) throws Exception {
        session.save(cotizacion);
        return true;
    }

    @Override
    public List<Cotizacion> listarCotizacion() {
        List<Cotizacion> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Cotizacion WHERE estado = 'VIGENTE' ORDER BY fechaCotizacion desc, numeroCotizacion desc";
        try
        {
            lista = session.createQuery(hql).list();
            for (Cotizacion l : lista) {
                Hibernate.initialize(l.getCliente());
            }
            for (Cotizacion l : lista) {
                Hibernate.initialize(l.getUsuario().getEmpleado());
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
    
}


package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.CiudadDAO;
import sys.model.Ciudad;
import sys.util.HibernateUtil;

public class CiudadDAOImp implements CiudadDAO{

    @Override
    public List<Ciudad> listarCiudadesPorPais(Session session, Integer idPais) {
        String hql = "FROM Ciudad WHERE idPais = :idPais ORDER BY nombreCiudad ASC";
        Query q = session.createQuery(hql);
        q.setParameter("idPais", idPais);
        return q.list();
    }

    @Override
    public void nuevoBanco(Ciudad ciudad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarCiudad(Ciudad ciudad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarBanco(Ciudad ciudad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ciudad buscarCiudad(Session session, Integer idCiudad) throws Exception 
    {
        String hql = "FROM Ciudad WHERE idCiudad = :idCiudad";
        Query q = session.createQuery(hql);
        q.setParameter("idCiudad", idCiudad);
        
        return (Ciudad) q.uniqueResult();
    }

    @Override
    public List<Ciudad> listarCiudades() {
        List<Ciudad> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Ciudad ORDER BY nombreCiudad ASC";
        try
        {
            lista = session.createQuery(hql).list();
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

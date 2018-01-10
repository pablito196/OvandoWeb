
package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.TipomonedaDAO;
import sys.model.Tipomoneda;
import sys.util.HibernateUtil;


public class TipomonedaDAOImp implements TipomonedaDAO{

    @Override
    public List<Tipomoneda> listarTipomoneda() 
    {
        List<Tipomoneda> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Tipomoneda ORDER BY descripcion ASC";
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

    @Override
    public void nuevoTipomoneda(Tipomoneda tipomoneda) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarTipomoneda(Tipomoneda tipomoneda) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarTipomoneda(Tipomoneda tipomoneda) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tipomoneda buscarMoneda(Session session, Integer idTipomoneda) throws Exception {
        String hql = "FROM Tipomoneda WHERE idTipomoneda = :idTipomoneda";
        Query q = session.createQuery(hql);
        q.setParameter("idTipomoneda", idTipomoneda);
        return (Tipomoneda) q.uniqueResult();
    }
    
}


package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.TipousuarioDAO;
import sys.model.Tipousuario;
import sys.util.HibernateUtil;


public class TipousuarioDAOImp implements TipousuarioDAO{

    @Override
    public List<Tipousuario> listarTipousuario() {
        List<Tipousuario> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Tipousuario ORDER BY descripcion ASC";
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
    public void nuevoTipousuario(Tipousuario tipousuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarTipousuario(Tipousuario tipousuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarTipousuario(Tipousuario tipousuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tipousuario buscarusuario(Session session, Integer idTipousuario) throws Exception {
        String hql = "FROM Tipousuario WHERE idTipoUsuario = :idTipousuario";
        Query q = session.createQuery(hql);
        q.setParameter("idTipousuario", idTipousuario);
        return (Tipousuario) q.uniqueResult();
    }
    
}

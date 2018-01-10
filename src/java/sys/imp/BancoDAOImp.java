
package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.BancoDAO;
import sys.model.Banco;
import sys.util.HibernateUtil;


public class BancoDAOImp implements BancoDAO{

    @Override
    public List<Banco> listarBancos() 
    {
        List<Banco> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Banco ORDER BY nombreBanco ASC";
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
    public void nuevoBanco(Banco banco) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarBanco(Banco banco) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarBanco(Banco banco) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Banco buscarBanco(Session session, Integer idBanco) throws Exception {
        String hql = "FROM Banco WHERE idBanco = :idBanco";
        Query q = session.createQuery(hql);
        q.setParameter("idBanco", idBanco);
        return (Banco) q.uniqueResult();
    }
    
}

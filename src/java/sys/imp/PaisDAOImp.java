package sys.imp;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.PaisDAO;
import sys.model.Pais;
import sys.util.HibernateUtil;

public class PaisDAOImp implements PaisDAO{

    @Override
    public List<Pais> listarPaises() {
        List<Pais> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Pais ORDER BY nombrePais ASC";
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
    public void nuevoPais(Pais pais) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarPais(Pais pais) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarPais(Pais pais) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

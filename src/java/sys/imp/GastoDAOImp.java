
package sys.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.GastoDAO;
import sys.model.Gasto;
import sys.util.HibernateUtil;

public class GastoDAOImp  implements GastoDAO{

    @Override
    public List<Gasto> listarGastos() 
    {
        List<Gasto> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Gasto ORDER BY fechaHoraRegistro DESC, idGasto DESC";
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
    public void nuevoGasto(Gasto gasto) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(gasto);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        }
        finally
        {
            if(session != null)
                session.close();
        }
    }

    @Override
    public void actualizarGasto(Gasto gasto) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(gasto);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        }
        finally
        {
            if(session != null)
                session.close();
        }
    }

    @Override
    public void eliminarGasto(Gasto gasto) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(gasto);
            session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            session.getTransaction().rollback();
        }
        finally
        {
            if(session != null)
                session.close();
        }
    }

    @Override
    public List<Gasto> listarGastosPorFechas(Session session, Date fechaInicio, Date fechaFin) throws Exception {
        List<Gasto> lista = null;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String fechaIni = dateFormat.format(fechaInicio);
        String fechaFi = dateFormat.format(fechaFin);
        
        
        System.out.println("entra a listar gastos por fechas");
        //StringBuilder hql = new StringBuilder();
        
        //hql.append("FROM Gasto WHERE fechaHoraRegistro BETWEEN '").append(fechaIni).append("' AND '").append(fechaFi).append("' ORDER BY fechaHoraRegistro desc");
        String hql = "FROM Gasto WHERE cast(fechaHoraRegistro as date) BETWEEN :fechaInicio AND :fechaFin ORDER BY fechaHoraRegistro desc";
        Query q = session.createQuery(hql);
        System.out.println("consulta gastos: "+hql);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        lista = q.list();
        
        for (Gasto item : lista) {
            System.out.println("concepto: "+item.getConcepto());
        }
        
        return lista;
               
    }
    
}

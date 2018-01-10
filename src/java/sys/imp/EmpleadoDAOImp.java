
package sys.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sys.dao.EmpleadoDAO;
import sys.model.Empleado;
import sys.util.HibernateUtil;

public class EmpleadoDAOImp implements EmpleadoDAO{

    @Override
    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Empleado WHERE Estado = 'VIGENTE' ORDER BY nombresEmpleado ASC, apellidoPaterno ASC, apellidoMaterno ASC";
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
    public void nuevoEmpleado(Empleado empleado) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(empleado);
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
    public void actualizarEmpleado(Empleado empleado) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(empleado);
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
    public void eliminarEmpleado(Empleado empleado) {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(empleado);
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
    public boolean guardarEmpleado(Session session, Empleado empleado) throws Exception {
        session.save(empleado);
        return true;
    }

    @Override
    public Empleado buscarEmpleado(Session session, Integer idEmpleado) throws Exception {
        String hql = "FROM Empleado WHERE idEmpleado = :idEmpleado";
        Query q = session.createQuery(hql);
        q.setParameter("idEmpleado", idEmpleado);
        
        return (Empleado) q.uniqueResult();
    }

    
}

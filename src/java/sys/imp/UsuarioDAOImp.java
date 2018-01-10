package sys.imp;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import sys.dao.UsuarioDAO;
import sys.model.Usuario;
import sys.util.HibernateUtil;

public class UsuarioDAOImp implements UsuarioDAO {

    @Override
    public Usuario obtenerDatosPorUsuario(Usuario usuario, Integer idSucursal) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Usuario WHERE loginUsuario = :loginUsuario AND empleado.idEmpleado in (SELECT empleado.idEmpleado FROM Ingreso WHERE sucursal.idSucursal = :idSucursal)";
        Query q = session.createQuery(hql);
        q.setParameter("loginUsuario", usuario.getLoginUsuario());
        q.setParameter("idSucursal", idSucursal);
        return (Usuario) q.uniqueResult();
    }

    @Override
    public Usuario login(Usuario usuario, Integer idSucursal) {
        Usuario user = this.obtenerDatosPorUsuario(usuario, idSucursal);
        if (user != null) {
            //***
            //desencriptar
             StandardPBEStringEncryptor d = new StandardPBEStringEncryptor(); 
             d.setPassword("uniquekey"); 
             String devuelve = ""; 
             try 
             {    
             devuelve = d.decrypt(user.getPassword()); 
             }    
             catch (Exception e) 
             {
             System.err.println(e);
             } 
            //encriptar
            
            /*StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
            s.setPassword("uniquekey");
            String passwd = s.encrypt(usuario.getPassword());*/
            
            //****
            if (!devuelve.equals(usuario.getPassword())) {
                user = null;

            }
        }
        return user;
    }

    @Override
    public boolean guardarUsuario(Session session, Usuario usuario) throws Exception {
        session.save(usuario);
        return true;
    }

    @Override
    public Usuario buscarUsuario(Session session, Integer idEmpleado) throws Exception {
        
        String hql = "FROM Usuario WHERE idEmpleado = :idEmpleado";
        Query q = session.createQuery(hql);
        q.setParameter("idEmpleado", idEmpleado);
        
        return (Usuario) q.uniqueResult();
        
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM Usuario WHERE empleado.estado = 'VIGENTE' ORDER BY empleado.nombresEmpleado ASC, empleado.apellidoPaterno ASC, empleado.apellidoMaterno ASC";
        try
        {
            lista = session.createQuery(hql).list();
            for (Usuario l : lista) {
                Hibernate.initialize(l.getEmpleado());
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
    public boolean actualizarUsuario(Session session, Usuario usuario) throws Exception {
        session.update(usuario);
        return true;
    }

    @Override
    public Usuario buscarUsuarioPorId(Session session, Integer idUsuario) throws Exception {
        String hql = "FROM Usuario WHERE idUsuario = :idUsuario";
        Query q = session.createQuery(hql);
        q.setParameter("idUsuario", idUsuario);
        
        return (Usuario) q.uniqueResult();
    }

}

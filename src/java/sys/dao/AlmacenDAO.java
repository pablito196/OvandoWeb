package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Almacen;


public interface AlmacenDAO {
    public List<Almacen> listarAlmacen();
    public boolean nuevoAlmacen(Session session, Almacen almacen);
    public boolean actualizarAlmacen(Session session, Almacen almacen);
    public void eliminarAlmacen(Almacen almacen);
    public Almacen buscarAlmacenPrincipal(Session session, Integer idSucursal);
    public List<Almacen> listarAlmacenPorSucursal(Session session, Integer idSucursal);
    public Almacen buscarAlmacen(Session session, Integer idAlmacen);
}

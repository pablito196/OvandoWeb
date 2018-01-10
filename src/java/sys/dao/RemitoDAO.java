package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Remito;

public interface RemitoDAO {
    public List<Remito> listarRemito();
    public void actualizarRemito(Remito remito); 
    public boolean guardarRemito(Session session, Remito remito);
}

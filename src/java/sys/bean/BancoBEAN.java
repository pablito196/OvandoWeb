
package sys.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import sys.dao.BancoDAO;
import sys.imp.BancoDAOImp;
import sys.model.Banco;

@ManagedBean
@ViewScoped
public class BancoBEAN {

    private Banco banco;
    private List<SelectItem> lstBancos;
    private List<Banco> listaBancos;
    
    public BancoBEAN() {
        this.banco = new Banco();
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<SelectItem> getLstBancos() throws Exception {
        this.lstBancos = new ArrayList<SelectItem>();
        listar();
        lstBancos.clear();
        for (Banco bancos :listaBancos) 
        {
            SelectItem bancoItem = new SelectItem(bancos.getIdBanco(), bancos.getNombreBanco());
            this.lstBancos.add(bancoItem);
        }
        return lstBancos;
    }

    public void setLstBancos(List<SelectItem> lstBancos) {
        this.lstBancos = lstBancos;
    }

    public List<Banco> getListaBancos() {
        BancoDAO bDao =  new BancoDAOImp();
        listaBancos = bDao.listarBancos();
        return listaBancos;
    }

    public void setListaBancos(List<Banco> listaBancos) {
        this.listaBancos = listaBancos;
    }
    
    public void listar() throws Exception
    {
        BancoDAO bDao;
        try
        {
            bDao = new BancoDAOImp();
            listaBancos = bDao.listarBancos();
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}

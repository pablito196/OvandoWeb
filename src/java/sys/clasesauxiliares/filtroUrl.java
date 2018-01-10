
package sys.clasesauxiliares;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class filtroUrl  implements PhaseListener{

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        //capturamos el nombre d ela pagina actual
        String currentPage = facesContext.getViewRoot().getViewId();
        //creamos una variable booleana para comprobar que es la pagina de login la que se capturo
        boolean isPageLogin = currentPage.lastIndexOf("login.xhtml") > -1 ? true : false;
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        //recuperamos un object del string que se guardo para ello se toma de la sesion el usuario que se definio en el loginBean
        Object usuario = session.getAttribute("usuario");
        if(!isPageLogin && usuario == null) //si no es la pagina de logueo y el usuario es nulo, lo redirigimos a la pagina login.xhtml
        {
            NavigationHandler nHandler = facesContext.getApplication().getNavigationHandler();
            nHandler.handleNavigation(facesContext, null, "/login.xhtml");
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
}

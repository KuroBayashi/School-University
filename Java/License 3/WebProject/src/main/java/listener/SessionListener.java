package listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import service.ServiceContainer;


public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("serviceContainer", new ServiceContainer());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().setAttribute("serviceContainer", new ServiceContainer());
    }
    
}

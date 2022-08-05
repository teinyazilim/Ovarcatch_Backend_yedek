package com.tein.overcatchbackend.config;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

@Component
public class SessionEventListener extends HttpSessionEventPublisher {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
       // super.sessionCreated(event);
        //event.getSession().setMaxInactiveInterval(60*3);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String name=null;
        SessionRegistry sessionRegistry = getSessionRegistry(event);
        SessionInformation sessionInfo = (sessionRegistry != null ? sessionRegistry
            .getSessionInformation(event.getSession().getId()) : null);
        UserDetails ud = null;
        if (sessionInfo != null) {
            ud = (UserDetails) sessionInfo.getPrincipal();
        }
        if (ud != null) {
            name=ud.getUsername();
            System.out.println(name);
            //OUR IMPORTANT ACTIONS IN CASE OF SESSION CLOSING
           // getAllGames(event).removeByName(name);
            //for (SessionInformation information : sessionRegistry.getAllSessions(ud, true)) {
               // information.expireNow();
            //}


        }
        super.sessionDestroyed(event);
    }

   /*  public YourBean4Service getMyService(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ApplicationContext ctx =
            WebApplicationContextUtils.
                getWebApplicationContext(session.getServletContext());
        return (YourBean4Service) ctx.getBean("yourBean4Service");
    }

  public AllGames getAllGames(HttpSessionEvent event){
        HttpSession session = event.getSession();
        ApplicationContext ctx =
            WebApplicationContextUtils.
                getWebApplicationContext(session.getServletContext());
        return (AllGames) ctx.getBean("allGames");
    }*/

    public SessionRegistry getSessionRegistry(HttpSessionEvent event){
        HttpSession session = event.getSession();
        ApplicationContext ctx =
            WebApplicationContextUtils.
                getWebApplicationContext(session.getServletContext());
        return (SessionRegistry) ctx.getBean("sessionRegistry");
    }
}

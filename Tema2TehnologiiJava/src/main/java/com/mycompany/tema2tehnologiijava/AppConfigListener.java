package com.mycompany;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppConfigListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Obține contextul aplicației
        var context = sce.getServletContext();

        // Citește parametrii de inițializare
        String prelude = context.getInitParameter("prelude");
        String coda = context.getInitParameter("coda");

        // Stochează valorile în contextul aplicației
        context.setAttribute("prelude", prelude);
        context.setAttribute("coda", coda);
        
        System.out.println("Listener initialized: ");
        System.out.println("Prelude: " + prelude);
        System.out.println("Coda: " + coda);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Curățiri, dacă sunt necesare
    }
}

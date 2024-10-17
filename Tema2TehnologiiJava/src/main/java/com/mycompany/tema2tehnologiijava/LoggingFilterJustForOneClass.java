package com.mycompany;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@WebFilter(urlPatterns = {"/result.jsp"})
public class LoggingFilterJustForOneClass implements Filter {

    private String logFilePath = "C:/Users/Dan/Documents/NetBeansProjects/Tema2TehnologiiJava/src/main/webapp/request_log_oneclass.txt"; // Calea fișierului de log

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inițializări, dacă sunt necesare
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("LoggingFilter activated");
        String ipAddress = req.getRemoteAddr();
        System.out.println("LoggingFilter activated for IP: " + req.getRemoteAddr());
        String logEntry = "IP: " + ipAddress + ", Time: " + new Date().toString() + "\n";

        // Scrierea informației în fișier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace(); // Poate fi înregistrat în jurnalul de erori
        }

        // Continuă cu filtrarea cererii
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        // Curățiri, dacă sunt necesare
    }
}

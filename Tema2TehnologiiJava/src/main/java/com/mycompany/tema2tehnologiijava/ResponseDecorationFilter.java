package com.mycompany;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebFilter(urlPatterns = {"/*"}) // Aplicați filtrul pentru toate cererile
public class ResponseDecorationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inițializări, dacă sunt necesare
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        // Obține contextul pentru a citi prelude și coda
        var context = req.getServletContext();
        String prelude = (String) context.getAttribute("prelude");
        String coda = (String) context.getAttribute("coda");

        // Wrapping the response
        HttpServletResponse response = (HttpServletResponse) res;
        CharResponseWrapper responseWrapper = new CharResponseWrapper(response);

        // Continuă cu filtrarea cererii
        chain.doFilter(req, responseWrapper);

        // Obține răspunsul generat
        String originalResponse = responseWrapper.toString();

        // Scrie prelude și coda în răspuns
        PrintWriter out = response.getWriter();
        out.write(prelude);
        out.write(originalResponse);
        out.write(coda);
        out.flush();
    }

    @Override
    public void destroy() {
        // Curățiri, dacă sunt necesare
    }
}

// Custom response wrapper for capturing output
class CharResponseWrapper extends HttpServletResponseWrapper {
    private StringWriter writer = new StringWriter();

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(writer);
    }

    @Override
    public String toString() {
        return writer.toString();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.test;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Dan
 */
@WebServlet(name = "StringToListServlet", urlPatterns = {"/stringtolist"})
public class StringToListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        // Obținerea șirului trimis prin URL (ca parametru)
        String inputString = request.getParameter("input");
        
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<html>");
            out.println("<head><title>String to List</title></head>");
            out.println("<body>");
            out.println("<h1>Ordered List of Characters</h1>");
            
            if (inputString != null && !inputString.isEmpty()) {
                // Lista ordonata
                out.println("<ol>");
                for (char c : inputString.toCharArray()) {
                    out.println("<li>" + c + "</li>");
                }
                out.println("</ol>");
            } else {
                out.println("<p>No string provided!</p>");
            }
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);  
    }
}
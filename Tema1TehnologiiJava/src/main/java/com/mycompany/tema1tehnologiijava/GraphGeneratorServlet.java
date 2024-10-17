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
import java.util.Random;

/**
 *
 * @author Dan
 */
@WebServlet(name = "GraphGeneratorServlet", urlPatterns = {"/generateGraph"})
public class GraphGeneratorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Parameters
        int numVertices = Integer.parseInt(request.getParameter("numVertices"));
        int numEdges = Integer.parseInt(request.getParameter("numEdges"));

        // Log request
        logRequestInfo(request);

        // Generate matrix
        int[][] adjacencyMatrix = generateRandomGraph(numVertices, numEdges);

        
        response.setContentType("text/html;charset=UTF-8");

       
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Adjacency Matrix</title></head>");
            out.println("<body>");
            out.println("<h1>Adjacency Matrix</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th></th>");

            // headers
            for (int i = 0; i < numVertices; i++) {
                out.println("<th>" + i + "</th>");
            }
            out.println("</tr>");

            // rows
            for (int i = 0; i < numVertices; i++) {
                out.println("<tr><th>" + i + "</th>");
                for (int j = 0; j < numVertices; j++) {
                    out.println("<td>" + adjacencyMatrix[i][j] + "</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void logRequestInfo(HttpServletRequest request) {
        String method = request.getMethod();
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String acceptLanguage = request.getHeader("Accept-Language");
        String parameters = request.getParameter("numVertices") + ", " + request.getParameter("numEdges");

        System.out.println("HTTP Method: " + method);
        System.out.println("Client IP: " + clientIP);
        System.out.println("User-Agent: " + userAgent);
        System.out.println("Accept-Language: " + acceptLanguage);
        System.out.println("Parameters: " + parameters);
    }

    private int[][] generateRandomGraph(int numVertices, int numEdges) {
    Random random = new Random();
    
    // Check valid
    int maxEdges = numVertices * (numVertices - 1) / 2;
    if (numEdges < 0 || numEdges > maxEdges) {
        throw new IllegalArgumentException("Invalid number of edges: " + numEdges);
    }

    int[][] matrix = new int[numVertices][numVertices];

    int edgesAdded = 0;
    while (edgesAdded < numEdges) {
        int from = random.nextInt(numVertices);
        int to = random.nextInt(numVertices);
        
        
        if (from != to && matrix[from][to] == 0) {
            matrix[from][to] = 1;
            matrix[to][from] = 1; 
            edgesAdded++;
        }
    }

    return matrix;
    }

}

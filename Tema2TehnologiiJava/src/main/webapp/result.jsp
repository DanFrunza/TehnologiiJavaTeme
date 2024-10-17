<%-- 
    Document   : result
    Created on : Oct 16, 2024, 7:23:17 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, java.util.Collections"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shuffled File Lines</title>
    </head>
    <body>
        <h1>Shuffled Lines from Uploaded File</h1>

        <%
            // Obținem lista de linii din sesiune
            List<String> fileLines = (List<String>) session.getAttribute("fileLines");

            if (fileLines != null && !fileLines.isEmpty()) {
                // Amestecăm liniile
                Collections.shuffle(fileLines);

                // Afișăm liniile amestecate
                out.println("<ul>");
                for (String line : fileLines) {
                    out.println("<li>" + line + "</li>");
                }
                out.println("</ul>");
            } else {
                out.println("<p>No lines to display! Please upload a file first.</p>");
            }
        %>
    </body>
</html>

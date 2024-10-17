package com.mycompany;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifică răspunsul CAPTCHA
        String captchaAnswer = request.getParameter("captchaAnswer"); // Obține răspunsul utilizatorului
        String correctAnswer = "5"; // Răspunsul corect la întrebarea 3 + 2

        if (captchaAnswer == null || !captchaAnswer.equals(correctAnswer)) {
            response.getWriter().write("CAPTCHA verification failed.");
            return; // Oprește procesarea dacă CAPTCHA nu este corect
        }

        // Get the uploaded file part
        Part filePart = request.getPart("file");

        // Read the lines of the file and store them in a list
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Store the lines in the session
        request.getSession().setAttribute("fileLines", lines);

        // Redirect to result.jsp
        response.sendRedirect("result.jsp");
    }
}

package com.example.electricity;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/bill")
public class ElectricityBillServlet extends HttpServlet {
    private static final String VIEW = "/WEB-INF/views/index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String input = request.getParameter("units");
        request.setAttribute("unitsInput", input == null ? "" : input.trim());

        try {
            if (input == null || input.isBlank()) {
                throw new IllegalArgumentException("Enter the number of units used.");
            }

            int units = Integer.parseInt(input.trim());
            if (units < 0) {
                throw new IllegalArgumentException("Units cannot be negative.");
            }

            request.setAttribute("bill", ElectricityBillCalculator.calculate(units));
        } catch (NumberFormatException exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", "Enter a whole number of units.");
        } catch (IllegalArgumentException exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", exception.getMessage());
        }

        request.getRequestDispatcher(VIEW).forward(request, response);
    }
}

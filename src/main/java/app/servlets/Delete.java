package app.servlets;


import app.ModelTMP;
import model.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "delete", urlPatterns = {"/delete"})
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getContextPath());
        System.out.println(request.getParameter("id"));
        long idForDelete = Long.parseLong(request.getParameter("id"));
        boolean isDeleted = Model.getInstance().delete(idForDelete);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(isDeleted);
    }
}
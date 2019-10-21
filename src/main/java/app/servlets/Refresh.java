package app.servlets;


import app.ModelTMP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcg.spring.jdbctemplate.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/refresh"})
public class Refresh extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(Model.getInstance().ReadAllProducts());
        req.setAttribute("table", json);
        dispatcher.forward(req, resp);
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("name");// "name" from jsp
//        String password = req.getParameter("pass");// "pass" from jsp
//        app.Product user = new app.Product(name, password);
//        app.InitInit model = app.InitInit.getInstance();
//        model.add(user);
//
//        req.setAttribute("userName", name);
//        doGet(req, resp);
//    }
}
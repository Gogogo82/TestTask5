package app.servlets;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Update extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        req.setAttribute("table", app.Model.refresh());
        System.out.println("create");
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
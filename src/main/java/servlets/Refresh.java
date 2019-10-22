package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Model;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "refresh", urlPatterns = {"/refresh"})
public class Refresh extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> list = Model.getInstance().readAllProducts();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonG = gson.toJson(list);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonG);
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
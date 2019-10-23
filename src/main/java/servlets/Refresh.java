package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Product;
import model.Model;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> list = Model.getInstance().readAllProducts();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonG = gson.toJson(list);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonG);
        System.out.println("Refresh successful");
    }
}
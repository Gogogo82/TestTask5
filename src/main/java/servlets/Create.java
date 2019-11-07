package servlets;

import model.Model;
import entities.Product;
import util.ProductCreator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "create", urlPatterns = {"/create"})
public class Create extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String valuesString = request.getReader().lines().collect(Collectors.joining());

        System.out.println("Create.doPost values: " + valuesString);

        Product product = ProductCreator.createProductFromJSON(valuesString);

        boolean isUpdated = Model.getInstance().create(product);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(isUpdated);
        System.out.println("Create.doPost: update successful");
    }
}
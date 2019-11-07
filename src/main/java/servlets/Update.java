package servlets;

import com.google.gson.*;
import entities.Product;
import model.Model;
import util.ProductCreator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "update", urlPatterns = {"/update"})
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String valuesString = request.getReader().lines().collect(Collectors.joining());

        System.out.println("Update.doPost.valuesString: " + valuesString);

        //If client requests data of object for update
        if (valuesString.startsWith("askProductData:")) {

            System.out.println("Update.doPost: parsing id...");

            long id = Long.parseLong(valuesString.replace("askProductData:", ""));

            System.out.println("Update.doPost: asking from DB by id: " + id);

            Product productForUpdate = Model.getInstance().readProductByID(id);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonG = gson.toJson(productForUpdate);

            response.setContentType("application/json;charset=windows-1251");
            PrintWriter out = response.getWriter();
            out.print(jsonG);

            //If client sends data to put in database
        } else {
            System.out.println("Update.doPost: constructing product from: " + valuesString);

            Product product = ProductCreator.createProductFromJSON(valuesString);

            boolean isUpdated = Model.getInstance().update(product);

            response.setContentType("application/json;charset=windows-1251");
            PrintWriter out = response.getWriter();
            out.print(isUpdated);
            System.out.println("Update.doPost: update successful");
        }
    }
}
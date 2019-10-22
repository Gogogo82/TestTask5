package servlets;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Model;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "update", urlPatterns = {"/update"})
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String values = request.getReader().lines().collect(Collectors.joining());
        values = values.replaceAll("\\+", " ");
        System.out.println("Update.doPost values: "+ values);

        if (!values.contains("&")) {
            System.out.println("Update.doPost: searching by id");
            long id = Long.parseLong(values);

            Product productForUpdate = Model.getInstance().readProductByID(id);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonG = gson.toJson(productForUpdate);

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonG);
        } else {
            System.out.println("Update.doPost: constructing product");
            Product product = new Product();
            product.setId(Long.parseLong(values.substring(values.indexOf("id") + 3, values.indexOf("name") - 1)));
            product.setName(values.substring(values.indexOf("name") + 5, values.indexOf("description") - 1));
            product.setDescription(values.substring(values.indexOf("description") + 12, values.indexOf("create_date") - 1));
            String create_date = values.substring(values.indexOf("create_date") + 12, values.indexOf("place_storage") - 1);

            System.out.println("Update.doPost: creating date from: " + create_date);
            Calendar calendar = new GregorianCalendar(
                    Integer.parseInt(create_date.substring(6)),
                    Integer.parseInt(create_date.substring(3, 5)) - 1,
                    Integer.parseInt(create_date.substring(0, 2))
            );
            System.out.println("5");
            product.setCreate_date(calendar.getTimeInMillis());
            System.out.println("Update.doPost: date created");

            String place_storage;
            boolean reserved;
            if (values.contains("reserved")) {
                place_storage = values.substring(values.indexOf("place_storage") + 14, values.indexOf("reserved") - 1);
                reserved = true;
            } else {
                place_storage = values.substring(values.indexOf("place_storage") + 14);
                reserved = false;
            }
            System.out.println("4");

            product.setPlace_storage(Long.parseLong(place_storage));
            product.setReserved(reserved);

            System.out.println("Update.doPost: object created: " + product);
            boolean isUpdated = Model.getInstance().update(product);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(isUpdated);
            System.out.println("Update.doPost: update successful");
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
//            ServletException, IOException {
//        System.out.println("doget: " + request.getReader().lines().collect(Collectors.joining()));
//        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet())
//            System.out.println(e.getKey() + " " + e.getValue());
//
//    }
}
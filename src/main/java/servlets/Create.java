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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

@WebServlet(name = "create", urlPatterns = {"/create"})
public class Create extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String values = request.getReader().lines().collect(Collectors.joining());
        values = values.replaceAll("\\+", " ");

            Product product = new Product();
            product.setName(values.substring(values.indexOf("name") + 5, values.indexOf("description") - 1));
            product.setDescription(values.substring(values.indexOf("description") + 12, values.indexOf("create_date") - 1));
            String create_date = values.substring(values.indexOf("create_date") + 12, values.indexOf("place_storage") - 1);

            Calendar calendar = new GregorianCalendar(
                    Integer.parseInt(create_date.substring(6)),
                    Integer.parseInt(create_date.substring(3, 5)) - 1,
                    Integer.parseInt(create_date.substring(0, 2))
            );
            product.setCreate_date(calendar.getTimeInMillis());

            String place_storage;
            boolean reserved;
            if (values.contains("reserved")) {
                place_storage = values.substring(values.indexOf("place_storage") + 14, values.indexOf("reserved") - 1);
                reserved = true;
            } else {
                place_storage = values.substring(values.indexOf("place_storage") + 14);
                reserved = false;
            }

            product.setPlace_storage(Long.parseLong(place_storage));
            product.setReserved(reserved);

            System.out.println("product to create: " + product);
            boolean isUpdated = Model.getInstance().create(product);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(isUpdated);
        }
    }
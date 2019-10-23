package servlets;


import model.Model;
import entities.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@WebServlet(name = "create", urlPatterns = {"/create"})
public class Create extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("Cp1251");
        String values = new String(request.getReader().lines().collect(Collectors.joining()).getBytes("ISO-8859-1"),"Cp1251");
//        String values = request.getReader().lines().collect(Collectors.joining());
        values = values.replaceAll("\\+", " ");
        System.out.println("Create: doPost values: " + values);

            Product product = new Product();
            product.setName(values.substring(values.indexOf("name") + 5, values.indexOf("description") - 1));
            product.setDescription(values.substring(values.indexOf("description") + 12, values.indexOf("create_date") - 1));
            String create_date = values.substring(values.indexOf("create_date") + 12, values.indexOf("place_storage") - 1);

        LocalDate localDate = LocalDate.parse(create_date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        product.setCreate_date(Date.valueOf(localDate).getTime());

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

            System.out.println("product to createOrUpdate: " + product);
            boolean isUpdated = Model.getInstance().create(product);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(isUpdated);
            System.out.println("Create.doPost: update successful");
        }
    }
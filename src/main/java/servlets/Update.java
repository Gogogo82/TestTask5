package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Product;
import model.Model;

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

@WebServlet(name = "update", urlPatterns = {"/update"})
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("cp1251");
        String valuesString = request.getReader().lines().collect(Collectors.joining());

//        String requestEnc = request.getCharacterEncoding();
//        if (requestEnc == null) requestEnc = "ISO-8859-1";
//        String clientEnc = request.getParameter("charset");
//        if (clientEnc == null) clientEnc = "Cp1251";
//        System.out.println(requestEnc + " - " + clientEnc);
//
//        String valuesEncodedString = new String(valuesString.getBytes(requestEnc), clientEnc);
//        System.out.println("Update.doPost        valuesString: " + valuesString);
//        System.out.println("Update.doPost valuesEncodedString: " + valuesEncodedString);

        valuesString = valuesString.replaceAll("\\+", " ");

        //Если поступил запрос данных обновляемого объекта
        if (!valuesString.contains("&")) {
            System.out.println("Update.doPost: asking from DB by id");
            long id = Long.parseLong(valuesString);

            Product productForUpdate = Model.getInstance().readProductByID(id);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonG = gson.toJson(productForUpdate);

            response.setContentType("application/json;charset=windows-1251");
            PrintWriter out = response.getWriter();
            out.print(jsonG);

            //Если поступили обновлённые данные объекта
        } else {
            System.out.println("Update.doPost: constructing product");
            Product product = new Product();
            product.setId(Long.parseLong(valuesString.substring(valuesString.indexOf("id") + 3, valuesString.indexOf("name") - 1)));
            product.setName(valuesString.substring(valuesString.indexOf("name") + 5, valuesString.indexOf("description") - 1));
            product.setDescription(valuesString.substring(valuesString.indexOf("description") + 12, valuesString.indexOf("create_date") - 1));
            String create_date = valuesString.substring(valuesString.indexOf("create_date") + 12, valuesString.indexOf("place_storage") - 1);

            LocalDate localDate = LocalDate.parse(create_date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            product.setCreate_date(Date.valueOf(localDate).getTime());

            String place_storage;
            boolean reserved;
            if (valuesString.contains("reserved")) {
                place_storage = valuesString.substring(valuesString.indexOf("place_storage") + 14, valuesString.indexOf("reserved") - 1);
                reserved = true;
            } else {
                place_storage = valuesString.substring(valuesString.indexOf("place_storage") + 14);
                reserved = false;
            }

            product.setPlace_storage(Long.parseLong(place_storage));
            product.setReserved(reserved);

            System.out.println("Update.doPost: object created: " + product);
            boolean isUpdated = Model.getInstance().update(product);
            response.setContentType("application/json;charset=windows-1251");
            PrintWriter out = response.getWriter();
            out.print(isUpdated);
            System.out.println("Update.doPost: update successful");
        }
    }
}
package servlets;

import com.google.gson.*;
import entities.Product;
import model.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

            //normalizing JSON format
            valuesString = valuesString.replaceFirst("\\[", "");
            valuesString = valuesString.replaceAll("\"name\":", "");
            valuesString = valuesString.replaceAll("\"value\":", "");
            StringBuilder stringBuilder = new StringBuilder(valuesString);
            stringBuilder.replace(valuesString.lastIndexOf("]"), valuesString.lastIndexOf("]") + 1, "");
            valuesString = stringBuilder.toString().replaceAll("\",\"", "\":\"");
            valuesString = valuesString.replaceAll("},\\{", ",");
            System.out.println("Update.doPost.valuesString JSONed: " + valuesString);

            //GSON deserializer
            class ProductDeserializer implements JsonDeserializer<Product> {
                @Override
                public Product deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    Product result = new Product();

                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    result.setId(jsonObject.get("id").getAsLong());
                    result.setName(jsonObject.get("name").getAsString());
                    result.setDescription(jsonObject.get("description").getAsString());

                    String create_dateTMP = jsonObject.get("create_date").getAsString();
                    LocalDate localDate = LocalDate.parse(create_dateTMP, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    result.setCreate_date(Date.valueOf(localDate).getTime());

                    result.setPlace_storage(jsonObject.get("place_storage").getAsLong());

                    if (jsonObject.has("reserved"))
                        result.setReserved(jsonObject.get("reserved").getAsBoolean());

                    return result;
                }
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Product.class, new ProductDeserializer())
                    .create();

            Product product = gson.fromJson(valuesString, Product.class);

//            Product product = new Product();
//            product.setId(Long.parseLong(valuesString.substring(valuesString.indexOf("id") + 3, valuesString.indexOf("name") - 1)));
//            product.setName(valuesString.substring(valuesString.indexOf("name") + 5, valuesString.indexOf("description") - 1));
//            product.setDescription(valuesString.substring(valuesString.indexOf("description") + 12, valuesString.indexOf("create_date") - 1));
//            String create_date = valuesString.substring(valuesString.indexOf("create_date") + 12, valuesString.indexOf("place_storage") - 1);
//
//            LocalDate localDate = LocalDate.parse(create_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            product.setCreate_date(Date.valueOf(localDate).getTime());
//
//            String place_storage;
//            boolean reserved;
//            if (valuesString.contains("reserved")) {
//                place_storage = valuesString.substring(valuesString.indexOf("place_storage") + 14, valuesString.indexOf("reserved") - 1);
//                reserved = true;
//            } else {
//                place_storage = valuesString.substring(valuesString.indexOf("place_storage") + 14);
//                reserved = false;
//            }
//
//            product.setPlace_storage(Long.parseLong(place_storage));
//            product.setReserved(reserved);

            System.out.println("Update.doPost: created object from JSON: " + product);

            boolean isUpdated = Model.getInstance().update(product);

            response.setContentType("application/json;charset=windows-1251");
            PrintWriter out = response.getWriter();
            out.print(isUpdated);
            System.out.println("Update.doPost: update successful");
        }
    }
}
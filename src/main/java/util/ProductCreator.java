package util;

import com.google.gson.*;
import entities.Product;

import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductCreator {

    public static Product createProductFromJSON(String valuesString) {
        Product result;

        //GSON deserializer
        class ProductDeserializer implements JsonDeserializer<Product> {
            @Override
            public Product deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                Product result = new Product();

                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (!jsonObject.get("id").getAsString().equals(""))
                    result.setId(jsonObject.get("id").getAsLong());

                result.setName(jsonObject.get("name").getAsString());
                result.setDescription(jsonObject.get("description").getAsString());

                String create_dateTMP = jsonObject.get("create_date").getAsString();
                LocalDate localDate = LocalDate.parse(create_dateTMP, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                result.setCreate_date(Date.valueOf(localDate).getTime());

                result.setPlace_storage(jsonObject.get("place_storage").getAsLong());

                if (jsonObject.has("reserved"))
                    result.setReserved(true);

                return result;
            }
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Product.class, new ProductDeserializer())
                .create();

        result = gson.fromJson(valuesString, Product.class);

        System.out.println("ProductCreator: created object from JSON: " + result);

        return result;
    }

}

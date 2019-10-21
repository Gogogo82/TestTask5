package app.servlets;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcg.spring.jdbctemplate.Model;
import com.jcg.spring.jdbctemplate.Product;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Refresh extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");

        System.out.println("refresh");
        List<Product> list = Model.getInstance().ReadAllProducts();
        for (Product p: list)
            System.out.println(p);

        System.out.println("1");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("2");
        String jsonG = gson.toJson(new ArrayList<>());
//        String jsonG = gson.toJson(new Product(2l, "f", "f", new Date(), 5l, true));
        System.out.println("json: " + jsonG);


        ObjectMapper mapper = new ObjectMapper();
//        String jsonJ = mapper.writeValueAsString(new Product(2l, "f", "f", new Date(), 5l, true));
        String jsonJ = mapper.writeValueAsString("test");
        System.out.println("jsonJ: " + jsonJ);



        req.setAttribute("table", jsonG);
        dispatcher.forward(req, resp);
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
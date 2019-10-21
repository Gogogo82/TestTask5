package app.servlets;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Model;
import model.Product;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "refresh", urlPatterns = {"/refresh"})
public class Refresh extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        List<Product> list = Model.getInstance().ReadAllProducts();
//        for (Product p: list)
//            System.out.println(p);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonG = gson.toJson(list);
//        System.out.println("json: " + jsonG);


//        ObjectMapper mapper = new ObjectMapper();
//        String jsonJ = mapper.writeValueAsString(new Product(2l, "f", "f", new Date(), 5l, true));
//        String jsonJ = mapper.writeValueAsString("test");
//        System.out.println("jsonJ: " + jsonJ);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonG);


//        request.setAttribute("table", jsonG);
//        dispatcher.forward(request, response);
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
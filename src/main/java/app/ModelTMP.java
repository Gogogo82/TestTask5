package app;

import java.sql.*;

public class ModelTMP {

    public static String refresh() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String db = "jdbc:hsqldb:C:/hsqldb-2.5.0/hsqldb/data/test5db;ifexists=true;shutdown=true";
        String user = "user";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(db, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products;")) {

            while (rs.next()) {
                stringBuilder.append("<tr><td>");
                stringBuilder.append(rs.getInt("id"));
                stringBuilder.append("</td><td>");
                stringBuilder.append(rs.getString("name"));
                stringBuilder.append("</td><td>");
                stringBuilder.append(rs.getString("description"));
                stringBuilder.append("</td><td>");
                stringBuilder.append(rs.getDate("create_date").toString());
                stringBuilder.append("</td><td>");
                stringBuilder.append(rs.getInt("place_storage"));
                stringBuilder.append("</td><td>");
                stringBuilder.append(rs.getBoolean("reserved"));
                stringBuilder.append("</td><td>");
                stringBuilder.append("<button id=\"");
                stringBuilder.append(rs.getInt("id"));
                stringBuilder.append("\" onclick=\"return Update(this);\">Редактировать</button> <button id=\"");
                stringBuilder.append(rs.getInt("id"));
                stringBuilder.append("\" onclick=\"return Delete(this);\">Удалить</button></td></tr>");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
//        System.out.println("stringBuilder: " + stringBuilder);
        return stringBuilder.toString();
    }

}
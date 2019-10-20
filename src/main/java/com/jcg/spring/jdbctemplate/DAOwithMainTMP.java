package com.jcg.spring.jdbctemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DAOwithMainTMP {

    static JdbcTemplate jdbcTemplateObj;
    static SimpleDriverDataSource dataSourceObj;

    static String DB_USERNAME = "user";
    static String DB_PASSWORD = "password";
    static String DB_URL = "jdbc:hsqldb:C:/hsqldb-2.5.0/hsqldb/data/test5db;ifexists=true;shutdown=true";

    public static SimpleDriverDataSource getDatabaseConnection() {
        dataSourceObj = new SimpleDriverDataSource();

//            try {
//                Class.forName("org.hsqldb.jdbc.JDBCDriver" );
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }

        dataSourceObj.setDriver(DriverManager.getDrivers().nextElement());
        dataSourceObj.setUrl(DB_URL);
        dataSourceObj.setUsername(DB_USERNAME);
        dataSourceObj.setPassword(DB_PASSWORD);
        return dataSourceObj;
    }

    public static void main(String[] args) throws SQLException {

        jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());

        // SQL Operation #1 - SQL INSERT Operation
        String sqlInsertQuery = "INSERT INTO products (name, description, create_date, place_storage, reserved) VALUES (?, ?, ?, ?, ?)";
//        jdbcTemplateObj.update(sqlInsertQuery, "Editor ", "@javacodegeek.com", "2019-12-17", "5", "true");

            // SQL Operation #2 - SQL UPDATE Operation
            String sqlUpdateQuery = "UPDATE products set place_storage=2 where name = 'SomeShit'";
//            jdbcTemplateObj.update(sqlUpdateQuery);

//            // SQL Operation #3 - SQL READ Operation
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        String sqlSelectQuery = "SELECT * FROM products";
        List<Product> listContacts = jdbcTemplateObj.query(sqlSelectQuery, new RowMapper() {
            public Product mapRow(ResultSet result, int rowNum) throws SQLException {
                Product product = null;
                try {
                    product = new Product(
                            Long.parseLong(result.getString("id")),
                            result.getString("name"),
                            result.getString("description"),
                            format.parse(result.getString("create_date")),
                            Long.parseLong(result.getString("place_storage")),
                            Boolean.parseBoolean(result.getString("description"))
                    );
                } catch (ParseException e) {
                    throw new SQLException(e.getMessage());
                }
                return product;
            }
        });

            for (Product contactDetail : listContacts) {
                System.out.println(contactDetail.toString());
            }

        // SQL Operation #4 - SQL DELETE Operation
            String sqlDeleteQuery = "DELETE FROM products where name=?";
//            jdbcTemplateObj.update(sqlDeleteQuery, "Editor");
    }
}
//    The Jdbc Template methods throw runtime DataAccessException, so here is an example if developers want to catch this exception explicitly:
//        try {
//        String sqlDeleteQuery = "DELETE FROM contact where name=?";
//        jdbcTemplateObj.update(sqlDeleteQuery, "Editor 104");
//        } catch (DataAccessException exObj) {
//        exObj.printStackTrace();
//        }
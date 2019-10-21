package model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class Model {
    private static Model instance = new Model();

    private static JdbcTemplate jdbcTemplateObj;
    private static SimpleDriverDataSource dataSourceObj;

    private static String DB_USERNAME = "user";
    private static String DB_PASSWORD = "password";
    private static String DB_URL = "jdbc:hsqldb:C:/hsqldb-2.5.0/hsqldb/data/test5db;ifexists=true;shutdown=true";

    private Model() {
    }

    public static Model getInstance() {
        return instance;
    }

    private static SimpleDriverDataSource getDatabaseConnection() {
        dataSourceObj = new SimpleDriverDataSource();

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        dataSourceObj.setDriver(DriverManager.getDrivers().nextElement());
        dataSourceObj.setUrl(DB_URL);
        dataSourceObj.setUsername(DB_USERNAME);
        dataSourceObj.setPassword(DB_PASSWORD);
        return dataSourceObj;
    }

    public boolean Create(Product product) {
        boolean result = true;
        try {
            jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
            String sqlInsertQuery = "INSERT INTO products (name, description, create_date, place_storage, reserved) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplateObj.update(sqlInsertQuery, product.getName(), product.getDescription(), product.getCreate_dateAsString(), product.getPlace_storage(), product.isReserved());
        } catch (Exception e) {
            System.err.println("Creation failed");
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public List<Product> ReadAllProducts() {
        List<Product> result = null;
        try {
            jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String sqlSelectQuery = "SELECT * FROM products";

            result = jdbcTemplateObj.query(sqlSelectQuery, new RowMapper() {
                public Product mapRow(ResultSet resultRow, int rowNum) throws SQLException {
                    Product product;
                    try {
                        product = new Product(
                                Long.parseLong(resultRow.getString("id")),
                                resultRow.getString("name"),
                                resultRow.getString("description"),
                                format.parse(resultRow.getString("create_date")).getTime(),
                                Long.parseLong(resultRow.getString("place_storage")),
                                Boolean.parseBoolean(resultRow.getString("description"))

                        );
                    } catch (ParseException e) {
                        throw new SQLException(e.getMessage());
                    }
//                    System.out.println("product: " + product);
                    return product;
                }
            });

        } catch (Exception e) {
            System.err.println("Read failed");
            e.printStackTrace();
        }
        return result;
    }

    public Product ReadProductByID (long id) {
        Product result = null;
        try {
            jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
            String sqlUpdateQuery = "SELECT * FROM products WHERE id=?";
            jdbcTemplateObj.update(sqlUpdateQuery, id);
        } catch (Exception e) {
            System.err.println("User read failed");
            e.printStackTrace();
        }
        return result;
    }

    public boolean Update(Product product) {
        boolean result = true;
        try {
            jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
            String sqlUpdateQuery = "UPDATE products set name=?, description=?, create_date=?, place_storage=?, reserved=? WHERE id=?";
            jdbcTemplateObj.update(sqlUpdateQuery, product.getName(), product.getDescription(), product.getCreate_dateAsString(), product.getPlace_storage(), product.isReserved(), product.getId());
        } catch (Exception e) {
            System.err.println("Update failed");
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean delete(long id) {
        boolean result = true;
        try {
            jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
            String sqlDeleteQuery = "DELETE FROM products where id=?";
            jdbcTemplateObj.update(sqlDeleteQuery, id);
        } catch (Exception e) {
            System.err.println("Update failed");
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
//    The Jdbc Template methods throw runtime DataAccessException, so here is an example if developers want to catch this exception explicitly:
//        try {
//        String sqlDeleteQuery = "DELETE FROM contact where name=?";
//        jdbcTemplateObj.update(sqlDeleteQuery, "Editor 104");
//        } catch (DataAccessException exObj) {
//        exObj.printStackTrace();
//        }
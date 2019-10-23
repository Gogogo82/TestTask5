package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class Model {
    private static Model instance = new Model();

    private static JdbcTemplate jdbcTemplateObj;
    private static SimpleDriverDataSource dataSourceObj;

//    private static String DB_USERNAME = "user";
//    private static String DB_PASSWORD = "password";
//    private static String DB_URL = "jdbc:hsqldb:C:/hsqldb-2.5.0/hsqldb/data/test5db;ifexists=true;shutdown=true";

    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    private static String DB_URL;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private Model() {
    }

    public static Model getInstance() {
        return instance;
    }

    private static SimpleDriverDataSource getDatabaseConnection() {

        dataSourceObj = new SimpleDriverDataSource();

        try {
            String projectRoot = Model.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            projectRoot = projectRoot.substring(0, projectRoot.lastIndexOf("TestTask5_war_exploded") + "TestTask5_war_exploded".length() + 1);
            projectRoot = projectRoot.replaceAll("%20", " ");
            String pathToParams = projectRoot + "DBparams.txt";
            if (pathToParams.matches("/[a-zA-Z]:.*"))
                pathToParams = pathToParams.replaceFirst("/", "");

            String[] params = Files.lines(Paths.get(pathToParams)).toArray(String[]::new);
            DB_USERNAME = params[0];
            DB_PASSWORD = params[1];
            DB_URL = "jdbc:hsqldb:" + params[2].replaceFirst("\\.script", "") + ";ifexists=true;shutdown=true";

            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        dataSourceObj.setDriver(DriverManager.getDrivers().nextElement());
        dataSourceObj.setUrl(DB_URL);
        dataSourceObj.setUsername(DB_USERNAME);
        dataSourceObj.setPassword(DB_PASSWORD);

        return dataSourceObj;
    }

    public boolean create(Product product) {
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

    public List<Product> readAllProducts() {
        List<Product> result = null;
        try {
            jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
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
                                Boolean.parseBoolean(resultRow.getString("reserved"))
                        );
//                        System.out.println("refresh from model: " + product);
                    } catch (ParseException e) {
                        throw new SQLException(e.getMessage());
                    }
                    return product;
                }
            });

        } catch (Exception e) {
            System.err.println("Read failed");
            e.printStackTrace();
        }
        return result;
    }

    public Product readProductByID(long id) {
        Product result = null;
        try {
            jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
            String sqlUpdateQuery = "SELECT * FROM products WHERE id=" + id;

            List<Product> list = jdbcTemplateObj.query(sqlUpdateQuery, new RowMapper() {
                public Product mapRow(ResultSet resultRow, int rowNum) throws SQLException {
                    Product product;
                    try {
                        product = new Product(
                                Long.parseLong(resultRow.getString("id")),
                                resultRow.getString("name"),
                                resultRow.getString("description"),
                                format.parse(resultRow.getString("create_date")).getTime(),
                                Long.parseLong(resultRow.getString("place_storage")),
                                Boolean.parseBoolean(resultRow.getString("reserved"))
                        );
                    } catch (ParseException e) {
                        throw new SQLException(e.getMessage());
                    }
                    return product;
                }
            });

            result = list.get(0);

        } catch (Exception e) {
            System.err.println("User read failed");
            e.printStackTrace();
        }
        return result;
    }

    public boolean update(Product product) {
        boolean result = true;
        try {
            jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
            String sqlUpdateQuery = "UPDATE products set name=?, description=?, create_date=?, place_storage=?, reserved=? WHERE id=?";
            System.out.println("tosql: " + product.getCreate_dateAsString());
            jdbcTemplateObj.update(sqlUpdateQuery, product.getName(), product.getDescription(), product.getCreate_dateAsString(), product.getPlace_storage(), product.isReserved(), product.getId());
        } catch (Exception e) {
            System.err.println("update failed");
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
            System.err.println("update failed");
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
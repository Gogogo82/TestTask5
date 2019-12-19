package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class Model {
    private final static Model instance = new Model();

    private static JdbcTemplate jdbcTemplateObj;
    private static DataSource dataSource = setupDataSource();
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private Model() {
    }

    public static Model getInstance() {
        return instance;
    }

    private static DataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        try {
                Properties properties = new Properties();
                properties.load(Model.class.getClassLoader().getResourceAsStream("SqlConnection.properties"));

            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
            ds.setUrl("jdbc:hsqldb:" +
                    properties.getProperty("pathToDbFile").replaceFirst("\\.script", "") +
                    ";ifexists=true;shutdown=true");
            ds.setUsername(properties.getProperty("userName"));
            ds.setPassword(properties.getProperty("password"));

//            //loading database settings from file
//            String projectRoot = Model.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//            projectRoot = projectRoot.substring(0, projectRoot.lastIndexOf("TestTask5_war_exploded") + "TestTask5_war_exploded".length() + 1);
//            projectRoot = projectRoot.replaceAll("%20", " ");
//            String pathToParams = projectRoot + "DBparams.txt";
//
//            if (pathToParams.matches("/[a-zA-Z]:.*"))
//                pathToParams = pathToParams.replaceFirst("/", "");
//
//            String[] params = Files.lines(Paths.get(pathToParams)).toArray(String[]::new);
//
//            ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
//            ds.setUrl("jdbc:hsqldb:" + params[2].replaceFirst("\\.script", "") + ";ifexists=true;shutdown=true");
//            ds.setUsername(params[0]);
//            ds.setPassword(params[1]);

            System.out.println("setupDataSource completed");
        } catch (Exception e) {
            System.out.println("setupDataSource failed");
            e.printStackTrace();
        }
        return ds;
    }

    public boolean create(Product product) {
        boolean result = true;
        try {
            jdbcTemplateObj = new JdbcTemplate(dataSource);
            String sqlInsertQuery = "INSERT INTO products (name, description, create_date, place_storage, reserved) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplateObj.update(sqlInsertQuery, product.getName(), product.getDescription(), product.getCreate_dateAsString(), product.getPlace_storage(), product.isReserved());
        } catch (Exception e) {
            System.out.println("Model.create failed");
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public List<Product> readAllProducts() {
        List<Product> result = null;
        try {
            jdbcTemplateObj = new JdbcTemplate(dataSource);
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
                    } catch (ParseException e) {
                        throw new SQLException(e.getMessage());
                    }
                    return product;
                }
            });

        } catch (Exception e) {
            System.out.println("Model.read failed");
            e.printStackTrace();
        }
        return result;
    }

    public Product readProductByID(long id) {
        Product result = null;
        try {
            jdbcTemplateObj = new JdbcTemplate(dataSource);
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
            System.out.println("Model.User read failed");
            e.printStackTrace();
        }
        return result;
    }

    public boolean update(Product product) {
        boolean result = true;
        try {
            jdbcTemplateObj = new JdbcTemplate(dataSource);
            String sqlUpdateQuery = "UPDATE products set name=?, description=?, create_date=?, place_storage=?, reserved=? WHERE id=?";
            jdbcTemplateObj.update(sqlUpdateQuery, product.getName(), product.getDescription(), product.getCreate_dateAsString(), product.getPlace_storage(), product.isReserved(), product.getId());
        } catch (Exception e) {
            System.out.println("Model.update failed");
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean delete(long id) {
        boolean result = true;
        try {
            jdbcTemplateObj = new JdbcTemplate(dataSource);
            String sqlDeleteQuery = "DELETE FROM products where id=?";
            jdbcTemplateObj.update(sqlDeleteQuery, id);
        } catch (Exception e) {
            System.out.println("Model.delete failed");
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
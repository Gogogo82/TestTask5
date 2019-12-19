package util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.logging.Logger;

@WebListener
public class JdbcDriverUnregisteringListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(JdbcDriverUnregisteringListener.class.getName());

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();

            try {
                DriverManager.deregisterDriver(driver);
                logger.info("Deregistering JDBC driver: {" + driver + "}");
            } catch (Exception e) {
                logger.warning("Error deregistering JDBC driver: {" + driver + "}. Root cause: " + e);
            }
        }
    }
}


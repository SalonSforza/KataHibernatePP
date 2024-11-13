package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory factory;

    private static final String USER_NAME = "hibernate.connection.username";
    private static final String PASSWORD = "hibernate.connection.password";
    private static final String URL = "hibernate.connection.url";

    public static SessionFactory getFactory() {

        if (factory == null) {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            prop.setProperty("hibernate.connection.url", PropertiesUtil.get(URL));
            prop.setProperty("hibernate.connection.username", PropertiesUtil.get(USER_NAME));
            prop.setProperty("hibernate.connection.password", PropertiesUtil.get(PASSWORD));
            prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            prop.setProperty("hibernate.show_sql", "true");
            prop.setProperty("hibernate.current_session_context_class", "thread");

            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(prop).build();

            factory = cfg.buildSessionFactory(serviceRegistry);
        }
        return factory;

    }

    public static Session getSession() {
        return getFactory().openSession();
    }
}

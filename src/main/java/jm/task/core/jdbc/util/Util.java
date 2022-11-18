package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String DB_URL = "jdbc:mysql://localhost:3306/user_test_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Jnpmzqgv1!";

    Driver driver;
    Connection connection;
    Statement statement;

    SessionFactory factory = new Configuration().configure()
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    {
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

        } catch (SQLException e) {
            System.err.println("Не удалось загрузить драйвер БД");
            throw new RuntimeException(e);
        }

        try {

            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            statement = connection.createStatement();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public SessionFactory getFactory() {
        return factory;
    }
}

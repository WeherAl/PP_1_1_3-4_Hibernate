package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД

    public static SessionFactory getFactory() {

        SessionFactory factory = new Configuration().configure()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        return factory;
    }
}

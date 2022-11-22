package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age int NOT NULL)";

            Query query = session.createSQLQuery(sql).addEntity(User.class);

            query.executeUpdate();

            session.getTransaction().commit();
        }

    }

    @Override
    public void dropUsersTable() {

        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS users";

            Query query = session.createSQLQuery(sql).addEntity(User.class);

            query.executeUpdate();

            session.getTransaction().commit();
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getFactory().getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            session.getTransaction().commit();
        }

    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("Пользователь с id " + id + " успешно удален");

            } else {
                System.out.println("Пользователя с id: " + id + " не существует");
            }
            session.getTransaction().commit();
        }
    }


    @Override
    public List<User> getAllUsers() {

        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            List<User> userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();

            for (Object user : userList) {
                System.out.println(user);
            }

            return userList;

        }

    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            System.out.println("Таблица пользователей успешно очищена");
            session.getTransaction().commit();
        }
    }
}

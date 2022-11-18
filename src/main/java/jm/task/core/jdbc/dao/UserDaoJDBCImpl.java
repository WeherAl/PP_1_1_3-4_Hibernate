package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String query="CREATE TABLE IF NOT EXISTS USERS(" +
                "ID int NOT NULL AUTO_INCREMENT," +
                "NAME varchar(50)," +
                "LASTNAME varchar(10)," +
                "AGE int(10)," +
                "PRIMARY KEY(ID))";

        System.out.println("Таблица создана");


        Statement statement = null;
        try {
            statement = util.getConnection().createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {
        String query=" DROP TABLE IF EXISTS USERS";

        Statement statement = null;
        try {
            statement = util.getConnection().createStatement();
            statement.executeUpdate(query);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (name, lastname, age) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = util.getConnection().prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void removeUserById(long userId) {
        String query = "delete from users where id =" + userId;
        try {
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate(query);
            System.out.println("Пользователь с id " + userId + " успешно удален");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<User> getAllUsers() {
        String query = "select * from users";
        List<User> usersList = new ArrayList<>();
        try {
            Statement statement = util.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge((byte) resultSet.getInt(4));
                usersList.add(user);
            }
            return usersList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cleanUsersTable() {
        String query = "delete from users";
        try {
            Statement statement = util.getConnection().createStatement();
            statement.executeUpdate(query);
            System.out.println("Таблица пользователей успешно очищена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

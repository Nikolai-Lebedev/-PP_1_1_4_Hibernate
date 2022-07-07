package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getDbConnection;

public class UserDaoJDBCImpl implements UserDao {
    static int coutnOfUser;

    static Connection connection;

    static {
        try {
            connection = getDbConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL, lastName VARCHAR(30) NOT NULL, age TINYINT NOT NULL, PRIMARY KEY (id))";
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users(name, lastName,  age) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString (1, name);
            statement.setString (2, lastName);
            statement.setByte (3, age);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName (resultSet.getString("name"));
                user.setLastName (resultSet.getString("lastName"));
                user.setAge (resultSet.getByte ("age"));

                allUsers.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE Users";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

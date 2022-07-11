package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getDbConnection;

public class UserDaoHibernateImpl implements UserDao {
    static SessionFactory sessionFactory = Util.getSessionFactory();
    Session session;


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL, lastName VARCHAR(30) NOT NULL, age TINYINT NOT NULL, PRIMARY KEY (id))";
//        String sql = "CREATE TABLE IF NOT EXISTS Users (id BIGINT NOT NULL, name VARCHAR(30) NOT NULL, lastName VARCHAR(30) NOT NULL, age TINYINT NOT NULL)";
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();


    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        try (session = sessionFactory.getCurrentSession();) {
            return session.createQuery("from Student", User.class).list();
        }


    }

    @Override
    public void cleanUsersTable() {

    }
}

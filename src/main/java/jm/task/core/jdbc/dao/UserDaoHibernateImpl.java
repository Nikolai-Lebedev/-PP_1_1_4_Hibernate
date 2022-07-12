package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import static jm.task.core.jdbc.util.Util.getDbConnection;
public class UserDaoHibernateImpl implements UserDao {
    static SessionFactory sessionFactory = Util.getSessionFactory();
    Session session;
    Transaction transaction;
    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL, lastName VARCHAR(30) NOT NULL, age TINYINT NOT NULL, PRIMARY KEY (id))";
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            session.close();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User us = session.get(User.class, id);
            session.delete(us);
            transaction.commit();
            System.out.println("User с id – " + id + " удален из базы данных");
            session.close();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        Criteria criteria;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            usersList = session.createQuery("SELECT i FROM User i", User.class).getResultList();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return usersList;
    }
    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE Users";
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}

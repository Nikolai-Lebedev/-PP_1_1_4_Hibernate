package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {

        UserService userDao = new UserServiceImpl();

        userDao.createUsersTable();

        userDao.saveUser("Nikolai", "Lebebev", (byte) 33);
        userDao.saveUser("Aleksei", "Ivanov", (byte) 25);
        userDao.saveUser("Ivan", "Smirnov", (byte) 28);
        userDao.saveUser("Oleg", "Smirnov", (byte) 40);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();



    }
}

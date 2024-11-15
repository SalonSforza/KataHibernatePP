package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private static final String CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
            id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            userName TEXT NOT NULL,
            lastName TEXT NOT NULL,
            age TINYINT NOT NULL);
            """;

    private static final String DROP_USERS_TABLE = """
            DROP TABLE IF EXISTS users;
            """;

    private static final String CLEAN_USERS = """
            TRUNCATE TABLE users;
            """;

    @Override
    public void createUsersTable() {
        executeQueryViaHibernate(CREATE_USERS_TABLE);
    }

    @Override
    public void cleanUsersTable() {
        executeQueryViaHibernate(CLEAN_USERS);
    }

    @Override
    public void dropUsersTable() {
        executeQueryViaHibernate(DROP_USERS_TABLE);
    }

    private static void executeQueryViaHibernate(String sql) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            try {
                session.createSQLQuery(sql).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                try {
                    session.getTransaction().rollback();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = HibernateUtil.getSession()) {
            try {
                session.beginTransaction();
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
            } catch (Exception e) {
                try {
                    session.getTransaction().rollback();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = HibernateUtil.getSession()) {
            try {
                session.beginTransaction();
                session.remove(session.get(User.class, id));
                session.getTransaction().commit();
            } catch (Exception e) {
                try {
                    session.getTransaction().rollback();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = List.of();
        try (Session session = HibernateUtil.getSession()) {
            try {
                session.beginTransaction();
                users = session.createQuery("from User").list();
                session.getTransaction().commit();
            } catch (Exception e) {
                try {
                    session.getTransaction().rollback();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return users;
    }

}




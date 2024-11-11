package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
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
        Configuration config = new Configuration();
        SessionFactory sessionFactory = config.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Configuration config = new Configuration().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        }
        return users;
    }


}

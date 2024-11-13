package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Nikita", "Nikitin", (byte) 32);
        userService.saveUser("Marinka", "Nikitina", (byte) 28);
        userService.saveUser("Zaur", "Tregulov", (byte) 35);
        userService.saveUser("Denis", "Matveenko", (byte) 31);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}

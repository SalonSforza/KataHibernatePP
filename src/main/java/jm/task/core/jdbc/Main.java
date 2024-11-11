package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.saveUser(" ", " ", (byte) 2);
        userService.saveUser("Test", "Test", (byte) 3);
        userService.removeUserById(6);
        userService.getAllUsers().stream().forEach(System.out::println);
        // поигрался в мейне просто для себя потестить
    }
}

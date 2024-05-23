package service;

import dao.UserDAO;
import model.User;

public class AuthenticationService {
    private UserDAO userDAO = new UserDAO();

    public User authenticate(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            System.out.println("User found: " + user.getUsername()); // Debugging output
            if (UserService.checkPassword(password, user.getPassword())) {
                return user;
            } else {
                System.out.println("Password mismatch"); // Debugging output
            }
        } else {
            System.out.println("No user found with username: " + username); // Debugging output
        }
        return null;
    }
}

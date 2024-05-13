package ui;

import model.User;
import service.AuthenticationService;
import session.UserSession;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");

    public LoginForm() {
        super("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);

        loginButton.addActionListener(e -> performLogin());
        registerButton.addActionListener(e -> openRegistrationForm());

        pack();
        setLocationRelativeTo(null);
    }

    private void openRegistrationForm() {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        AuthenticationService authService = new AuthenticationService();
        User user = authService.authenticate(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            UserSession.logIn(user.isVisitor());  // Update session based on user role
            dispose(); // Close the login window
            MainApplicationFrame mainFrame = new MainApplicationFrame(user.isVisitor());
            mainFrame.setVisible(true); // Open the main application window
        } else {
            JOptionPane.showMessageDialog(this, "Login failed!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}

package ui;

import model.User;
import service.UserService;
import session.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

        loginButton.addActionListener(this::performLogin);
        registerButton.addActionListener(this::openRegistrationForm);

        pack();
        setLocationRelativeTo(null);
    }

    private void openRegistrationForm(ActionEvent event) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setVisible(true);
    }

    private void performLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = UserService.authenticateUser(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            UserSession.logIn(user.isVisitor());
            dispose();
            MainApplicationFrame mainFrame = new MainApplicationFrame(user.isVisitor());
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}

package ui;

import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistrationForm extends JFrame {
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"model.Visitor", "Admin", "Staff"});
    private JTextField emailField = new JTextField(10);
    private JButton registerButton = new JButton("Register");

    public RegistrationForm() {
        super("Register");
        setLayout(new GridLayout(5, 2));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Role:"));
        add(roleComboBox);
        add(new JLabel("Email:"));
        add(emailField);
        add(registerButton);

        registerButton.addActionListener(this::registerUser);
        pack();
        setLocationRelativeTo(null);
    }

    private void registerUser(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        String email = emailField.getText();

        boolean isVisitor = "visitor".equals(role);
        UserService.registerUser(username, password, role, email, isVisitor);

        JOptionPane.showMessageDialog(this, "Registration Successful!");
        dispose();
    }
}

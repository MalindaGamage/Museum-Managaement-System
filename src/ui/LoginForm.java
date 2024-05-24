package ui;

import model.User;
import service.UserService;
import session.UserSession;
import com.formdev.flatlaf.FlatLightLaf;

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

        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Museum Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        mainPanel.add(registerButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

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

package ui;

import dao.VisitorDAO;
import session.UserSession;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class VisitorLogin extends JFrame {
    private JTextField emailField = new JTextField(20);
    private JTextField idField = new JTextField(20);
    private JButton loginButton = new JButton("Login");

    public VisitorLogin() {
        super("Visitor Login");
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Visitor ID:"));
        add(idField);

        loginButton.addActionListener(e -> authenticateVisitor());
        add(loginButton);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void authenticateVisitor() {
        String email = emailField.getText().trim();
        String visitorId = idField.getText().trim();
        VisitorDAO visitorDAO = new VisitorDAO();
        try {
            UUID uuid = UUID.fromString(visitorId);
            if (visitorDAO.validateVisitor(email, uuid)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                UserSession.logIn(true);  // Log in as visitor
                dispose(); // Close the login window
                EventQueue.invokeLater(() -> {
                    MainApplicationFrame mainFrame = new MainApplicationFrame(true);
                    mainFrame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Login failed!");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid Visitor ID format.");
        }
    }
}
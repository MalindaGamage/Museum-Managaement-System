package ui;

import dao.VisitorDAO;
import session.UserSession;

import javax.swing.*;
import java.awt.*;

public class VisitorLogin extends JFrame {
    private JTextField emailField = new JTextField(20);
    private JTextField idField = new JTextField(20);
    private JButton loginButton = new JButton("Login");

    public VisitorLogin() {
        super("model.Visitor Login");
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("model.Visitor ID:"));
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
        if (visitorDAO.validateVisitor(email, Integer.parseInt(visitorId))) {
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
    }
}

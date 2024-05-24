package ui;

import model.User;
import service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserDialog extends JDialog {
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "staff", "visitor"});
    private JTextField emailField = new JTextField(10);
    private JCheckBox isVisitorCheckbox = new JCheckBox("Visitor");
    private JButton saveButton = new JButton("Save");
    private boolean isEdit;
    private DefaultTableModel model;
    private int row;

    public UserDialog(Frame owner, String title, boolean modal, User user, boolean isEdit, DefaultTableModel model, int row) {
        super(owner, title, modal);
        this.isEdit = isEdit;
        this.model = model;
        this.row = row;

        // Set the Look and Feel to FlatLaf
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setLayout(new GridLayout(6, 2, 10, 10));
        setTitle("User Management");
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Add components
        add(new JLabel("Username:", new ImageIcon("icons/username.png"), SwingConstants.RIGHT));
        add(usernameField);
        add(new JLabel("Password:", new ImageIcon("icons/password.png"), SwingConstants.RIGHT));
        add(passwordField);
        add(new JLabel("Role:", new ImageIcon("icons/role.png"), SwingConstants.RIGHT));
        add(roleComboBox);
        add(new JLabel("Email:", new ImageIcon("icons/email.png"), SwingConstants.RIGHT));
        add(emailField);
        add(new JLabel("Visitor:", new ImageIcon("icons/visitor.png"), SwingConstants.RIGHT));
        add(isVisitorCheckbox);

        if (user != null) {
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            roleComboBox.setSelectedItem(user.getRole());
            emailField.setText(user.getEmail());
            isVisitorCheckbox.setSelected(user.isVisitor());
        }

        saveButton.addActionListener(e -> saveUser());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        add(buttonPanel);

        pack();
        setLocationRelativeTo(owner);
    }

    private void saveUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword()); // Get the password as a string
        String role = (String) roleComboBox.getSelectedItem();
        String email = emailField.getText();
        boolean isVisitor = isVisitorCheckbox.isSelected();

        if (username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isEdit) {
            // Update existing user
            UserService.updateUser(username, password, role, email, isVisitor); // Ensure password is hashed
            model.setValueAt(username, row, 0);
            model.setValueAt(role, row, 1);
            model.setValueAt(email, row, 2);
        } else {
            // Add new user
            UserService.registerUser(username, password, role, email, isVisitor); // Ensure password is hashed
            model.addRow(new Object[]{username, role, email});
        }

        setVisible(false);
        dispose();
    }
}
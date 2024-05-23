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

        setLayout(new GridLayout(6, 2));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Role:"));
        add(roleComboBox);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Visitor:"));
        add(isVisitorCheckbox);

        if (user != null) {
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            roleComboBox.setSelectedItem(user.getRole());
            emailField.setText(user.getEmail());
            isVisitorCheckbox.setSelected(user.isVisitor());
        }

        saveButton.addActionListener(e -> saveUser());
        add(saveButton);

        pack();
        setLocationRelativeTo(owner);
    }

    private void saveUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword()); // Get the password as a string
        String role = (String) roleComboBox.getSelectedItem();
        String email = emailField.getText();
        boolean isVisitor = isVisitorCheckbox.isSelected();

        if (isEdit) {
            // Update existing user
            UserService.registerUser(username, password, role, email, isVisitor); // Ensure password is hashed
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

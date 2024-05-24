package ui;

import model.User;
import service.UserService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserDialog extends JDialog {
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"admin", "staff", "visitor"});
    private JTextField emailField = new JTextField(15);
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

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Visitor:"), gbc);
        gbc.gridx = 1;
        add(isVisitorCheckbox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        saveButton.setBackground(new Color(0, 120, 215));
        saveButton.setForeground(Color.WHITE);
        add(saveButton, gbc);

        if (user != null) {
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            roleComboBox.setSelectedItem(user.getRole());
            emailField.setText(user.getEmail());
            isVisitorCheckbox.setSelected(user.isVisitor());
        }

        saveButton.addActionListener(e -> saveUser());
        pack();
        setLocationRelativeTo(owner);
        setModal(true);
    }

    private void saveUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        String email = emailField.getText();
        boolean isVisitor = isVisitorCheckbox.isSelected();

        if (isEdit) {
            UserService.registerUser(username, password, role, email, isVisitor);
            model.setValueAt(username, row, 0);
            model.setValueAt(role, row, 1);
            model.setValueAt(email, row, 2);
        } else {
            UserService.registerUser(username, password, role, email, isVisitor);
            model.addRow(new Object[]{username, role, email});
        }

        setVisible(false);
        dispose();
    }
}

package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserDialog extends JDialog {
    private JTable userTable;
    private JTextField usernameField = new JTextField(10);
    private JTextField passwordField = new JTextField(10);
    private JTextField roleField = new JTextField(10);
    private JTextField emailField = new JTextField(10);
    private JCheckBox isVisitorCheckbox = new JCheckBox("model.Visitor");
    private JButton saveButton = new JButton("Save");
    private boolean isEdit;
    private UserDAO userDAO;
    private DefaultTableModel model;
    private int row;

    public UserDialog(Frame owner, String title, boolean modal, User user, boolean isEdit, DefaultTableModel model, int row) {
        super(owner, title, modal);
        this.isEdit = isEdit;
        this.model = model;
        this.row = row;
        this.userDAO = new UserDAO(); // Initialize DAO within the dialog for operations

        setLayout(new GridLayout(5, 2));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Role:"));
        add(roleField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("model.Visitor:"));
        add(isVisitorCheckbox);

        if (user != null) {
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            roleField.setText(user.getRole());
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
        String password = passwordField.getText(); // Consider hashing before saving
        String role = roleField.getText();
        String email = emailField.getText();
        boolean isVisitor = isVisitorCheckbox.isSelected();

        if (isEdit) {
            userDAO.updateUser(new User(0, username, password, role, email, isVisitor)); // Implement updateUser in dao.UserDAO
            model.setValueAt(username, row, 0);
            model.setValueAt(role, row, 1);
            model.setValueAt(email, row, 2);
        } else {
            userDAO.addUser(new User(0, username, password, role, email, isVisitor));
            model.addRow(new Object[]{username, role, email});
        }

        setVisible(false);
        dispose();
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) model.getValueAt(selectedRow, 0);
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                UserDialog dialog = new UserDialog(JFrame.getFrames()[0], "Edit model.User", true, user, true, model, selectedRow);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "model.User not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.");
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) userTable.getValueAt(selectedRow, 0); // Assuming username is in column 0
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                UserDAO userDAO = new UserDAO();
                userDAO.deleteUser(username);
                ((DefaultTableModel)userTable.getModel()).removeRow(selectedRow); // Assuming a DefaultTableModel is used
            }
        } else {
            JOptionPane.showMessageDialog(this, "No user selected for deletion.");
        }
    }

}

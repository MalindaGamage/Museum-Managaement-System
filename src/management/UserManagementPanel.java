package management;

import dao.UserDAO;
import model.User;
import ui.UserDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserManagementPanel extends JPanel {
    private JTable userTable;
    private DefaultTableModel model;

    public UserManagementPanel() {
        setLayout(new BorderLayout());
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        model = new DefaultTableModel(new Object[]{"Username", "Role", "Email"}, 0);
        userTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(e -> addUser());
        editButton.addActionListener(e -> editUser());
        deleteButton.addActionListener(e -> deleteUser());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0); // Clear the table
        UserDAO userDAO = new UserDAO();
        java.util.List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            model.addRow(new Object[]{user.getUsername(), user.getRole(), user.getEmail()});
        }
    }

    private void addUser() {
        UserDialog dialog = new UserDialog(JFrame.getFrames()[0], "Add User", true, null, false, model, -1);
        dialog.setVisible(true);
        loadData(); // Refresh data
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) model.getValueAt(selectedRow, 0);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                UserDialog dialog = new UserDialog(JFrame.getFrames()[0], "Edit User", true, user, true, model, selectedRow);
                dialog.setVisible(true);
                loadData(); // Refresh data
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.");
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) model.getValueAt(selectedRow, 0);
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                UserDAO userDAO = new UserDAO();
                userDAO.deleteUser(username);
                model.removeRow(selectedRow); // Remove from table
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }
}

package management;

import dao.VisitorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VisitorManagementPanel extends JPanel {
    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private VisitorDAO visitorDAO;

    public VisitorManagementPanel() {
        visitorDAO = new VisitorDAO();
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        table = new JTable(); // Set up with a proper table model
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> addVisitor());
        editButton.addActionListener(e -> editVisitor());
        deleteButton.addActionListener(e -> deleteVisitor());

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addVisitor() {
        // Code to add a visitor
    }

    private void editVisitor() {
        // Code to edit a visitor
    }

    private void deleteVisitor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int visitorId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
            visitorDAO.deleteVisitor(visitorId);
            ((DefaultTableModel)table.getModel()).removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select a visitor to delete");
        }
    }
}

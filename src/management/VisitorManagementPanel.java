package management;

import dao.VisitorDAO;
import model.Visitor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VisitorManagementPanel extends JPanel {
    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private VisitorDAO visitorDAO;
    private DefaultTableModel tableModel;

    public VisitorManagementPanel() {
        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        visitorDAO = new VisitorDAO();
        setLayout(new BorderLayout());
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Full Name", "Email", "Phone", "Visit Date", "Group Size"}, 0);
        table = new JTable(tableModel);
        customizeTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        addButton.addActionListener(e -> addVisitor());
        editButton.addActionListener(e -> editVisitor());
        deleteButton.addActionListener(e -> deleteVisitor());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add a title at the top
        JLabel titleLabel = new JLabel("Visitor Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);
    }

    private void loadData() {
        tableModel.setRowCount(0); // Clear the table
        List<Visitor> visitors = visitorDAO.getAllVisitors();
        for (Visitor visitor : visitors) {
            tableModel.addRow(new Object[]{
                    visitor.getVisitorId(),
                    visitor.getFullName(),
                    visitor.getEmail(),
                    visitor.getPhone(),
                    visitor.getVisitDate(),
                    visitor.getGroupSize()
            });
        }
    }

    private void addVisitor() {
        JTextField fullNameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField visitDateField = new JTextField(20);
        JTextField groupSizeField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Visit Date (yyyy-mm-dd):"));
        panel.add(visitDateField);
        panel.add(new JLabel("Group Size:"));
        panel.add(groupSizeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Visitor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String visitDateStr = visitDateField.getText();
            int groupSize = Integer.parseInt(groupSizeField.getText());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date visitDate;
            try {
                visitDate = dateFormat.parse(visitDateStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-mm-dd.");
                return;
            }

            Visitor visitor = new Visitor(UUID.randomUUID(), fullName, email, phone, visitDate, groupSize);
            visitorDAO.addVisitor(visitor);
            loadData(); // Refresh data
        }
    }

    private void editVisitor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            UUID visitorId = UUID.fromString(tableModel.getValueAt(selectedRow, 0).toString());
            Visitor visitor = visitorDAO.getVisitorById(visitorId);
            if (visitor != null) {
                JTextField fullNameField = new JTextField(visitor.getFullName(), 20);
                JTextField emailField = new JTextField(visitor.getEmail(), 20);
                JTextField phoneField = new JTextField(visitor.getPhone(), 20);
                JTextField visitDateField = new JTextField(visitor.getVisitDate().toString(), 20);
                JTextField groupSizeField = new JTextField(String.valueOf(visitor.getGroupSize()), 20);

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Full Name:"));
                panel.add(fullNameField);
                panel.add(new JLabel("Email:"));
                panel.add(emailField);
                panel.add(new JLabel("Phone:"));
                panel.add(phoneField);
                panel.add(new JLabel("Visit Date (yyyy-mm-dd):"));
                panel.add(visitDateField);
                panel.add(new JLabel("Group Size:"));
                panel.add(groupSizeField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Visitor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String fullName = fullNameField.getText();
                    String email = emailField.getText();
                    String phone = phoneField.getText();
                    String visitDateStr = visitDateField.getText();
                    int groupSize = Integer.parseInt(groupSizeField.getText());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date visitDate;
                    try {
                        visitDate = dateFormat.parse(visitDateStr);
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-mm-dd.");
                        return;
                    }

                    visitor.setFullName(fullName);
                    visitor.setEmail(email);
                    visitor.setPhone(phone);
                    visitor.setVisitDate(visitDate);
                    visitor.setGroupSize(groupSize);
                    visitorDAO.updateVisitor(visitor);
                    loadData(); // Refresh data
                }
            } else {
                JOptionPane.showMessageDialog(this, "Visitor not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a visitor to edit.");
        }
    }

    private void deleteVisitor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            UUID visitorId = UUID.fromString(tableModel.getValueAt(selectedRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this visitor?", "Delete Visitor", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                visitorDAO.deleteVisitor(visitorId);
                loadData(); // Refresh data
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a visitor to delete.");
        }
    }

    private void customizeTable() {
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
    }
}

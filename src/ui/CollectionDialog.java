package ui;

import dao.CollectionDAO;
import model.Collection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CollectionDialog extends JDialog {
    private JTextField nameField = new JTextField(15);
    private JTextField descriptionField = new JTextField(15);
    private JComboBox<String> categoryField = new JComboBox<>(new String[]{"Art", "Historical", "Science", "Nature"});
    private JTextField acquisitionDateField = new JTextField(15);
    private JComboBox<String> statusField = new JComboBox<>(new String[]{"on_display", "in_storage", "on_loan"});
    private JTextField imageUrlField = new JTextField(15);
    private JButton saveButton = new JButton("Save");
    private boolean isEdit;
    private CollectionDAO collectionDAO;
    private DefaultTableModel model;
    private int row;

    public CollectionDialog(Frame owner, String title, boolean modal, Collection collection, boolean isEdit, DefaultTableModel model, int row) {
        super(owner, title, modal);
        this.isEdit = isEdit;
        this.model = model;
        this.row = row;
        this.collectionDAO = new CollectionDAO();

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addTitle(mainPanel, gbc);
        addFormFields(mainPanel, gbc, collection);

        saveButton.addActionListener(this::saveCollection);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        mainPanel.add(saveButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(owner);

        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void addTitle(JPanel panel, GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("Collection Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
    }

    private void addFormFields(JPanel panel, GridBagConstraints gbc, Collection collection) {
        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        panel.add(descriptionField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        panel.add(categoryField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Acquisition Date (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        panel.add(acquisitionDateField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        panel.add(statusField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Image URL:"), gbc);
        gbc.gridx = 1;
        panel.add(imageUrlField, gbc);

        if (collection != null) {
            nameField.setText(collection.getName());
            descriptionField.setText(collection.getDescription());
            categoryField.setSelectedItem(collection.getCategory());
            acquisitionDateField.setText(collection.getAcquisitionDate().toString());
            statusField.setSelectedItem(collection.getStatus());
            imageUrlField.setText(collection.getImageUrl());
        }
    }

    private void saveCollection(ActionEvent event) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String category = (String) categoryField.getSelectedItem();
        String acquisitionDateStr = acquisitionDateField.getText();
        String status = (String) statusField.getSelectedItem();
        String imageUrl = imageUrlField.getText();

        // Validate name
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        // Validate category
        if (category == null || category.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category cannot be empty.");
            return;
        }

        // Validate dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date acquisitionDate;
        try {
            acquisitionDate = dateFormat.parse(acquisitionDateStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        // Validate description
        if (description == null || description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description cannot be empty.");
            return;
        }

        if (isEdit) {
            Collection collection = new Collection(model.getValueAt(row, 0).toString(), name, description, category, acquisitionDate, status, imageUrl);
            collectionDAO.updateCollection(collection);
            model.setValueAt(name, row, 1);
            model.setValueAt(description, row, 2);
            model.setValueAt(category, row, 3);
            model.setValueAt(acquisitionDate, row, 4);
            model.setValueAt(status, row, 5);
            model.setValueAt(imageUrl, row, 6);
        } else {
            String collectionId = UUID.randomUUID().toString();
            Collection collection = new Collection(collectionId, name, description, category, acquisitionDate, status, imageUrl);
            collectionDAO.addCollection(collection);
            model.addRow(new Object[]{collectionId, name, description, category, acquisitionDate, status, imageUrl});
        }

        setVisible(false);
        dispose();
    }
}

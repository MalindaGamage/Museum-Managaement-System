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
    private JTextField nameField = new JTextField(10);
    private JTextField descriptionField = new JTextField(10);
    private JTextField categoryField = new JTextField(10);
    private JTextField acquisitionDateField = new JTextField(10);
    private JTextField statusField = new JTextField(10);
    private JTextField imageUrlField = new JTextField(10);
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

        setLayout(new GridLayout(7, 2));
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("Category:"));
        add(categoryField);
        add(new JLabel("Acquisition Date:"));
        add(acquisitionDateField);
        add(new JLabel("Status:"));
        add(statusField);
        add(new JLabel("Image URL:"));
        add(imageUrlField);

        if (collection != null) {
            nameField.setText(collection.getName());
            descriptionField.setText(collection.getDescription());
            categoryField.setText(collection.getCategory());
            acquisitionDateField.setText(collection.getAcquisitionDate().toString());
            statusField.setText(collection.getStatus());
            imageUrlField.setText(collection.getImageUrl());
        }

        saveButton.addActionListener(this::saveCollection);
        add(saveButton);

        pack();
        setLocationRelativeTo(owner);
    }

    private void saveCollection(ActionEvent event) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String category = categoryField.getText();
        String acquisitionDateStr = acquisitionDateField.getText();
        String status = statusField.getText();
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
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-mm-dd.");
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

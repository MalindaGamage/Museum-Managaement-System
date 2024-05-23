package ui;

import dao.CollectionDAO;
import model.Collection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

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
    private int collectionId;

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
            collectionId = collection.getCollectionId();
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
        Date acquisitionDate = java.sql.Date.valueOf(acquisitionDateField.getText());
        String status = truncateStatus(statusField.getText());
        String imageUrl = imageUrlField.getText();

        if (isEdit) {
            Collection collection = new Collection(collectionId, name, description, category, acquisitionDate, status, imageUrl);
            collectionDAO.updateCollection(collection);
            model.setValueAt(name, row, 0);
            model.setValueAt(description, row, 1);
            model.setValueAt(category, row, 2);
            model.setValueAt(acquisitionDate, row, 3);
            model.setValueAt(status, row, 4);
            model.setValueAt(imageUrl, row, 5);
        } else {
            Collection collection = new Collection(0, name, description, category, acquisitionDate, status, imageUrl);
            collectionDAO.addCollection(collection);
            model.addRow(new Object[]{name, description, category, acquisitionDate, status, imageUrl});
        }

        setVisible(false);
        dispose();
    }

    private String truncateStatus(String status) {
        if (status.length() > 10) {
            return status.substring(0, 10);
        }
        return status;
    }
}
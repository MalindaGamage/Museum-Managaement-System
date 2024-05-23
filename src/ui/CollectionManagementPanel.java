package ui;

import dao.CollectionDAO;
import model.Collection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;

public class CollectionManagementPanel extends JPanel {
    private JTable collectionTable;
    private DefaultTableModel model;
    private CollectionDAO collectionDAO;

    public CollectionManagementPanel() {
        setLayout(new BorderLayout());

        collectionDAO = new CollectionDAO();
        model = new DefaultTableModel(new String[]{"Name", "Description", "Category", "Acquisition Date", "Status", "Image URL"}, 0);
        collectionTable = new JTable(model);

        loadCollections();

        add(new JScrollPane(collectionTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(this::addCollection);
        editButton.addActionListener(this::editCollection);
        deleteButton.addActionListener(this::deleteCollection);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadCollections() {
        List<Collection> collections = collectionDAO.getAllCollections();
        for (Collection collection : collections) {
            model.addRow(new Object[]{
                    collection.getName(),
                    collection.getDescription(),
                    collection.getCategory(),
                    collection.getAcquisitionDate(),
                    collection.getStatus(),
                    collection.getImageUrl()
            });
        }
    }

    private void addCollection(ActionEvent event) {
        CollectionDialog dialog = new CollectionDialog(null, "Add Collection", true, null, false, model, -1);
        dialog.setVisible(true);
    }

    private void editCollection(ActionEvent event) {
        int selectedRow = collectionTable.getSelectedRow();
        if (selectedRow != -1) {
            String name = (String) model.getValueAt(selectedRow, 0);
            Optional<Collection> optionalCollection = collectionDAO.getAllCollections().stream()
                    .filter(c -> c.getName().equals(name))
                    .findFirst();
            if (optionalCollection.isPresent()) {
                Collection collection = optionalCollection.get();
                CollectionDialog dialog = new CollectionDialog(null, "Edit Collection", true, collection, true, model, selectedRow);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Collection not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a collection to edit.");
        }
    }

    private void deleteCollection(ActionEvent event) {
        int selectedRow = collectionTable.getSelectedRow();
        if (selectedRow != -1) {
            String name = (String) model.getValueAt(selectedRow, 0);
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this collection?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                Optional<Collection> optionalCollection = collectionDAO.getAllCollections().stream()
                        .filter(c -> c.getName().equals(name))
                        .findFirst();
                if (optionalCollection.isPresent()) {
                    Collection collection = optionalCollection.get();
                    collectionDAO.deleteCollection(collection.getCollectionId());
                    model.removeRow(selectedRow);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No collection selected for deletion.");
        }
    }
}
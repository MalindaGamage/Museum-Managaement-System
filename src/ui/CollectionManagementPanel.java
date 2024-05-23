package ui;

import dao.CollectionDAO;
import model.Collection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class CollectionManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private CollectionDAO collectionDAO;

    public CollectionManagementPanel() {
        setLayout(new BorderLayout());
        collectionDAO = new CollectionDAO();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Description", "Category", "Acquisition Date", "Status", "Image URL"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        List<Collection> collections = collectionDAO.getAllCollections();
        for (Collection collection : collections) {
            tableModel.addRow(new Object[]{
                    collection.getCollectionId(),
                    collection.getName(),
                    collection.getDescription(),
                    collection.getCategory(),
                    collection.getAcquisitionDate(),
                    collection.getStatus(),
                    collection.getImageUrl()
            });
        }

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CollectionDialog dialog = new CollectionDialog(null, "Add Collection", true, null, false, tableModel, -1);
                dialog.setVisible(true);
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String collectionId = tableModel.getValueAt(selectedRow, 0).toString();
                    String name = tableModel.getValueAt(selectedRow, 1).toString();
                    String description = tableModel.getValueAt(selectedRow, 2).toString();
                    String category = tableModel.getValueAt(selectedRow, 3).toString();
                    Date acquisitionDate = java.sql.Date.valueOf(tableModel.getValueAt(selectedRow, 4).toString());
                    String status = tableModel.getValueAt(selectedRow, 5).toString();
                    String imageUrl = tableModel.getValueAt(selectedRow, 6).toString();

                    Collection collection = new Collection(collectionId, name, description, category, acquisitionDate, status, imageUrl);
                    CollectionDialog dialog = new CollectionDialog(null, "Edit Collection", true, collection, true, tableModel, selectedRow);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a collection to edit.");
                }
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this collection?", "Delete Collection", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        String collectionId = tableModel.getValueAt(selectedRow, 0).toString();
                        collectionDAO.deleteCollection(collectionId);
                        tableModel.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a collection to delete.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

package ui;

import dao.CollectionDAO;
import model.Collection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CollectionViewerPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private CollectionDAO collectionDAO;

    public CollectionViewerPanel() {
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

        add(scrollPane, BorderLayout.CENTER);
    }
}

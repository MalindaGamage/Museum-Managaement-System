package ui;

import dao.CollectionDAO;
import model.Collection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CollectionViewerPanel extends JPanel {
    private JTable collectionTable;
    private DefaultTableModel model;
    private CollectionDAO collectionDAO;

    public CollectionViewerPanel() {
        setLayout(new BorderLayout());

        collectionDAO = new CollectionDAO();
        model = new DefaultTableModel(new String[]{"Name", "Description", "Category", "Acquisition Date", "Status", "Image URL"}, 0);
        collectionTable = new JTable(model);

        loadCollections();

        add(new JScrollPane(collectionTable), BorderLayout.CENTER);
    }

    private void loadCollections() {
        List<Collection> collections = (List<Collection>) collectionDAO.getAllCollections();
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
}

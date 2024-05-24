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

        // Fetch collections and add to the table model
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

        // Add the scroll pane to the center of the layout
        add(scrollPane, BorderLayout.CENTER);

        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Customize table appearance
        customizeTable();

        // Add a title at the top
        JLabel titleLabel = new JLabel("Collection Viewer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);
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
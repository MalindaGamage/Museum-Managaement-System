package ui;

import dao.ExhibitionDAO;
import model.Exhibition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ExhibitionDialog extends JDialog {
    private JTextField titleField = new JTextField(15);
    private JTextField startDateField = new JTextField(15);
    private JTextField endDateField = new JTextField(15);
    private JTextField descriptionField = new JTextField(15);
    private JCheckBox activeCheckBox = new JCheckBox();
    private JButton saveButton = new JButton("Save");
    private boolean isEdit;
    private ExhibitionDAO exhibitionDAO;
    private DefaultTableModel model;
    private int row;
    private UUID exhibitionId;

    public ExhibitionDialog(Frame owner, String title, boolean modal, Exhibition exhibition, boolean isEdit, DefaultTableModel model, int row) {
        super(owner, title, modal);
        this.isEdit = isEdit;
        this.model = model;
        this.row = row;
        this.exhibitionDAO = new ExhibitionDAO();

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addTitle(mainPanel, gbc);
        addFormFields(mainPanel, gbc, exhibition);

        saveButton.addActionListener(this::saveExhibition);
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
        JLabel titleLabel = new JLabel("Exhibition Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
    }

    private void addFormFields(JPanel panel, GridBagConstraints gbc, Exhibition exhibition) {
        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Start Date (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        panel.add(startDateField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("End Date (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        panel.add(endDateField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        panel.add(descriptionField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Active:"), gbc);
        gbc.gridx = 1;
        panel.add(activeCheckBox, gbc);

        if (exhibition != null) {
            exhibitionId = exhibition.getExhibitionId();
            titleField.setText(exhibition.getTitle());
            startDateField.setText(exhibition.getStartDate().toString());
            endDateField.setText(exhibition.getEndDate().toString());
            descriptionField.setText(exhibition.getDescription());
            activeCheckBox.setSelected(exhibition.isActive());
        }
    }

    private void saveExhibition(ActionEvent event) {
        String title = titleField.getText();
        String startDateStr = startDateField.getText();
        String endDateStr = endDateField.getText();
        String description = descriptionField.getText();
        boolean isActive = activeCheckBox.isSelected();

        // Validate title
        if (title == null || title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty.");
            return;
        }

        // Validate dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;
        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(this, "Start date must be before end date.");
            return;
        }

        // Validate description
        if (description == null || description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description cannot be empty.");
            return;
        }

        if (isEdit) {
            Exhibition exhibition = new Exhibition(exhibitionId, title, startDate, endDate, description, isActive);
            exhibitionDAO.updateExhibition(exhibition);
            model.setValueAt(title, row, 0);
            model.setValueAt(startDate, row, 1);
            model.setValueAt(endDate, row, 2);
            model.setValueAt(description, row, 3);
            model.setValueAt(isActive, row, 4);
        } else {
            Exhibition exhibition = new Exhibition(UUID.randomUUID(), title, startDate, endDate, description, isActive);
            exhibitionDAO.addExhibition(exhibition);
            model.addRow(new Object[]{title, startDate, endDate, description, isActive});
        }

        setVisible(false);
        dispose();
    }
}

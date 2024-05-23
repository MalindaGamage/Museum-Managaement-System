package ui;

import dao.ExhibitionDAO;
import model.Exhibition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class ExhibitionDialog extends JDialog {
    private JTextField titleField = new JTextField(10);
    private JTextField startDateField = new JTextField(10);
    private JTextField endDateField = new JTextField(10);
    private JTextField descriptionField = new JTextField(10);
    private JCheckBox activeCheckBox = new JCheckBox();
    private JButton saveButton = new JButton("Save");
    private boolean isEdit;
    private ExhibitionDAO exhibitionDAO;
    private DefaultTableModel model;
    private int row;
    private int exhibitionId;

    public ExhibitionDialog(Frame owner, String title, boolean modal, Exhibition exhibition, boolean isEdit, DefaultTableModel model, int row) {
        super(owner, title, modal);
        this.isEdit = isEdit;
        this.model = model;
        this.row = row;
        this.exhibitionDAO = new ExhibitionDAO();

        setLayout(new GridLayout(6, 2));
        add(new JLabel("Title:"));
        add(titleField);
        add(new JLabel("Start Date (yyyy-mm-dd):"));
        add(startDateField);
        add(new JLabel("End Date (yyyy-mm-dd):"));
        add(endDateField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("Active:"));
        add(activeCheckBox);

        if (exhibition != null) {
            exhibitionId = exhibition.getExhibitionId();
            titleField.setText(exhibition.getTitle());
            startDateField.setText(exhibition.getStartDate().toString());
            endDateField.setText(exhibition.getEndDate().toString());
            descriptionField.setText(exhibition.getDescription());
            activeCheckBox.setSelected(exhibition.isActive());
        }

        saveButton.addActionListener(this::saveExhibition);
        add(saveButton);

        pack();
        setLocationRelativeTo(owner);
    }

    private void saveExhibition(ActionEvent event) {
        String title = titleField.getText();
        Date startDate = java.sql.Date.valueOf(startDateField.getText());
        Date endDate = java.sql.Date.valueOf(endDateField.getText());
        String description = descriptionField.getText();
        boolean isActive = activeCheckBox.isSelected();

        if (isEdit) {
            Exhibition exhibition = new Exhibition(exhibitionId, title, startDate, endDate, description, isActive);
            exhibitionDAO.updateExhibition(exhibition);
            model.setValueAt(title, row, 0);
            model.setValueAt(startDate, row, 1);
            model.setValueAt(endDate, row, 2);
            model.setValueAt(description, row, 3);
            model.setValueAt(isActive, row, 4);
        } else {
            Exhibition exhibition = new Exhibition(0, title, startDate, endDate, description, isActive);
            exhibitionDAO.addExhibition(exhibition);
            model.addRow(new Object[]{title, startDate, endDate, description, isActive});
        }

        setVisible(false);
        dispose();
    }
}
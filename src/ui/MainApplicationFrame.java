package ui;

import management.CollectionManagementPanel;
import management.ExhibitionManagementPanel;
import management.UserManagementPanel;
import session.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainApplicationFrame extends JFrame {
    private JPanel contentPanel;
    private boolean isVisitor;

    public MainApplicationFrame(boolean isVisitor) {
        super("Museum Management System");
        this.isVisitor = isVisitor;
        initializeUI();
    }

    private void initializeUI() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        contentPanel = new JPanel(new CardLayout());
        add(contentPanel);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        exitItem.addActionListener(e -> System.exit(0));

        JMenuItem logoutItem = new JMenuItem("Logout");
        fileMenu.add(logoutItem);
        logoutItem.addActionListener(this::performLogout);

        if (!isVisitor) {
            setupAdminUI(menuBar);
        } else {
            setupVisitorUI(menuBar);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupAdminUI(JMenuBar menuBar) {
        JMenu manageMenu = new JMenu("Manage");
        menuBar.add(manageMenu);

        JMenuItem usersItem = new JMenuItem("Users");
        manageMenu.add(usersItem);
        usersItem.addActionListener(this::showUserManagement);

        JMenuItem collectionsItem = new JMenuItem("Collections");
        manageMenu.add(collectionsItem);
        collectionsItem.addActionListener(this::showCollectionManagement);

        JMenuItem exhibitionsItem = new JMenuItem("Exhibitions");
        manageMenu.add(exhibitionsItem);
        exhibitionsItem.addActionListener(this::showExhibitionManagement);
    }

    private void setupVisitorUI(JMenuBar menuBar) {
        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        JMenuItem viewCollectionsItem = new JMenuItem("View Collections");
        viewMenu.add(viewCollectionsItem);
        viewCollectionsItem.addActionListener(this::showCollectionViewer);
    }

    private void showUserManagement(ActionEvent event) {
        contentPanel.removeAll();
        UserManagementPanel userManagementPanel = new UserManagementPanel();
        contentPanel.add(userManagementPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCollectionManagement(ActionEvent event) {
        contentPanel.removeAll();
        CollectionManagementPanel collectionManagementPanel = new CollectionManagementPanel();
        contentPanel.add(collectionManagementPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showExhibitionManagement(ActionEvent event) {
        contentPanel.removeAll();
        ExhibitionManagementPanel exhibitionManagementPanel = new ExhibitionManagementPanel();
        contentPanel.add(exhibitionManagementPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCollectionViewer(ActionEvent event) {
        contentPanel.removeAll();
        CollectionViewerPanel collectionViewerPanel = new CollectionViewerPanel();
        contentPanel.add(collectionViewerPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void performLogout(ActionEvent event) {
        UserSession.logOut();
        dispose();
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
}
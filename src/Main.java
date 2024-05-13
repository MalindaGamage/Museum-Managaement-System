import session.UserSession;
import ui.LoginForm;
import ui.MainApplicationFrame;

import javax.swing.*;

public class Main {
    /*public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set system look and feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            boolean isVisitor = loginAsVisitor(); // Implement this method to determine if the user is a visitor
            ui.MainApplicationFrame mainFrame = new ui.MainApplicationFrame(isVisitor);
            mainFrame.setVisible(true);
        });
    }

    private static void createAndShowGUI() {
        Object[] options = {"Staff", "model.Visitor"};
        int choice = JOptionPane.showOptionDialog(null, "Log in as:", "Login",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            showLoginForm(); // Staff login
        } else if (choice == 1) {
            showVisitorLogin(); // model.Visitor login
        } else {
            System.exit(0); // Handle closing the option pane without selection
        }
    }

    private static void showLoginForm() {
        ui.LoginForm loginForm = new ui.LoginForm();
        setupFrame(loginForm);
    }

    private static void showVisitorLogin() {
        ui.VisitorLogin visitorLogin = new ui.VisitorLogin(); // Assuming you create a ui.VisitorLogin similar to ui.LoginForm
        setupFrame(visitorLogin);
    }

    private static void setupFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static boolean loginAsVisitor() {
        // Implement logic to determine if the user is a visitor
        // This could be a dialog asking the user if they are a visitor or staff
        return true; // Placeholder
    }*/
    public static void main(String[] args) {
        // Set the look and feel to the system look and feel for better UI integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set system look and feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(Main::launchInitialInterface);
    }

    private static void launchInitialInterface() {
        // Check if a user is logged in and what type
        if (UserSession.isLoggedIn()) {
            if (UserSession.isVisitor()) {
                MainApplicationFrame mainFrame = new MainApplicationFrame(true);
                mainFrame.setVisible(true);
            } else {
                MainApplicationFrame mainFrame = new MainApplicationFrame(false);
                mainFrame.setVisible(true);
            }
        } else {
            // No user is logged in, decide which login to show
            showLogin();
        }
    }

    private static void showLogin() {
        // This could be your admin or visitor login form, or a selection screen
        LoginForm loginForm = new LoginForm(); // Assuming ui.LoginForm is your admin login form
        loginForm.setVisible(true);
    }
}

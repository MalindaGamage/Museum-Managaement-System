import com.formdev.flatlaf.FlatLightLaf;
import session.UserSession;
import ui.LoginForm;
import ui.MainApplicationFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            if (UserSession.isLoggedIn()) {
                MainApplicationFrame mainFrame = new MainApplicationFrame(UserSession.isVisitor());
                mainFrame.setVisible(true);
            } else {
                new LoginForm().setVisible(true);
            }
        });
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

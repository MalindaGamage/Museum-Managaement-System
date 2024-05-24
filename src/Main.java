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
}

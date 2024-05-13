package session;

public class UserSession {
    private static boolean loggedIn = false;
    private static boolean isVisitor = false;

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void logIn(boolean isVisitor) {
        UserSession.loggedIn = true;
        UserSession.isVisitor = isVisitor;  // Store whether the user is a visitor
    }

    public static void logOut() {
        loggedIn = false;
        isVisitor = false;
    }

    public static boolean isVisitor() {
        return isVisitor;
    }
}

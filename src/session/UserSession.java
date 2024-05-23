package session;

public class UserSession {
    private static boolean isLoggedIn = false;
    private static boolean isVisitor = false;

    public static void logIn(boolean visitor) {
        isLoggedIn = true;
        isVisitor = visitor;
    }

    public static void logOut() {
        isLoggedIn = false;
        isVisitor = false;
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static boolean isVisitor() {
        return isVisitor;
    }
}

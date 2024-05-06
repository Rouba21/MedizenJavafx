package Medizen.Utils;

import Medizen.Models.User;

public class Session {
    private static Session instance;
    private User currentUser;

    private Session() { }

    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public static void ClearSession() {
    }

    public static void start_session(User user) {
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    public User getUser() {
        return currentUser;
    }
}

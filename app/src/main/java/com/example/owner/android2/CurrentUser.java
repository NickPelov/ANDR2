package com.example.owner.android2;

/**
 * Created by k_vol on 12/06/2017.
 */

public class CurrentUser {
    private static User user;
    private static boolean isLogged = false;

    public static void setLogged(boolean logged) {
        isLogged = logged;
    }

    public static boolean getLogged() {
        return isLogged;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user1) {
        user = user1;
    }

}

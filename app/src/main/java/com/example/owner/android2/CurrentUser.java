package com.example.owner.android2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k_vol on 12/06/2017.
 */

public class CurrentUser {
    private static User user;
    private static boolean isLogged = false;
    public static List<User> users = new ArrayList<>();

    public static void setLogged(boolean logged) {
        isLogged = logged;
    }

    public static boolean getLogged() {
        return isLogged;
    }
    public static void setUsertoNull(){
        user = null;
    }

    public static User getUser() {
        return user;
    }
    public static void setIsSignedForEvent(boolean bool){
        if(CurrentUser.getUser()!=null){
            CurrentUser.getUser().isSignedForEvent = bool;
        }
    }
    public static Boolean getIsSignedForEvent(){
        if(CurrentUser.getUser()!=null){
            return CurrentUser.getUser().isSignedForEvent;
        }
        return false;
    }
    public static void setUser(User user1) {
        user = user1;
    }

}

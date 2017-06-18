package com.example.owner.android2.User;

import android.location.Location;

import com.example.owner.android2.Event.EventCompetition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by k_vol on 12/06/2017.
 */

public class CurrentUser {
    private static User user;
    private static EventCompetition event;
    private static boolean isLogged = false;

    public static List<com.example.owner.android2.User.UserKey> keys = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static List<EventCompetition> events = new ArrayList<>();

    public static String UserKey;

    public static void sortlist() {
        Collections.sort(users, new MyComparator());
    }

    public static void setLogged(boolean logged) {
        isLogged = logged;
    }

    public static boolean getLogged() {
        return isLogged;
    }

    public static void setUsertoNull() {
        user = null;
    }

    public static User getUser() {
        return user;
    }


    public static EventCompetition getEvent() {
        return event;
    }

    public static void setEvent(EventCompetition event) {
        CurrentUser.event = event;
        if(event!=null){
            setIsSignedForEvent(true);
        }
        else
            setIsSignedForEvent(false);
    }


    public static void setIsSignedForEvent(boolean bool) {
        if (CurrentUser.getUser() != null) {
            CurrentUser.getUser().isSignedForEvent = bool;
        }
    }

    public static Boolean getIsSignedForEvent() {
        if (CurrentUser.getUser() != null) {
            return CurrentUser.getUser().isSignedForEvent;
        }
        return false;
    }

    public static void setUser(User user1) {
        user = user1;
    }

    public static void trimEvents(){
        int size = CurrentUser.events.size();
        int y=0;
        int[] distance = new int[size];
        Location userlocation = new Location("user");
        Location eventlocation = new Location("event");
        userlocation.setLatitude(CurrentUser.getUser().location.Latitude);
        userlocation.setLongitude(CurrentUser.getUser().location.Longitude);
        for (int i = 0;i<size;i++){
            EventCompetition currentevent=CurrentUser.events.get(i);
            eventlocation.setLongitude(currentevent.location.Longitude);
            eventlocation.setLatitude(currentevent.location.Latitude);
            distance[i] = (int) eventlocation.distanceTo(userlocation);
        }
        for (int i = 0;i<size;i++){
            if (distance[i]>10000){
                CurrentUser.events.remove(y);
            }
            else{
                y++;
            }
        }

    }
}

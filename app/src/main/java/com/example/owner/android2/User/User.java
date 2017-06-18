package com.example.owner.android2.User;

import android.location.Location;

import com.example.owner.android2.location;

import java.util.Comparator;
import java.util.List;

/**
 * Created by k_vol on 11/06/2017.
 */

public class User {
    public String Name;
    public String Email;
    public String NickName;
    public String Password;
    public final int Score;
    public com.example.owner.android2.location location;
    public boolean isSignedForEvent;

    public User(String name,boolean isSigned, String email, String nickname, String password,int score, location location) {
        this.Name = name;
        this.Email = email;
        this.NickName = nickname;
        this.Password = password;
        this.location = location;
        this.Score = score;
        this.isSignedForEvent = isSigned;
    }
}
//class CustomComparator implements Comparator<User> {
//    @Override
//    public int compare(User o1, User o2) {
//        return o1.Score - (o2.Score);
//    }
//}

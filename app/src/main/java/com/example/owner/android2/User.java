package com.example.owner.android2;

import android.location.Location;

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
    public location location;
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
class MyComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if (o1.Score > o2.Score) {
            return -1;
        } else if (o1.Score < o2.Score) {
            return 1;
        }
        return 0;
    }}
//class CustomComparator implements Comparator<User> {
//    @Override
//    public int compare(User o1, User o2) {
//        return o1.Score - (o2.Score);
//    }
//}
class UserKey
{
    public String Key;
    public String NickName;
    public UserKey(String key, String nickName){
        this.Key = key;
        this.NickName = nickName;
    }
}
package com.example.owner.android2;

import android.location.Location;

import java.util.List;

/**
 * Created by k_vol on 11/06/2017.
 */

public class User {
    public String Name;
    public String Email;
    public String NickName;
    public String Password;
    public int Score;
    public location location;

    public User(String name, String email, String nickname, String password,int score, location location) {
        this.Name = name;
        this.Email = email;
        this.NickName = nickname;
        this.Password = password;
        this.location = location;
        this.Score = score;
    }
}

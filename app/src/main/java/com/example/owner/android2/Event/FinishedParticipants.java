package com.example.owner.android2.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k_vol on 18/06/2017.
 */
public class FinishedParticipants implements Serializable {
    public String user1;
    public String user2;
    public String user3;
    public String user4;
    public String user5;
    public List<String> users = new ArrayList<>();

    public FinishedParticipants(String u1) {
        this.user1 = u1;
        users.add(u1);
    }

    public FinishedParticipants(String u1, String u2) {
        this.user1 = u1;
        this.user2 = u2;
        users.add(u1);
        users.add(u2);
    }

    public FinishedParticipants(String u1, String u2, String u3) {
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
        users.add(u1);
        users.add(u2);
        users.add(u3);
    }

    public FinishedParticipants(String u1, String u2, String u3, String u4) {
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
        this.user4 = u4;
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
    }

    public FinishedParticipants(String u1, String u2, String u3, String u4, String u5) {
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
        this.user4 = u4;
        this.user5 = u5;
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
    }

}

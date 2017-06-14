package com.example.owner.android2;

import java.util.List;

/**
 * Created by k_vol on 14/06/2017.
 */

public class EventCompetition {
    public String Name;
    public Participants Participants;
    public FinishedParticipants FinishedParticipants;
    public int Slots;
    public boolean isActive;
    public boolean isStarted;
    public location location;

    public EventCompetition(String name,
                            boolean isactive,
                            boolean isstarted,
                            Participants participants,
                            FinishedParticipants finishedpsrticipants,
                            int slots, location location1){
        this.Name = name;
        this.Participants = participants;
        this.FinishedParticipants = finishedpsrticipants;
        this.Slots = slots;
        this.location = location1;
        this.isActive = isactive;
        this.isStarted = isstarted;
    }
}
class Participants{
    public String user1;
    public String user2;
    public String user3;
    public String user4;
    public String user5;
    public Participants(String u1){
        this.user1 = u1;
    }
    public Participants(String u1,String u2){
        this.user1 = u1;
        this.user2 = u2;
    }
    public Participants(String u1,String u2,String u3){
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
    }
    public Participants(String u1,String u2,String u3,String u4){
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
        this.user4 = u4;
    }
    public Participants(String u1,String u2,String u3,String u4,String u5){
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
        this.user4 = u4;
        this.user5 = u5;
    }
}
class FinishedParticipants{
    public String user1;
    public String user2;
    public String user3;
    public String user4;
    public String user5;
    public FinishedParticipants(String u1){
        this.user1 = u1;
    }
    public FinishedParticipants(String u1,String u2){
        this.user1 = u1;
        this.user2 = u2;
    }
    public FinishedParticipants(String u1,String u2,String u3){
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
    }
    public FinishedParticipants(String u1,String u2,String u3,String u4){
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
        this.user4 = u4;
    }
    public FinishedParticipants(String u1,String u2,String u3,String u4,String u5){
        this.user1 = u1;
        this.user2 = u2;
        this.user3 = u3;
        this.user4 = u4;
        this.user5 = u5;
    }
}

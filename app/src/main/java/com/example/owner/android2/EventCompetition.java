package com.example.owner.android2;

import java.util.List;

/**
 * Created by k_vol on 14/06/2017.
 */

public class EventCompetition {
    public String Name;
    public List<User> Participants;
    public List<User> FinishedParticipants;
    public int Slots;
    public boolean isActive;
    public location loc;

    public EventCompetition(String name,boolean isactive,List<User> psrticipants, List<User> finishedpsrticipants,int slots,location location1){
        this.Name = name;
        this.Participants = psrticipants;
        this.FinishedParticipants = finishedpsrticipants;
        this.Slots = slots;
        this.loc = location1;
        this.isActive = isactive;
    }
}

package com.example.owner.android2.Event;

import com.example.owner.android2.location;

import java.io.Serializable;

/**
 * Created by k_vol on 14/06/2017.
 */

public class EventCompetition implements Serializable{
    public String Name;
    public com.example.owner.android2.Event.Participants Participants;
    public com.example.owner.android2.Event.FinishedParticipants FinishedParticipants;
    public int Slots;
    public boolean isActive;
    public boolean isStarted;
    public com.example.owner.android2.location location;

    public EventCompetition(String name, boolean isactive, boolean isstarted, Participants psrticipants, FinishedParticipants finishedpsrticipants, int slots, location location1) {
        this.Name = name;
        this.Participants = psrticipants;
        this.FinishedParticipants = finishedpsrticipants;
        this.Slots = slots;
        this.location = location1;
        this.isActive = isactive;
        this.isStarted = isstarted;
    }

}



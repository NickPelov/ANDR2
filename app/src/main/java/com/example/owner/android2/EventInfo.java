package com.example.owner.android2;

import android.app.usage.UsageEvents;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventInfo extends AppCompatActivity {
    EventCompetition event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        event =(EventCompetition) getIntent().getSerializableExtra("bundle");
        View view = findViewById(R.id.event_info);
        TextView names = (TextView) view.findViewById(R.id.event_name);
        TextView slots = (TextView) view.findViewById(R.id.event_slots);
        TextView status = (TextView) view.findViewById(R.id.event_status);
        names.setText(event.Name);
        slots.setText(String.valueOf(getNumberOfParticipants()+"/"+event.Slots));
        if (event.isActive){
            status.setText("Active");
        }
        else{
            status.setText("Innactive");
        }



    }
    public int getNumberOfParticipants(){
        int number=0;
        for (int y = 0; y < event.Participants.users.size(); y++) {
            if (!event.Participants.users.get(y).equals("")) {
                number++;
            }
        }
        return number;
    }
    public String[] getParticipants(){
        String[] participants=new String[getNumberOfParticipants()];
        for (int y = 0; y < event.Participants.users.size(); y++) {
            if (!event.Participants.users.get(y).equals("")) {
                participants[y]=event.Participants.users.get(y);
            }
        }
        return participants;
    }
}

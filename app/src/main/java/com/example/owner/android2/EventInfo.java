package com.example.owner.android2;

import android.app.usage.UsageEvents;
import android.graphics.Color;
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
        TextView player1 = (TextView) view.findViewById(R.id.player1);
        TextView player2 = (TextView) view.findViewById(R.id.player2);
        TextView player3 = (TextView) view.findViewById(R.id.player3);
        TextView player4 = (TextView) view.findViewById(R.id.player4);
        TextView player5 = (TextView) view.findViewById(R.id.player5);

        names.setText(event.Name);
        slots.setText(String.valueOf(getNumberOfParticipants()+"/"+event.Slots));
        if (event.isActive & event.isStarted){
            status.setTextColor(Color.GREEN);
            status.setText("Active");
        }
        else if (!event.isActive && !event.isStarted){
            status.setTextColor(Color.GRAY);
            status.setText("Not Started");
        }
        else if (!event.isActive && event.isStarted){
            status.setTextColor(Color.RED);
            status.setText("Finished");
        }
        String[] s= getParticipants();
        player1.setText(s[0]);
        player2.setText(s[1]);
        player3.setText(s[2]);
        player4.setText(s[3]);
        player5.setText(s[4]);



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
        String[] participants= {"","","","",""};

        for (int y = 0; y < event.Participants.users.size(); y++) {
            if (event.Participants.users.get(y).equals("")) {
                participants[y]="Slot is Free";

            }
            else {
                participants[y]=event.Participants.users.get(y);
            }

        }
        return participants;
    }
}

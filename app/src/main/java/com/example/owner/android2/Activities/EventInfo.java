package com.example.owner.android2.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.android2.Event.EventCompetition;
import com.example.owner.android2.FireBaseConnection;
import com.example.owner.android2.R;
import com.example.owner.android2.User.CurrentUser;

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
        Button signup = (Button) view.findViewById(R.id.event_signup);
        signup.setTag(event);
        if (event.isActive & event.isStarted){
            status.setTextColor(Color.GREEN);
            status.setText("Active");
        }
        else if (!event.isActive && event.isStarted){
            status.setTextColor(Color.RED);
            status.setText("Finished");
        }
        else {
            status.setTextColor(Color.GRAY);
            status.setText("Not Started");
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
    public void registerUserForEvent(View view) {
        if (!CurrentUser.getUser().isSignedForEvent){
            EventCompetition eventSignup =(EventCompetition) view.getTag();
            CurrentUser.setEvent(eventSignup);
            CurrentUser.getUser().isSignedForEvent = true;
            int number = getNumberOfParticipants() + 1;
            FireBaseConnection.setEventParticipant(eventSignup,number);
            Intent intent = new Intent(this, ProfileActivity2.class);
            startActivity(intent);
            Toast.makeText(this, "You are signed up for "+eventSignup.Name, Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, EventsActivity.class);
            startActivity(intent);
            Toast.makeText(this, "You are already signed up for an event", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }
}

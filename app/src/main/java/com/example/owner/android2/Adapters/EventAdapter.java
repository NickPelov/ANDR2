package com.example.owner.android2.Adapters;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.owner.android2.Event.EventCompetition;
import com.example.owner.android2.R;
import com.example.owner.android2.User.CurrentUser;

import java.util.Locale;

/**
 * Created by Nikolay Pelov on 16.6.2017 г..
 */

public class EventAdapter extends ArrayAdapter<Object> {
    public EventAdapter(Context context, EventCompetition[] events) {
        super(context, R.layout.fragment_event_item, events);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup par) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.fragment_event_item, par, false);
            view.setId(position);


        TextView slots_txt = (TextView) view.findViewById(R.id.fragment_txt_slots);
        TextView distance_txt = (TextView) view.findViewById(R.id.fragment_textView);
        Button b = (Button) view.findViewById(R.id.button2);
        b.setId(position);
        Location locationUser = new Location("gosho");
        Location loc = new Location("pesho");
        locationUser.setLatitude(CurrentUser.getUser().location.Latitude);
        locationUser.setLongitude(CurrentUser.getUser().location.Longitude);
        EventCompetition event = (EventCompetition) getItem(position);

        String distanceArray;
        int registeredParticipants = 0;

        if (event != null){
            for (int y = 0; y < event.Participants.users.size(); y++) {
                if (!event.Participants.users.get(y).equals("")) {
                    registeredParticipants++;
                }
            }
            loc.setLongitude(event.location.Longitude);
            loc.setLatitude(event.location.Latitude);
            int distance = (int) loc.distanceTo(locationUser);
            String slots = registeredParticipants + "/" + event.Slots;

            if(distance>1000){
                distanceArray = String.format(Locale.ENGLISH,"%,d", distance/1000) + "km away";
            }else{
                distanceArray = String.format(Locale.ENGLISH,"%,d", distance) + "m away";
            }





            distance_txt.setText(distanceArray);
            slots_txt.setText(slots);
        }




        return view;

    }

}

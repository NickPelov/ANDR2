package com.example.owner.android2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Nikolay Pelov on 16.6.2017 г..
 */

public class CustomAdapter extends ArrayAdapter<String> {
    CustomAdapter(Context context, String[] amama){
        super(context,R.layout.fragment_event_item,amama);
    }

    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView,@NonNull ViewGroup par){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.fragment_event_item,par,false);

        String slots = getItem(position);
        TextView slots_txt = (TextView) customView.findViewById(R.id.fragment_txt_slots);
        TextView distance_txt = (TextView) customView.findViewById(R.id.fragment_textView);

        slots_txt.setText(slots);
        return customView;

    }
}

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

public class CustomAdapter extends ArrayAdapter<Object> {
    CustomAdapter(Context context, ListItem li[]){
        super(context,R.layout.fragment_event_item,li);
    }

    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView,@NonNull ViewGroup par){
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            convertView = inflater.inflate(R.layout.fragment_event_item,par,false);
        }


        ListItem slots = (ListItem) getItem(position);
        TextView slots_txt = (TextView) convertView.findViewById(R.id.fragment_txt_slots);
        TextView distance_txt = (TextView) convertView.findViewById(R.id.fragment_textView);

        distance_txt.setText(slots.textview1);
        slots_txt.setText(slots.textview2);

        return convertView;

    }
}

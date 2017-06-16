package com.example.owner.android2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by k_vol on 16/06/2017.
 */

public class AdapterLeaderBoard extends ArrayAdapter<Object> {

    AdapterLeaderBoard(Context context,ListItem li[]){

        super(context,R.layout.fragment_leaderboard_item,li);
    }
    AdapterLeaderBoard(Context context,String[] am){

        super(context,R.layout.fragment_leaderboard_item,am);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup par){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.fragment_leaderboard_item,par,false);

        ListItem slots = (ListItem) getItem(position);
        TextView user_place_txt = (TextView) customView.findViewById(R.id.fragment_txt_place);
        TextView user_name_txt = (TextView) customView.findViewById(R.id.fragment_userName_textView);
        TextView user_score_txt = (TextView) customView.findViewById(R.id.fragment_txt_score);
        user_place_txt.setText(slots.textview1);
        user_name_txt.setText(slots.textview2);
        user_score_txt.setText(slots.textview3);

        return customView;

    }
}
class ListItem
{
    public String textview1;
    public String textview2;
    public String textview3;
    public int imageId;

    public ListItem(String t1, String t2, String t3, int imageId)
    {
        this.textview1 = t1;
        this.textview2 = t2;
        this.textview3 = t3;
        this.imageId = imageId;
    }
}

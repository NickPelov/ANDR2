package com.example.owner.android2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by k_vol on 16/06/2017.
 */

public class AdapterLeaderBoard extends ArrayAdapter<Object> {

    AdapterLeaderBoard(Context context, ListItem li[]) {

        super(context, R.layout.fragment_leaderboard_item, li);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup par) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_leaderboard_item, par, false);
        }
        RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.relativeLayoutMain);
        ListItem slots = (ListItem) getItem(position);
        TextView user_place_txt = (TextView) convertView.findViewById(R.id.fragment_txt_place);
        TextView user_name_txt = (TextView) convertView.findViewById(R.id.fragment_userName_textView);
        TextView user_score_txt = (TextView) convertView.findViewById(R.id.fragment_txt_score);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_leaderboard);
        switch (slots.textview1) {
            case "1.":
                imageView.setImageResource(R.mipmap.first_place);
                user_place_txt.setText("");
                break;
            case "2.":
                imageView.setImageResource(R.mipmap.second_place);
                user_place_txt.setText("");
                break;
            case "3.":
                imageView.setImageResource(R.mipmap.third_place);
                user_place_txt.setText("");
                break;
            default:
                imageView.setImageResource(0);
                user_place_txt.setText(slots.textview1);
                break;
        }
        rl.setBackgroundColor(Color.parseColor("#ffffff"));
        if (slots.textview2.equals(CurrentUser.getUser().NickName)) {
            rl.setBackgroundColor(Color.parseColor("#989da5"));
        }
        user_name_txt.setText(slots.textview2);
        user_score_txt.setText(slots.textview3);

        return convertView;

    }
}

class ListItem implements Comparable<ListItem> {
    public String textview1;
    public String textview2;
    public String textview3;

    public ListItem(String t1, String t2, String t3) {
        this.textview1 = t1;
        this.textview2 = t2;
        this.textview3 = t3;
    }

    public ListItem(String t1, String t2) {
        this.textview1 = t1;
        this.textview2 = t2;
    }

    //    Collections.sort(list1, new Comparator<ListItem>() {
//    @Override public int compare(ListItem p1, ListItem p2) {
//        return Integer.parseInt(p1.textview3) - Integer.parseInt(p2.textview3); // Ascending
//    }
//
//});
    @Override
    public int compareTo(ListItem o) {
        // usually toString should not be used,
        // instead one of the attributes or more in a comparator chain
        return toString().compareTo(o.toString());
    }

}

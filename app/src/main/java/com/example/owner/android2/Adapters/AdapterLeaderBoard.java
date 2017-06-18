package com.example.owner.android2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.owner.android2.R;
import com.example.owner.android2.User.CurrentUser;

/**
 * Created by k_vol on 16/06/2017.
 */

public class AdapterLeaderBoard extends ArrayAdapter<Object> {

    public AdapterLeaderBoard(Context context, ListItem li[]) {

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

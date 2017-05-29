package com.example.owner.android2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by Yasen on 5/24/2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}

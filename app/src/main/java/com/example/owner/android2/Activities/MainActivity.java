package com.example.owner.android2.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.owner.android2.FireBaseConnection;
import com.example.owner.android2.User.CurrentUser;
import com.example.owner.android2.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yasen on 5/24/2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connect();
    }

    //loading the map
    public void loadMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

    public void Register(View view) {
        Connect();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * initi. login activity
     *
     * @param view
     */
    public void loadLogin(View view) {
        Connect();
        Intent innt = new Intent(this, LoginActivity.class);
        startActivity(innt);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        Connect();
        // Always call the superclass method first
        if (CurrentUser.getLogged()) {
            Intent intent = new Intent(this, ProfileActivity2.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                }).create().show();
    }
    private void Connect(){
        if (isNetworkAvailable()) {
            if (FireBaseConnection.isLoggedForFirstTime) {
                FireBaseConnection.LoadFromDB(CurrentUser.users);
                FireBaseConnection.getEvents(CurrentUser.events);
                FireBaseConnection.isLoggedForFirstTime=false;
            }
        }
        else{
            Toast.makeText(getBaseContext(), "Please Connect to the internet", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

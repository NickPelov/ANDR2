package com.example.owner.android2.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.android2.Event.FinishedParticipants;
import com.example.owner.android2.FireBaseConnection;
import com.example.owner.android2.Event.Participants;
import com.example.owner.android2.R;
import com.example.owner.android2.User.CurrentUser;

public class PushEventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private View view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        TextView CurrentLatitude = (TextView) findViewById(R.id.CurrentLatitude);
        TextView CurrentLongitude = (TextView) findViewById(R.id.CurrentLongitude);

        final EditText eventName = (EditText) findViewById(R.id.eventNameText);
        final EditText eventSlots = (EditText) findViewById(R.id.SlotsText);
        final EditText latitudeText = (EditText) findViewById(R.id.LatitudeText);
        final EditText longitudeText = (EditText) findViewById(R.id.LongitudeText);
        Button create = (Button) findViewById(R.id.event_push_DB);


        CurrentLatitude.setText("Current latitude: " + String.valueOf(CurrentUser.getUser().location.Latitude));
        CurrentLongitude.setText("Current longitude: " + String.valueOf(CurrentUser.getUser().location.Longitude));

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = eventName.getText().toString();
                final int slots = Integer.parseInt(eventSlots.getText().toString());
                final double latitude = Double.parseDouble(latitudeText.getText().toString());
                final double longitude = Double.parseDouble(longitudeText.getText().toString());
                Participants p;
                FinishedParticipants fp;
                switch (slots) {
                    case 1:
                        p = new Participants("");
                        fp = new FinishedParticipants("");
                        break;
                    case 2:
                        p = new Participants("", "");
                        fp = new FinishedParticipants("", "");
                        break;
                    case 3:
                        p = new Participants("", "", "");
                        fp = new FinishedParticipants("", "", "");
                        break;
                    case 4:
                        p = new Participants("", "", "", "");
                        fp = new FinishedParticipants("", "", "", "");
                        break;
                    default:
                        p = new Participants("", "", "", "", "");
                        fp = new FinishedParticipants("", "", "", "", "");
                        break;
                }

                FireBaseConnection.pushNewEvent(name, slots, p, fp, latitude, longitude);
                Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
                finish();
                gotoProfile(view2);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_profile2_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.map_option) {
            gotoMap(view2);
            finish();
        } else if (id == R.id.events_option) {
            gotoEvents(view2);
            finish();
        } else if (id == R.id.profile_option) {
            gotoProfile(view2);
            finish();
        }
//        else if (id == R.id.push_events_option) {
//            gotoPushEvent(view2);
//            finish();
//        }
        else if (id == R.id.leaderboard_option) {
            gotoLeaderBoard(view2);
            finish();
        } else if (id == R.id.exit_option) {
            logoutFromProfile();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    //going to the leaderboard
    public void gotoLeaderBoard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    //going to the map
    public void gotoMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //going to the achievements
    public void gotoAchiv(View view) {
        Intent intent = new Intent(this, AchivementsActivity.class);
        startActivity(intent);
    }

    //going to the settings
    public void gotoSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //going to the events
    public void gotoEvents(View view) {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    //going to the profile
    public void gotoProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity2.class);
        startActivity(intent);
    }

    //going to the main
    public void gotoMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        finish();
        gotoProfile(view2);
    }

    public void logoutFromProfile() {

        new AlertDialog.Builder(this)
                .setTitle("Really Log out?")
                .setMessage("Are you sure you want to log out?")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        CurrentUser.setLogged(false);
                        CurrentUser.setUsertoNull();
                        gotoMain(view2);
                    }
                }).create().show();
    }
}

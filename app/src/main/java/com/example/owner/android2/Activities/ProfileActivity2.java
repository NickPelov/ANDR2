package com.example.owner.android2.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owner.android2.User.CurrentUser;
import com.example.owner.android2.FireBaseConnection;
import com.example.owner.android2.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProfileActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private View view2;

    TextView nickNameTextView;
    TextView emailTextView;
    TextView pointsTextView;

    ImageView imageView;

    float distance;
    Thread NotificationThread = threadNotification();

    public static final int RESULT_LOAD_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (CurrentUser.getUser().NickName.equals("ADMIN")) {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view2 = view;
                    gotoPushEvent(view);
                    invalidateOptionsMenu();
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        nickNameTextView = (TextView) findViewById(R.id.RetrievedProfileName);
        emailTextView = (TextView) findViewById(R.id.RetrivedProfileEmail);
        pointsTextView = (TextView) findViewById(R.id.RetrievedProfilePoints);

        nickNameTextView.setText(CurrentUser.getUser().NickName);
        emailTextView.setText(CurrentUser.getUser().Email);
        pointsTextView.setText(String.valueOf(CurrentUser.getUser().Score));

        imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
           NotificationThread.start();

    }

    public Thread threadNotification() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (FireBaseConnection.isTrue) {
                        Location loc1 = new Location("y");
                        loc1.setLatitude(CurrentUser.getUser().location.Latitude);
                        loc1.setLongitude(CurrentUser.getUser().location.Longitude);
                        Location loc2 = new Location("z");
                        loc2.setLatitude(CurrentUser.events.get(CurrentUser.events.size() - 1).location.Latitude);
                        loc2.setLongitude(CurrentUser.events.get(CurrentUser.events.size() - 1).location.Longitude);
                        int slots = CurrentUser.events.get(CurrentUser.events.size() - 1).Slots;
                        distance = loc1.distanceTo(loc2);
                        showNotification(view2, slots);
                        FireBaseConnection.isTrue = false;
                        FireBaseConnection.isInitial = true;
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void showNotification(View v, int slot) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_icon2)
                .setContentTitle("There is a new event for " + slot + " people")
                .setContentText("it is " + new DecimalFormat("#.##").format(distance) + "km away!");
        Intent resultIntent = new Intent(this, EventsActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(EventsActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_profile2_drawer, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if(CurrentUser.getUser().Name.equals("ADMIN")){
////            int i = menu.getItem(4).getItemId();
//////            MenuItem item = menu.findItem(i);
////            menu.getItem(i).setVisible(true);
//            menu.findItem(R.id.push_events_option).setVisible(true);
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        MenuItem m;
        if (id == R.id.map_option) {
            gotoMap(view2);
            finish();
        } else if (id == R.id.events_option) {
            gotoEvents(view2);
            finish();
        } else if (id == R.id.settings_option) {
            gotoSettings(view2);
            finish();
        } else if (id == R.id.achievements_option) {
            gotoAchiv(view2);
            finish();
        } else if (id == R.id.profile_option) {
            gotoProfile(view2);
            finish();
        }
//        else if(id == R.id.push_events_option) {
//            if(CurrentUser.getUser().Name.equals("ADMIN")){
//                gotoPushEvent(view2);
//                finish();}
//
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

    //going to the eventoush
    public void gotoPushEvent(View view) {
        finish();
        NotificationThread.interrupt();
        Intent intent = new Intent(this, PushEventActivity.class);
        startActivity(intent);
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
        logoutFromProfile();
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
                        ProfileActivity2.super.onBackPressed();
                    }
                }).create().show();
    }

}

package com.example.owner.android2.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.owner.android2.Event.EventCompetition;
import com.example.owner.android2.FireBaseConnection;
import com.example.owner.android2.R;
import com.example.owner.android2.User.CurrentUser;
import com.example.owner.android2.User.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    public static final String TAG = MapsActivity.class.getSimpleName();

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationRequest mLocationRequest;

    private LatLng latLng;

    Location loc = null;
    Location location = null;
    Vibrator vibrator;
    int izverg = 1;

    public List<User> otherUsers = new ArrayList<>();
    public Button finished;
    View view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
         vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(100);

        Button bt1 = (Button) findViewById(R.id.buttonBack);
        Button btnRefersh = (Button) findViewById(R.id.buttonForceRefresh);
        finished= (Button)findViewById(R.id.buttonFinish);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view2 = v;
                goBackToProfile(v);
            }
        });
        btnRefersh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();
            }
        });
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                participantFinished(v);
            }
        });
        mGoogleApiClient.connect();
        updateLocation();
    }

    @Override
    protected void onResume() {
        mGoogleApiClient.connect();
        updateLocation();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        mMap.setMyLocationEnabled(true);
//        updateLocation();
    }

    @Override
    public void onConnected(Bundle bundle) {
        updateLocation();
    }

    private void updateLocation() {
        boolean fineCoarse = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
        boolean coarse = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        if ((fineCoarse || coarse) && izverg == 1) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        } else if (izverg == 2) {
        } else {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                handleNewLocation(location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    izverg++;
                    goBackToProfile(view2);
                }
            }
        }
    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        latLng = new LatLng(currentLatitude, currentLongitude);

        mMap.clear();
        new LoadDB(location, loc,mMap).execute();
        new LongOperation(mMap,finished,vibrator).execute();
        MarkerOptions options = new MarkerOptions()
                .position(latLng).title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(14).bearing(0).tilt(40).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

//    private void handleNewLocationFromDB(double longitude, double latitude) {
//        double currentLatitude = latitude;
//        double currentLongitude = longitude;
//
//        latLng = new LatLng(currentLatitude, currentLongitude);
//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//        mMap.clear();
//        mMap.addMarker(options);
//        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.event1)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(latLng).zoom(14).bearing(90).tilt(40).build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        handleNewLocation(location);
    }

    public void goBackToProfile(View view) {
        try {
            finish();
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                mGoogleApiClient.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, ProfileActivity2.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goBackToProfile(view2);
    }

    public void participantFinished(View view) {
        EventCompetition event = CurrentUser.getEvent();
        int number=0;
        for (int y = 0; y < event.FinishedParticipants.users.size(); y++) {
            if (!event.FinishedParticipants.users.get(y).equals("")) {
                number++;
            }
        }
        FireBaseConnection.participantFinished(number+1);
        Intent intent = new Intent(this, ProfileActivity2.class);
        intent.putExtra("Position",number+1);
        startActivity(intent);
    }

}

class LoadDB extends AsyncTask<Object, Object, Void> {

    private Location location;
    private Location loc;
    private GoogleMap mMap1;
    private List<LatLng> listLatLng;
    private List<String> nickNamess;

    public LoadDB(Location location, Location loc, GoogleMap mM) {
        this.location = location;
        this.loc = loc;
        this.mMap1 = mM;
        listLatLng = new ArrayList<>();
        nickNamess = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Object... params) {
        listLatLng = new ArrayList<>();
        nickNamess = new ArrayList<>();
        if (CurrentUser.getUser().NickName.equals("ADMIN")) {
            for (User u : CurrentUser.users
                    ) {
                if (u.location.Longitude == 0 && u.location.Latitude == 0) {
                    continue;
                }
                if (u.NickName.equals(CurrentUser.getUser().NickName)) {
                    continue;
                } else {
                    listLatLng.add(new LatLng(u.location.Latitude, u.location.Longitude));
                    nickNamess.add(u.NickName);
                }
            }
        }
        if (loc != location) {
            loc = location;
            FireBaseConnection.setUserLocation(CurrentUser.getUser().NickName, CurrentUser.getUser()
                    .Email, loc.getLatitude(), loc.getLongitude());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (CurrentUser.getUser().NickName.equals("ADMIN")) {
            for (int i = 0; i < listLatLng.size(); i++) {
                mMap1.addMarker(new MarkerOptions().position(listLatLng.get(i)).title(nickNamess.get(i))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Object... values) {
    }
}

class LongOperation extends AsyncTask<Object, Object, Void> {


    private List<String> eventName;
    private List<LatLng> listEventsLatLng;
    private GoogleMap mMap1;
    Button finish;
    Vibrator vibrator;
    public LongOperation(GoogleMap mM,Button b,Vibrator v) {
        this.mMap1 = mM;
        this.finish = b;
        this.vibrator = v;
        listEventsLatLng = new ArrayList<>();
        eventName = new ArrayList<>();

    }

    @Override
    protected Void doInBackground(Object... params) {
        listEventsLatLng = new ArrayList<>();
        eventName = new ArrayList<>();
        if (CurrentUser.getUser().NickName.equals("ADMIN")) {
            for (EventCompetition event : CurrentUser.events
                    ) {
                listEventsLatLng.add(new LatLng(event.location.Latitude, event.location.Longitude));
                eventName.add(event.Name);
            }
        }
        Location userlocation = new Location("user");
        Location user2location = new Location("event");
        userlocation.setLatitude(CurrentUser.getUser().location.Latitude);
        userlocation.setLongitude(CurrentUser.getUser().location.Longitude);
        for (User user:CurrentUser.users){
            user2location.setLongitude(user.location.Longitude);
            user2location.setLatitude(user.location.Latitude);
            int distance = (int) user2location.distanceTo(userlocation);
            if (distance<100){
                vibrator.vibrate(250);
            }
        }



        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (CurrentUser.getUser().NickName.equals("ADMIN")) {
            for (int i = 0; i < listEventsLatLng.size(); i++) {
                mMap1.addMarker(new MarkerOptions().position(listEventsLatLng.get(i)).title(eventName.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.event1)));
                mMap1.addCircle(new CircleOptions()
                        .center(listEventsLatLng.get(i))
                        .radius(100));
            }
        }
        if (CurrentUser.getUser().isSignedForEvent) {
            mMap1.addMarker(new MarkerOptions().position(
                    new LatLng(CurrentUser.getEvent().location.Latitude, CurrentUser.getEvent().location.Longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.event1)));
            mMap1.addCircle(new CircleOptions()
                    .center(new LatLng(CurrentUser.getEvent().location.Latitude, CurrentUser.getEvent().location.Longitude))
                    .radius(100));
            Location userlocation = new Location("user");
            Location eventlocation = new Location("event");
            userlocation.setLatitude(CurrentUser.getUser().location.Latitude);
            userlocation.setLongitude(CurrentUser.getUser().location.Longitude);
            eventlocation.setLongitude(CurrentUser.getEvent().location.Longitude);
            eventlocation.setLatitude(CurrentUser.getEvent().location.Latitude);
            int distance = (int) eventlocation.distanceTo(userlocation);
            if (distance>100){
                finish.setVisibility(View.INVISIBLE);
            }else{
                finish.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Object... values) {
    }
}
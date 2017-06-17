package com.example.owner.android2;

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
    Location location = null;
    Thread sendLocationThread;

    boolean doWork = false;
    int izverg = 1;

    public List<User> otherUsers = new ArrayList<>();
    //    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//    private DatabaseReference mUser = mRootRef.child("user");
//    private DatabaseReference mLocation = mUser.child("location");
//    private DatabaseReference mConditionLatitude = mLocation.child("Latitude");
//    private DatabaseReference mConditionLongitude = mLocation.child("Longitude");
    double latitude;
    double longitude;
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

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(100);

        Button bt1 = (Button) findViewById(R.id.buttonBack);
        Button btnRefersh = (Button) findViewById(R.id.buttonForceRefresh);

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

        mGoogleApiClient.connect();
        updateLocation();
        th1().start();
        th2().start();
//        mConditionLatitude.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                latitude = dataSnapshot.getValue(Double.class);
//                handleNewLocationFromDB(longitude,latitude);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mConditionLongitude.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                longitude = dataSnapshot.getValue(Double.class);
//                handleNewLocationFromDB(longitude,latitude);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        mGoogleApiClient.connect();
        updateLocation();
        th1().start();
        th2().start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
            try {
                th1().interrupt();
                th2().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        mMap.setMyLocationEnabled(true);
        updateLocation();
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

    private Thread th1() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Location loc = null;
                while (true) {
                    while (location != null) {
                        if (loc != location) {
                            loc = location;
                            FireBaseConnection.setUserLocation(CurrentUser.getUser().NickName, CurrentUser.getUser()
                                    .Email, loc.getLatitude(), loc.getLongitude());
                        }
                    }
                }
            }
        });
    }

    private Thread th2() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    otherUsers = new ArrayList<>();
                    FireBaseConnection.LoadFromDB(otherUsers);
                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    LocationManager ss = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
        new LongOperation(otherUsers, mMap).execute();

        MarkerOptions options = new MarkerOptions()
                .position(latLng).title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

        mMap.addMarker(options);
//        mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude + 0.02, currentLongitude + 0.02)).icon(BitmapDescriptorFactory.fromResource(R.drawable.event1)));
//        mMap.addCircle(new CircleOptions()
//                .center(new LatLng(currentLatitude + 0.02, currentLongitude + 0.02))
//                .radius(100));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(14).bearing(90).tilt(40).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        doWork=true;
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
        finish();
        try {
            th1().interrupt();
            th2().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, ProfileActivity2.class);
        startActivity(intent);
    }
    public void refresh(View view) {
        finish();
        try {
            th1().interrupt();
            th2().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}

class LongOperation extends AsyncTask<Object, Object, Void> {

    List<User> otherUsers1;
    GoogleMap mMap1;
    List<LatLng> listLatLng;
    List<String> nickNamess;

    public LongOperation(List<User> otheru, GoogleMap mM) {
        this.otherUsers1 = otheru;
        this.mMap1 = mM;
        listLatLng = new ArrayList<>();
        nickNamess = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Object... params) {
        listLatLng = new ArrayList<>();
        nickNamess = new ArrayList<>();
        for (User u : otherUsers1
                ) {
            if (u.location.Longitude == 0 && u.location.Latitude == 0) {
                continue;
            }
            if (CurrentUser.getUser().NickName.equals("ADMIN")) {
                if (u.NickName.equals(CurrentUser.getUser().NickName)) {
                    continue;
                } else {
                    listLatLng.add(new LatLng(u.location.Latitude, u.location.Longitude));
                    nickNamess.add(u.NickName);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        for (int i = 0; i < listLatLng.size(); i++) {
            mMap1.addMarker(new MarkerOptions().position(listLatLng.get(i)).title(nickNamess.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Object... values) {
    }
}

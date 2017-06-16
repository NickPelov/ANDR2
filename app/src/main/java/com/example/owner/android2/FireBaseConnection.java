package com.example.owner.android2;

import android.icu.text.MessagePattern;

import com.google.android.gms.games.snapshot.Snapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by k_vol on 12/06/2017.
 */

public class FireBaseConnection {


    static DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    public static void LoadFromDB(final List<User> users) {

        mRootRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String name = snap.child("Name").getValue(String.class);
                    String email = snap.child("Email").getValue(String.class);
                    String nickname = snap.child("NickName").getValue(String.class);
                    String pass = snap.child("Password").getValue(String.class);
                    int score = snap.child("Score").getValue(Integer.class);
                    Double lati = snap.child("location").child("Latitude").getValue(Double.class);
                    Double longi = snap.child("location").child("Longitude").getValue(Double.class);

                    users.add(new User(name, email, nickname, pass,score, new location(lati, longi)));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * pushing a new instance to the fireDB through the params:
     *
     * @param name
     * @param email
     * @param password
     * @param nickName
     */
    public static void pushNewInstanceUser(String name, String email, String password, String nickName) {
        try {

            DatabaseReference usersTable = mRootRef.child("users").push();
            String pushId = usersTable.getKey();
            usersTable.setValue(new User(name, email, nickName, password,0, new location(0, 0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pushNewEvent(String name, int slots,Participants participants,FinishedParticipants finishedparticipants, Double latitude, Double longitude) {
        try {

            DatabaseReference usersTable = mRootRef.child("events").push();
            String pushId = usersTable.getKey();
            usersTable.setValue(new EventCompetition(name,true,false,participants,finishedparticipants,slots,new location(latitude,longitude)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //neznam dali raboti
    public static User getUser(final String nickName1, final String email1) {

        final User[] user = {null};
        mRootRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String name = snap.child("Name").getValue(String.class);
                    String email = snap.child("Email").getValue(String.class);
                    String nickname = snap.child("NickName").getValue(String.class);
                    String pass = snap.child("Password").getValue(String.class);
                    int score = snap.child("Score").getValue(Integer.class);
                    Double lati = snap.child("location").child("Latitude").getValue(Double.class);
                    Double longi = snap.child("location").child("Longitude").getValue(Double.class);
                    if (nickName1.equals(nickname) && email1.equals(email)) {
                        user[0] = new User(name, email, nickname, pass,score, new location(lati, longi));
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
        return user[0];
    }

    public static void setUserLocation(final String nickName1, final String email1, final Double longitude, final Double lalitude) {

        mRootRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String email = snap.child("Email").getValue(String.class);
                    String nickname = snap.child("NickName").getValue(String.class);
                    if (nickName1.equals(nickname) && email1.equals(email)) {
                        String key = String.valueOf(snap.getKey());
                        DatabaseReference mUser = mRootRef.child("users").child(key).child("location");
                        Map<String,Object> taskMap = new HashMap<String,Object>();
                        taskMap.put("Latitude", longitude);
                        taskMap.put("Longitude",lalitude);
                        mUser.updateChildren(taskMap);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

    private static String p(int number,DataSnapshot snap){
        return  snap.child("Participants").child("user"+number).getValue(String.class);
    }
    public static void getEvents(final List<EventCompetition> events){
        mRootRef.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String name = snap.child("Name").getValue(String.class);
                    boolean isactive = snap.child("isActive").getValue(Boolean.class);
                    Boolean isstarted = snap.child("isStarted").getValue(Boolean.class);
                    FinishedParticipants finishedparticipants = new FinishedParticipants("");
                    int slots = snap.child("Slots").getValue(int.class);
                    Participants participants;

                    switch (slots){
                        case 1: participants = new Participants(p(1,snap));
                            break;
                        case 2:participants = new Participants(p(1,snap),p(2,snap));
                            break;
                        case 3:participants = new Participants(p(1,snap),p(2,snap),p(3,snap));
                            break;
                        case 4:participants = new Participants(p(1,snap),p(2,snap),p(3,snap),p(4,snap));
                            break;
                        default:participants = new Participants(p(1,snap),p(2,snap),p(3,snap),p(4,snap),p(5,snap));
                            break;
                    }
                    Double lati = snap.child("location").child("Latitude").getValue(Double.class);
                    Double longi = snap.child("location").child("Longitude").getValue(Double.class);

                    events.add(new EventCompetition(name,isactive,isstarted,participants, finishedparticipants,slots,new location(lati,longi)));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.example.owner.android2;

import com.example.owner.android2.Event.EventCompetition;
import com.example.owner.android2.Event.FinishedParticipants;
import com.example.owner.android2.Event.Participants;
import com.example.owner.android2.User.CurrentUser;
import com.example.owner.android2.User.User;
import com.example.owner.android2.User.UserKey;
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
    public static boolean isLoggedForFirstTime = true;
    public static boolean isInitial = true;
    public static boolean isTrue = false;
    public static EventCompetition tempRecord;

    public static void LoadFromDB(final List<User> users) {

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String key = snap.getKey();
                    String name = snap.child("Name").getValue(String.class);
                    String email = snap.child("Email").getValue(String.class);
                    boolean isSigned = snap.child("isSignedForEvent").getValue(Boolean.class);
                    String nickname = snap.child("NickName").getValue(String.class);
                    String pass = snap.child("Password").getValue(String.class);
                    int score = snap.child("Score").getValue(Integer.class);
                    Double lati = snap.child("location").child("Latitude").getValue(Double.class);
                    Double longi = snap.child("location").child("Longitude").getValue(Double.class);

                    users.add(new User(name, isSigned, email, nickname, pass, score, new location(lati, longi)));
                    CurrentUser.keys.add(new UserKey(key, nickname));
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
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersTable = mRootRef.child("users").push();
            String pushId = usersTable.getKey();
            usersTable.setValue(new User(name, false, email, nickName, password, 0, new location(0, 0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pushNewEvent(String name, int slots, Participants participants, FinishedParticipants finishedparticipants, Double latitude, Double longitude) {
        try {
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersTable = mRootRef.child("events").push();
            String pushId = usersTable.getKey();
            usersTable.setValue(new EventCompetition(name, true, false, participants, finishedparticipants, slots, new location(latitude, longitude)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setEventParticipant(EventCompetition event,int number){
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mEvent;
        String key="";
        for (UserKey s:CurrentUser.eventkeys){
            if (event.Name.equals(s.NickName)){
                key = s.Key;
            }
        }

            mEvent = mRootRef.child("events").child(key).child("Participants").child("user"+number);
            mEvent.setValue(CurrentUser.getUser().NickName);
    }


    //neznam dali raboti
    public static User getUser(final String nickName1, final String email1) {
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        final User[] user = {null};
        mRootRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String name = snap.child("Name").getValue(String.class);
                    String email = snap.child("Email").getValue(String.class);
                    boolean isSigned = snap.child("isSignedForEvent").getValue(Boolean.class);
                    String nickname = snap.child("NickName").getValue(String.class);
                    String pass = snap.child("Password").getValue(String.class);
                    int score = snap.child("Score").getValue(Integer.class);
                    Double lati = snap.child("location").child("Latitude").getValue(Double.class);
                    Double longi = snap.child("location").child("Longitude").getValue(Double.class);
                    if (nickName1.equals(nickname) && email1.equals(email)) {
                        user[0] = new User(name, isSigned, email, nickname, pass, score, new location(lati, longi));
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

        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mUser;
        for (UserKey u : CurrentUser.keys
                ) {
            if (u.NickName.equals(nickName1)) {
                mUser = mRootRef.child("users").child(u.Key).child("location");
                Map<String, Object> taskMap = new HashMap<String, Object>();
                taskMap.put("Latitude", longitude);
                taskMap.put("Longitude", lalitude);
                mUser.updateChildren(taskMap);
            }
        }
    }

    private static String p(int number, DataSnapshot snap) {
        return snap.child("Participants").child("user" + number).getValue(String.class);
    }

    public static void getEvents(final List<EventCompetition> events) {

        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String name = snap.child("Name").getValue(String.class);
                    String key = snap.getKey();
                    boolean isactive = snap.child("isActive").getValue(Boolean.class);
                    Boolean isstarted = snap.child("isStarted").getValue(Boolean.class);
                    FinishedParticipants finishedparticipants = new FinishedParticipants("");
                    int slots = snap.child("Slots").getValue(int.class);
                    Participants participants;

                    switch (slots) {
                        case 1:
                            participants = new Participants(p(1, snap));
                            break;
                        case 2:
                            participants = new Participants(p(1, snap), p(2, snap));
                            break;
                        case 3:
                            participants = new Participants(p(1, snap), p(2, snap), p(3, snap));
                            break;
                        case 4:
                            participants = new Participants(p(1, snap), p(2, snap), p(3, snap), p(4, snap));
                            break;
                        default:
                            participants = new Participants(p(1, snap), p(2, snap), p(3, snap), p(4, snap), p(5, snap));
                            break;
                    }
                    Double lati = snap.child("location").child("Latitude").getValue(Double.class);
                    Double longi = snap.child("location").child("Longitude").getValue(Double.class);

                    events.add(new EventCompetition(name, isactive, isstarted, participants, finishedparticipants, slots, new location(lati, longi)));
                    CurrentUser.eventkeys.add(new UserKey(key, name));
                }
                if (isInitial) {
                    tempRecord = events.get(events.size() - 1);
                    isInitial = false;
                }
                else if(!isInitial){
                    tempRecord = events.get(events.size() - 2);
                    if (!tempRecord.Name.equals(events.get(events.size() - 1).Name)) {
                            isTrue = true;
                        }
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.example.owner.android2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by k_vol on 12/06/2017.
 */

public class FireBaseConnection {


    public static void LoadFromDB(final List<User> users) {
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
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
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersTable = mRootRef.child("users").push();
            String pushId = usersTable.getKey();
            usersTable.setValue(new User(name, email, nickName, password,0, new location(0, 0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}

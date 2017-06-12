package com.example.owner.android2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static boolean isfound = false;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private int userId;
    private EditText Name;
    private EditText NickName;
    private EditText Email;
    private EditText Password;
    private String name, email, password, nickName;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        users = new ArrayList<>();
        Name = (EditText) findViewById(R.id.NameText);
        Email = (EditText) findViewById(R.id.EmailText);
        Password = (EditText) findViewById(R.id.PasswordText);
        NickName = (EditText) findViewById(R.id.NickNameText);
        final Button registerButton = (Button) findViewById(R.id.RegisterButton);

        LoadFromDB();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerInDB(v);
            }
        });
    }

    private void registerInDB(View v) {
        name = Name.getText().toString();
        email = Email.getText().toString();
        password = Password.getText().toString();
        nickName = NickName.getText().toString();

        if (name.equals(null) || name.equals("")) {
            Name.setError("Pease fill in");
            return;
        }
        if (email.equals(null) || email.equals("")) {
            Email.setError("Pease fill in");
            return;
        }
        if (password.equals(null) || password.equals("")) {
            Password.setError("Pease fill in");
            return;
        }
        if (nickName.equals(null) || nickName.equals("")) {
            NickName.setError("Pease fill in");
            return;
        }
        if (isRegistered()) {
            return;
        } else {
            pushNewInstance(name, email, password, nickName);
            gotoProfile(v);
        }
    }

    public void gotoProfile(View view) {
        Intent innt = new Intent(this, ProfileActivity.class);
        startActivity(innt);
    }

    private void LoadFromDB() {

        mRootRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()
                        ) {
                    String name = snap.child("Name").getValue(String.class);
                    String email = snap.child("Email").getValue(String.class);
                    String nickname = snap.child("NickName").getValue(String.class);
                    String pass = snap.child("Password").getValue(String.class);
                    Double lati = snap.child("location").child("Latitude").getValue(Double.class);
                    Double longi = snap.child("location").child("Longitude").getValue(Double.class);

                    users.add(new User(name, email, nickname, pass, new location(lati, longi)));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private boolean isRegistered() {
        for (User user : users
                ) {
            if (user.Email.equals(email)) {
                Email.setError("Email already in use");
                return true;
            }
            if (user.NickName.equals(nickName)) {
                NickName.setError("Nick name already in use");
                return true;
            }
        }
        return false;
    }

    private void pushNewInstance(String name, String email, String password, String nickName) {
        try {
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersTable = mRootRef.child("users").push();
            String pushId = usersTable.getKey();
            usersTable.setValue(new User(name, email, nickName, password, new location(0, 0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
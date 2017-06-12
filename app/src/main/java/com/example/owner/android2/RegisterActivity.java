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

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    private EditText NameTextField;
    private EditText NickNameTextField;
    private EditText EmailTextField;
    private EditText PasswordTextField;

    private String name, email, password, nickName;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        users = new ArrayList<>();

        //assigning the Inputs
        NameTextField = (EditText) findViewById(R.id.NameText);
        EmailTextField = (EditText) findViewById(R.id.EmailText);
        PasswordTextField = (EditText) findViewById(R.id.PasswordText);
        NickNameTextField = (EditText) findViewById(R.id.NickNameText);

        final Button registerButton = (Button) findViewById(R.id.RegisterButton);

        //loading fireBaseDB
        LoadFromDB();

        //setting the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerInDB(v);
            }
        });
    }

    /**
     * handling the registration in the "Form"
     *
     * @param v passing it to gotoLogin method
     */
    private void registerInDB(View v) {

        name = NameTextField.getText().toString();
        email = EmailTextField.getText().toString();
        password = PasswordTextField.getText().toString();
        nickName = NickNameTextField.getText().toString();

        View focusView = null;
        View focusView2 = null;
        //check if name is filled in
        if (name.equals(null) || name.equals("")) {
            NameTextField.setError("Pease fill in");
            focusView = NameTextField;
            focusView.requestFocus();
            return;
        }
        //check if email is filled in
        if (email.equals(null) || email.equals("")) {
            EmailTextField.setError("Pease fill in");
            focusView = EmailTextField;
            focusView.requestFocus();
            return;
        }
        //check if nickName is filled in
        if (nickName.equals(null) || nickName.equals("")) {
            NickNameTextField.setError("Pease fill in");
            focusView = NickNameTextField;
            focusView.requestFocus();
            return;
        }
        //validates password and email
        if (!isPasswordValid(password) && !isEmailValid(email)) {
            PasswordTextField.setError(getString(R.string.error_invalid_password));
            focusView = PasswordTextField;
            focusView.requestFocus();
            EmailTextField.setError(getString(R.string.error_invalid_email));
            focusView2 = EmailTextField;
            focusView2.requestFocus();
            return;
        }
        //validates password
        if (!isPasswordValid(password)) {
            PasswordTextField.setError(getString(R.string.error_invalid_password));
            focusView = PasswordTextField;
            focusView.requestFocus();
            return;
        }
        //validates email
        if (!isEmailValid(email)) {
            EmailTextField.setError(getString(R.string.error_invalid_email));
            focusView = EmailTextField;
            focusView.requestFocus();
            return;
        }
        //checks if the "user" is already in the DB
        if (isRegistered()) {
            return;
        }
        //registers the User
        else {
            pushNewInstance(name, email, password, nickName);
            gotoLogin(v);
        }
    }

    /**
     * initi. login activity
     *
     * @param view
     */
    public void gotoLogin(View view) {
        Intent innt = new Intent(this, LoginActivity.class);
        startActivity(innt);
    }

    /**
     * Loading objects from the fireDB
     * and filling the users list
     */
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

    /**
     * loops through the list with users and checks if they are
     * present with eighter Email or nickName
     *
     * @return
     */
    private boolean isRegistered() {
        for (User user : users
                ) {
            if (user.Email.equals(email)) {
                EmailTextField.setError("Email already in use");
                return true;
            }
            if (user.NickName.equals(nickName)) {
                NickNameTextField.setError("Nick name already in use");
                return true;
            }
        }
        return false;
    }

    /**
     * pushing a new instance to the fireDB through the params:
     *
     * @param name
     * @param email
     * @param password
     * @param nickName
     */
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

    /**
     * validating the password input
     *
     * @param password if is more than 6 symbols
     * @return
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    /**
     * validating if the email contains "@" which proposes for a currect email
     *
     * @param email
     * @return
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
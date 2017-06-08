package com.example.owner.android2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Owner on 6/4/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.image);
        //        profilePictureView.setProfileId(profile.getId());
//        Bundle params = new Bundle();
//        params.putString("fields", "id,email,gender,cover,picture.type(large)");
//        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
//                new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        if (response != null) {
//                            try {
//                                JSONObject data = response.getJSONObject();
//                                if (data.has("picture")) {
//                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
//                                    Bitmap profilePic = BitmapFactory.decodeStream(profilePicUrl.openConnection().getInputStream());
//                                    mImageView.setImageBitmap(profilePic);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).executeAsync();
    }
}

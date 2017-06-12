package com.example.owner.android2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.login.widget.ProfilePictureView;

/**
 * Created by Owner on 6/4/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


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

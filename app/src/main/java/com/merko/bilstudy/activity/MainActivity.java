package com.merko.bilstudy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.merko.bilstudy.data.ProviderLocator;
import com.merko.bilstudy.media.ImageCategory;
import com.merko.bilstudy.media.ImageLocalProvider;
import com.merko.bilstudy.media.ImageProvider;
import com.merko.bilstudy.pomodoro.PomodoroProvider;
import com.merko.bilstudy.pomodoro.PomodoroRoomProvider;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileLocalProvider;
import com.merko.bilstudy.social.ProfileProvider;
import com.merko.bilstudy.utils.Globals;
import com.merko.bilstudy.R;

public class MainActivity extends AppCompatActivity {

    private static boolean firstActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Globals.setApplicationContext(getApplicationContext());
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ProviderLocator locator = ProviderLocator.getInstance();
        if(firstActivity) {
            locator.setProvider(ProfileProvider.class, new ProfileLocalProvider());
            locator.setProvider(PomodoroProvider.class, new PomodoroRoomProvider());
            locator.setProvider(ImageProvider.class, new ImageLocalProvider());
            firstActivity = false;
        }

        Profile userProfile = locator.getProvider(ProfileProvider.class).getUserProfile();
        String str = getApplicationContext().getString(R.string.welcome_message, userProfile.name);
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(str);
        Log.d(toString(), "Path: " + getApplicationContext().getFilesDir().getAbsolutePath());
        ImageView profileIcon = findViewById(R.id.profileIcon);
        Bitmap profileImage = locator.getProvider(ImageProvider.class)
                                        .getImages(ImageCategory.PROFILE, userProfile.imageUuid)[0];
        profileIcon.setImageBitmap(profileImage);
    }
}
package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    CardView profileButton;
    Button questionnaireButton;
    CardView pomodoroButton;
    CardView notepadButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileButton = findViewById(R.id.profileCard);

        pomodoroButton = findViewById(R.id.pomodoroCard);
        questionnaireButton = findViewById(R.id.questionnaireButton);
        notepadButton = findViewById(R.id.notepadCard);

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

        pomodoroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pomodoroHome = new Intent(MainActivity.this, PomodoroOptionsActivity.class);
                startActivity(pomodoroHome);
            }
        });

        questionnaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionnairePage = new Intent(MainActivity.this, QuestionnaireActivity.class);
                startActivity(questionnairePage);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profilePage);
            }
        });
        notepadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notepadPage = new Intent(MainActivity.this, PreviousNotesActivity.class);
                startActivity(notepadPage);
            }
        });


    }
}
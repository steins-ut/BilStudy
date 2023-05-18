package com.merko.bilstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.leitner.RoomLeitnerSource;
import com.merko.bilstudy.media.ImageCategory;
import com.merko.bilstudy.media.LocalImageSource;
import com.merko.bilstudy.media.ImageSource;
import com.merko.bilstudy.pomodoro.PomodoroPreset;
import com.merko.bilstudy.pomodoro.PomodoroSource;
import com.merko.bilstudy.pomodoro.RoomPomodoroSource;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.LocalProfileSource;
import com.merko.bilstudy.social.ProfileSource;
import com.merko.bilstudy.utils.Globals;

public class MainActivity extends AppCompatActivity {

    static {
        SourceLocator locator = SourceLocator.getInstance();
        locator.setPreferredType(ProfileSource.class, LocalProfileSource.class);
        locator.setPreferredType(PomodoroSource.class, RoomPomodoroSource.class);
        locator.setPreferredType(ImageSource.class, LocalImageSource.class);
        locator.setPreferredType(LeitnerSource.class, RoomLeitnerSource.class);
    }

    private void handleFirstRun() {
        SharedPreferences preferences = getSharedPreferences(Globals.PREFERENCES_NAME, MODE_PRIVATE);
        int oldVersionCode = preferences.getInt(Globals.PREFERENCES_VERSION_CODE_KEY, -1);
        if(oldVersionCode != BuildConfig.VERSION_CODE) {
            Log.d(toString(), "Running app for the first time.");
            PomodoroSource pomodoroProvider = SourceLocator.getInstance().getProvider(PomodoroSource.class);
            LoadingDialog dialog = new LoadingDialog(this);
            dialog.addFutures(pomodoroProvider.putPreset(new PomodoroPreset(null, "Classic Pomodoro", 25, 5)));
            dialog.addFutures(pomodoroProvider.putPreset(new PomodoroPreset(null, "Extended Pomodoro", 40, 10)));
            dialog.addFutures(pomodoroProvider.putPreset(new PomodoroPreset(null, "Extreme Pomodoro", 60, 20)));
            dialog.addFutures(pomodoroProvider.putPreset(new PomodoroPreset(null, "Bilkent Student", 120, 30)));
            dialog.show();
        }
        preferences.edit().putInt(Globals.PREFERENCES_VERSION_CODE_KEY, BuildConfig.VERSION_CODE).apply();
    }

    private void initialize() {
        ImageView profileIcon = findViewById(R.id.profileIcon);
        CardView profileCard = findViewById(R.id.profileCard);
        TextView welcomeText = findViewById(R.id.welcomeText);

        Button questionnaireButton = findViewById(R.id.questionnaireButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        CardView pomodoroCard = findViewById(R.id.pomodoroCard);
        CardView notepadCard = findViewById(R.id.notepadCard);

        SourceLocator locator = SourceLocator.getInstance();

        try {
            Profile userProfile = locator.getProvider(ProfileSource.class).getLoggedInProfile().get();
            String str = getString(R.string.welcome_message, userProfile.name);
            welcomeText.setText(str);
            Bitmap profileImage = locator.getProvider(ImageSource.class).getImage(ImageCategory.PROFILE, userProfile.imageUuid).get();
            profileIcon.setImageBitmap(profileImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        pomodoroCard.setOnClickListener((View view) -> {
            Intent pomodoroHome = new Intent(MainActivity.this, PomodoroOptionsActivity.class);
            startActivity(pomodoroHome);
        });

        questionnaireButton.setOnClickListener((View view) -> {
            Intent questionnairePage = new Intent(MainActivity.this, QuestionnaireActivity.class);
            startActivity(questionnairePage);
        });

        profileCard.setOnClickListener((View view) -> {
            Intent profilePage = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(profilePage);
        });

        notepadCard.setOnClickListener((View view) -> {
            Intent notepadPage = new Intent(MainActivity.this, PreviousNotesActivity.class);
            startActivity(notepadPage);
        });

        settingsButton.setOnClickListener((View view) -> {
            Intent settingsPage = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsPage);
        });

        locator.getProvider(PomodoroSource.class).getAllPresets().thenAccept((PomodoroPreset[] presets) -> {
            for(PomodoroPreset p: presets) {
                Log.d(toString(), p.name);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Globals.setApplicationContext(getApplicationContext());

        handleFirstRun();
        initialize();
    }
}
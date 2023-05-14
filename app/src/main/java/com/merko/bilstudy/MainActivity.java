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

import com.merko.bilstudy.data.ProviderLocator;
import com.merko.bilstudy.media.ImageCategory;
import com.merko.bilstudy.media.LocalImageProvider;
import com.merko.bilstudy.media.ImageProvider;
import com.merko.bilstudy.pomodoro.PomodoroPreset;
import com.merko.bilstudy.pomodoro.PomodoroProvider;
import com.merko.bilstudy.pomodoro.RoomPomodoroProvider;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.LocalProfileProvider;
import com.merko.bilstudy.social.ProfileProvider;
import com.merko.bilstudy.utils.Globals;

public class MainActivity extends AppCompatActivity {

    private ImageView profileIcon;
    private CardView profileCard;
    private TextView welcomeText;

    private Button questionnaireButton;

    private CardView pomodoroCard;
    private CardView notepadCard;

    static {
        ProviderLocator locator = ProviderLocator.getInstance();
        locator.setPreferredType(ProfileProvider.class, LocalProfileProvider.class);
        locator.setPreferredType(PomodoroProvider.class, RoomPomodoroProvider.class);
        locator.setPreferredType(ImageProvider.class, LocalImageProvider.class);
    }

    private void handleFirstRun() {
        SharedPreferences preferences = getSharedPreferences(Globals.PREFERENCES_NAME, MODE_PRIVATE);
        int oldVersionCode = preferences.getInt(Globals.PREFERENCES_VERSION_CODE_KEY, -1);
        if(oldVersionCode != BuildConfig.VERSION_CODE) {
            Log.d(toString(), "Running app for the first time.");
            PomodoroProvider pomodoroProvider = ProviderLocator.getInstance().getProvider(PomodoroProvider.class);
            pomodoroProvider.putPreset(new PomodoroPreset(null, "Classic Pomodoro", 25, 5));
            pomodoroProvider.putPreset(new PomodoroPreset(null, "Extended Pomodoro", 40, 10));
            pomodoroProvider.putPreset(new PomodoroPreset(null, "Extreme Pomodoro", 60, 20));
            pomodoroProvider.putPreset(new PomodoroPreset(null, "Bilkent Student", 120, 30));
        }
        preferences.edit().putInt(Globals.PREFERENCES_VERSION_CODE_KEY, BuildConfig.VERSION_CODE).apply();
    }

    private void initialize() {
        profileIcon = findViewById(R.id.profileIcon);
        profileCard = findViewById(R.id.profileCard);
        welcomeText = findViewById(R.id.welcomeText);

        questionnaireButton = findViewById(R.id.questionnaireButton);

        pomodoroCard = findViewById(R.id.pomodoroCard);
        notepadCard = findViewById(R.id.notepadCard);

        ProviderLocator locator = ProviderLocator.getInstance();
        Profile userProfile = locator.getProvider(ProfileProvider.class).getProfile(null);
        String str = getString(R.string.welcome_message, userProfile.name);
        welcomeText.setText(str);
        Bitmap profileImage = locator.getProvider(ImageProvider.class).getImage(ImageCategory.PROFILE, userProfile.imageUuid);
        profileIcon.setImageBitmap(profileImage);

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
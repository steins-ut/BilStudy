package com.merko.bilstudy;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.splashscreen.SplashScreen;

import com.merko.bilstudy.data.SourceLocator;
import com.merko.bilstudy.dialog.LoadingDialog;
import com.merko.bilstudy.leitner.LeitnerSource;
import com.merko.bilstudy.leitner.RoomLeitnerSource;
import com.merko.bilstudy.media.ImageCategory;
import com.merko.bilstudy.media.ImageSource;
import com.merko.bilstudy.media.LocalImageSource;
import com.merko.bilstudy.notepad.NotepadSource;
import com.merko.bilstudy.notepad.RoomNotepadSource;
import com.merko.bilstudy.pomodoro.PomodoroPreset;
import com.merko.bilstudy.pomodoro.PomodoroSource;
import com.merko.bilstudy.pomodoro.RoomPomodoroSource;
import com.merko.bilstudy.social.LocalProfileSource;
import com.merko.bilstudy.social.Profile;
import com.merko.bilstudy.social.ProfileSource;
import com.merko.bilstudy.utils.BilStudyBroadCast;
import com.merko.bilstudy.utils.Globals;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    protected static Boolean notifications = true;

    static {
        SourceLocator locator = SourceLocator.getInstance();
        locator.setPreferredType(ProfileSource.class, LocalProfileSource.class);
        locator.setPreferredType(PomodoroSource.class, RoomPomodoroSource.class);
        locator.setPreferredType(ImageSource.class, LocalImageSource.class);
        locator.setPreferredType(LeitnerSource.class, RoomLeitnerSource.class);
        locator.setPreferredType(NotepadSource.class, RoomNotepadSource.class);
    }

    private void handleFirstRun() {
        SharedPreferences preferences = getSharedPreferences(Globals.PREFERENCES_NAME, MODE_PRIVATE);
        int oldVersionCode = preferences.getInt(Globals.PREFERENCES_VERSION_CODE_KEY, -1);
        if(oldVersionCode != BuildConfig.VERSION_CODE) {
            Log.d(toString(), "Running app for the first time.");
            PomodoroSource pomodoroProvider = SourceLocator.getInstance().getSource(PomodoroSource.class);
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
        Button shopButton = findViewById(R.id.shopButton);
        CardView pomodoroCard = findViewById(R.id.pomodoroCard);
        CardView notepadCard = findViewById(R.id.notepadCard);
        CardView leitnerCard = findViewById(R.id.leitnerCard);

        SourceLocator locator = SourceLocator.getInstance();

        Profile userProfile = locator.getSource(ProfileSource.class).getLoggedInProfile().join();
        String str = getString(R.string.welcome_message, userProfile.name);
        welcomeText.setText(str);
        Bitmap profileImage = locator.getSource(ImageSource.class).getImage(ImageCategory.PROFILE, userProfile.imageUuid).join();
        profileIcon.setImageBitmap(profileImage);

        leitnerCard.setOnClickListener((View view) -> {
            Intent leitnerHomeIntent = new Intent(MainActivity.this, LeitnerHomeActivity.class);
            startActivity(leitnerHomeIntent);
        });

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
            Intent notepadPage = new Intent(MainActivity.this, ChooseTemplateActivity.class);
            startActivity(notepadPage);
        });

        settingsButton.setOnClickListener((View view) -> {
            Intent settingsPage = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsPage);
        });

        shopButton.setOnClickListener((View view) -> {
            Intent shopPage = new Intent(MainActivity.this, ShopActivity.class);
            startActivity(shopPage);
        });


        locator.getSource(PomodoroSource.class).getAllPresets().thenAccept((PomodoroPreset[] presets) -> {
            for(PomodoroPreset p: presets) {
                Log.d(toString(), p.name);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            if(notifications) {
                channelNotification();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,16);
                calendar.set(Calendar.MINUTE,57);
                calendar.set(Calendar.SECOND,0);
                if(Calendar.getInstance().after(calendar)) {
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                }
                Intent i = new Intent(MainActivity.this, BilStudyBroadCast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,i,PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }

        Globals.setApplicationContext(getApplicationContext());

        handleFirstRun();
        initialize();
    }

    private void channelNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            CharSequence name = "BilStudy";
            String message = "BilStudy's Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification",name,importance);
            channel.setDescription(message);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
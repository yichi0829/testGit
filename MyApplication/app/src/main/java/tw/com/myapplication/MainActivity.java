package tw.com.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.provider.Settings;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NOTI_PRIMARY1 = 1100;
    private static final int NOTI_PRIMARY2 = 1101;
    private static final int NOTI_SECONDARY1 = 1200;
    private static final int NOTI_SECONDARY2 = 1201;
    private Timer mTimer = new Timer();


    /*
     * A
     */
    private NotificationHelper noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noti = new NotificationHelper(this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //sendNotification(NOTI_PRIMARY1, "getTitlePrimaryText()");

                MainActivity.this.enterPictureInPictureMode();
//                boolean supportsPIP = getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
//                if (supportsPIP) {
//                    Log.e("test","supportsPIP true");
//                } else {
//                    Log.e("test","supportsPIP false");
//                }
//                String uriString = "http://maps.google.com/maps?saddr=25.065744,121.5335589&daddr=25.0658678,121.5334937";
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
//                intent.setPackage("com.google.android.apps.maps");
//                getApplicationContext().startActivity(intent);


//                Uri gmmIntentUri = Uri.parse("geo:0,0?q=25.065744,121.5335589&q=25.0658678,121.5334937");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                getApplicationContext().startActivity(mapIntent);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimerTask();
            }
        });
    }

    private int timerCount = 0;

    private void setTimerTask() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerCount++;
                Log.e("test","Timer: "+timerCount);
                if(timerCount % 6 == 0){
//                    Intent startIntent = new Intent(MainActivity.this, MainActivity.class);
//                    startIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); //FLAG_ACTIVITY_NEW_TASK FLAG_ACTIVITY_REORDER_TO_FRONT
//                    startActivity(startIntent);

//                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // You need this if starting
//                    //  the activity from a service
//                    intent.setAction(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                    startActivity(intent);

//                    ActivityManager activtyManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//                    List<ActivityManager.RunningTaskInfo> runningTaskInfos = activtyManager.getRunningTasks(3);
//                    for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos)
//                    {
//                        if (MainActivity.this.getPackageName().equals(runningTaskInfo.topActivity.getPackageName()))
//                        {
//                            activtyManager.moveTaskToFront(runningTaskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
//                            return;
//                        }
//                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);

                }
            }
        }, 0, 1000);
    }

    @Override
    public void onPictureInPictureModeChanged (boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            Log.e("test","onPictureInPictureModeChanged true");
        } else {
            Log.e("test","onPictureInPictureModeChanged false");
        }


    }

    /**
     * Send activity notifications.
     *
     * @param id The ID of the notification to create
     * @param title The title of the notification
     */
    public void sendNotification(int id, String title) {
        Notification.Builder nb = null;
        switch (id) {
            case NOTI_PRIMARY1:
                nb = noti.getNotification1(title, getString(R.string.primary1_body));
                break;

            case NOTI_PRIMARY2:
                nb = noti.getNotification1(title, getString(R.string.primary2_body));
                break;

            case NOTI_SECONDARY1:
                nb = noti.getNotification2(title, getString(R.string.secondary1_body));
                break;

            case NOTI_SECONDARY2:
                nb = noti.getNotification2(title, getString(R.string.secondary2_body));
                break;
        }
        if (nb != null) {
            noti.notify(id, nb);
        }
    }

    /**
     * Send Intent to load system Notification Settings for this app.
     */
    public void goToNotificationSettings() {
        Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(i);
    }

    /**
     * Send intent to load system Notification Settings UI for a particular channel.
     *
     * @param channel Name of channel to configure
     */
    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

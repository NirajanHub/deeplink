package com.bjm.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.facebook.applinks.AppLinkData;
import com.onesignal.OneSignal;

public class GlobalApplication extends Application {
    static final String CHANNEL_1_ID = "channel1";

    private static Context context;

    public GlobalApplication() {
        context = this;
    }
    public static Context getContext(){
       return  context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        String appToken = "{YourAppToken}";
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        Adjust.onCreate(config);
        createNotificationChannels();

                OneSignal.startInit(this)
                        .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                        .unsubscribeWhenNotificationsAreDisabled(true)
                        .init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel_1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setImportance(NotificationManager.IMPORTANCE_HIGH);
            /* channel1.setBypassDnd(true);*/
            channel1.setLightColor(Color.argb(0, 128, 0, 128));
            // channel1.setVibrationPattern(new long[] { 1000, 1000, 1000, 1000, 1000 });
            channel1.setDescription("this is channel 1");


            NotificationManager manager = getSystemService(NotificationManager.class);
            //  manager.createNotificationChannel(defaultchannel);
            manager.createNotificationChannel(channel1);


          /*  NotificationManager default1= (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
            default1.createNotificationChannel(channel1);*/

        }
    }
}

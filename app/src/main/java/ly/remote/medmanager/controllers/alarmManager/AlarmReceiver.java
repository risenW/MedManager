package ly.remote.medmanager.controllers.alarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ly.remote.medmanager.views.ShowNotificationActivity;

/**
 * Created by Risen on 4/4/2018.
 */

public class AlarmReceiver extends BroadcastReceiver{

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

//        if (intent.getAction() != null && context != null) {
//            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
//                // Set the alarm here.
//                Log.d(TAG, "onReceive: BOOT_COMPLETED");
////                NotificationScheduler.setReminder(context, AlarmReceiver.class, localData.get_hour(), localData.get_min());
//                return;
//            }
//        }

        Log.d(TAG, "onReceive: ");

        //Trigger the notification
        NotificationScheduler.showNotification(context, ShowNotificationActivity.class,
                "You have 5 unwatched videos", "Watch them now?");

    }
}

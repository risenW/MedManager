package ly.remote.medmanager.controllers.alarmManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import ly.remote.medmanager.views.ShowNotificationActivity;

/**
 * Created by Risen on 4/3/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent repeating_intent = new Intent(context, ShowNotificationActivity.class);
//        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, repeating_intent,0);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setContentIntent(pendingIntent)
//                .setContentTitle("Hello there")
//                .setContentText("Take your med now")
//                .setAutoCancel(true);
//
//        notificationManager.notify(100,builder.build());
        Toast.makeText(context, "ALarm received", Toast.LENGTH_SHORT).show();


    }
}

package ly.remote.medmanager.controllers.alarmManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;


import ly.remote.medmanager.views.ShowNotificationActivity;

/**
 * Created by Risen on 4/3/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static int NOTIFICATION_ID = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent repeating_intent = new Intent(context, ShowNotificationActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sendNotification("Title", "Hello there", repeating_intent,23, context);

    }

    private void sendNotification(String title, String message,Intent intent, int pushId, Context context) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (intent != null){
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, pushId,intent,PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}

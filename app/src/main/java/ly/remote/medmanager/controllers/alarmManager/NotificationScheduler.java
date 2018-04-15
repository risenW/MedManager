package ly.remote.medmanager.controllers.alarmManager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;

import ly.remote.medmanager.R;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Risen on 4/4/2018.
 */

public class NotificationScheduler {

    public static final String MED_ID_EXTRA_KEY = "MedicationIdExtraKey";
    public static final String MED_TITLE_EXTRA_KEY = "MedicationTitle";
    public static final String MED_DESCRIPTION_KEY = "MedicationDescription";
    public static final String MED_HOUR_KEY = "Med_hour";

    public static void setReminder(Context context,Class<?> cls,int pendingRequestID,int hour, int min,int interval) {
        Calendar setCalendar = Calendar.getInstance();
        setCalendar.set(Calendar.HOUR_OF_DAY, hour);
        setCalendar.set(Calendar.MINUTE, min);
        setCalendar.set(Calendar.SECOND, 0);

//        // Enable a receiver
//        ComponentName receiver = new ComponentName(context, cls);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

        Intent intent = new Intent(context, cls);
        intent.putExtra(MED_ID_EXTRA_KEY,pendingRequestID);   //The request ID is the medication row ID in database.
        intent.putExtra(MED_HOUR_KEY, hour);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pendingRequestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, setCalendar.getTimeInMillis(),interval, pendingIntent);

    }

    public static void cancelReminder(Context context,Class<?> cls, int pendingRequestID) {
//         Disable a receiver
//
//        ComponentName receiver = new ComponentName(context, cls);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);

        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pendingRequestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static void showNotification(Context context, Class<?> cls, String title,int pendingId, String content) {

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.putExtra(MED_TITLE_EXTRA_KEY,title);
        notificationIntent.putExtra(MED_DESCRIPTION_KEY,content);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(pendingId, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("It's time to take your medication")
                .setContentText(title)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(pendingId, notification);

    }
}

package ly.remote.medmanager.controllers.alarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;

import ly.remote.medmanager.controllers.DatabaseHelper;
import ly.remote.medmanager.views.ShowNotificationActivity;

/**
 * Created by Risen on 4/4/2018.
 */

public class AlarmReceiver extends BroadcastReceiver{
    DatabaseHelper databaseHelper;
    String TAG = "AlarmReceiver";
    private String med_title, med_desc;

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
        Bundle extras = intent.getExtras();
        int med_id = extras.getInt(NotificationScheduler.MED_ID_EXTRA_KEY);
        databaseHelper = new DatabaseHelper(context);

        //Get the medication details from the database and pass it to the notification scheduler
        try {
            databaseHelper.open();
            Cursor cursor = databaseHelper.getMedicationById(med_id);
            if (cursor != null){
                cursor.moveToFirst();
                med_title = cursor.getString(1);
                med_desc = cursor.getString(2);
                cursor.close();
            }
            databaseHelper.close();

        }catch (SQLException e){
            e.printStackTrace();
        }


        //Trigger the notification
        NotificationScheduler.showNotification(context, ShowNotificationActivity.class,med_title, med_id,med_desc);

    }
}

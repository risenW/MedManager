package ly.remote.medmanager.controllers.alarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import ly.remote.medmanager.controllers.helperClasses.DatabaseHelper;
import ly.remote.medmanager.models.AlarmModel;
import ly.remote.medmanager.models.LocalData;

/**
 * Created by Risen on 4/17/2018.
 */

public class OnAlarmRebootReceiver extends BroadcastReceiver {
    DatabaseHelper databaseHelper;
    AlarmModel alarmModel;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseHelper = new DatabaseHelper(context);
//        Reset Alarms on BOOT receive
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
          try {
              databaseHelper.open();
              Cursor cursor = databaseHelper.getAlarms();
              if (cursor != null){
                  do {
                      alarmModel = new AlarmModel(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2));
                      //Resets alarm
                      NotificationScheduler.setReminder(context,AlarmReceiver.class,alarmModel.getPendingRequestId(),alarmModel.getHour(), LocalData.DEFAULT_MIN, alarmModel.getInterval());
                  }while (cursor.moveToNext());
              }

          }catch (Exception e){
              e.printStackTrace();
          }
        }
    }
}

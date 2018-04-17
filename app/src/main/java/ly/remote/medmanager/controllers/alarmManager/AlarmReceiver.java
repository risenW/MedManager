package ly.remote.medmanager.controllers.alarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import ly.remote.medmanager.controllers.DatabaseHelper;
import ly.remote.medmanager.models.AlarmModel;
import ly.remote.medmanager.models.LocalData;
import ly.remote.medmanager.views.ShowNotificationActivity;

/**
 * Created by Risen on 4/4/2018.
 */

public class AlarmReceiver extends BroadcastReceiver{
    DatabaseHelper databaseHelper;
    private String med_title, med_desc;
    ArrayList<AlarmModel>  alarmModelArrayList;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        int med_id = extras.getInt(NotificationScheduler.MED_ID_EXTRA_KEY);
        databaseHelper = new DatabaseHelper(context);
        AlarmModel alarmModel;
        alarmModelArrayList = new ArrayList<>();

        //Get the medication details from the database and pass it to the notification scheduler
//        try {
//            databaseHelper.open();
//            Cursor cursor = databaseHelper.getMedicationById(med_id);
//            if (cursor != null){
//                cursor.moveToFirst();
//                med_title = cursor.getString(1);
//                med_desc = cursor.getString(2);
//                cursor.close();
//            }
//            databaseHelper.close();
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
        try {
            databaseHelper.open();
            Cursor cursor = databaseHelper.getAlarms();
            if (cursor != null){
                do {

                    alarmModel = new AlarmModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
//                    alarmModelArrayList.add(alarmModel);
                }while (cursor.moveToNext());
                NotificationScheduler.showNotification(context, ShowNotificationActivity.class,alarmModel.getHour() + " ", med_id,alarmModel.getPendingRequestId() + "");
            }
            databaseHelper.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        //Trigger the notification

    }
}

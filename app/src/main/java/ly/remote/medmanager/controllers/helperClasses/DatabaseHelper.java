package ly.remote.medmanager.controllers.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import ly.remote.medmanager.controllers.dbContractClasses.AlarmDbContract;
import ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract;
import ly.remote.medmanager.views.CreateMedicationActivity;

import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.CREATE_QUERY;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.INDEX;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_DEBUG_TAG;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_DESC;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_DOSAGE;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_END_DATE;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_MONTH;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_NAME;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_REMINDER;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_START_DATE;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.MED_START_TIME;
import static ly.remote.medmanager.controllers.dbContractClasses.MedDatabaseContract.TABLE_NAME;

/**
 * Created by Risen on 3/30/2018.
 * Database helper methods goes here
 */

public class DatabaseHelper {

    private SQLiteDatabase sqLiteDatabase;
    private MedDatabaseHelper medDatabaseHelper;
    private Context context;

    public DatabaseHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException{
        medDatabaseHelper = new MedDatabaseHelper(context);
        sqLiteDatabase = medDatabaseHelper.getReadableDatabase();
    }

    public void close(){
        if (medDatabaseHelper != null) {
            medDatabaseHelper.close();
        }
    }

    //Inner SQLite class for Medication database
    private static class MedDatabaseHelper extends SQLiteOpenHelper{
        //Constructor
        private MedDatabaseHelper(Context context) {
            super(context, MedDatabaseContract.DB_NAME, null, MedDatabaseContract.DB_VERSION);
            Log.d(MedDatabaseContract.MED_DEBUG_TAG, "database created...");

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(MedDatabaseContract.MED_DEBUG_TAG, CREATE_QUERY);
            Log.d(MedDatabaseContract.MED_DEBUG_TAG, AlarmDbContract.ALARM_DB_CREATE_QUERY);
            db.execSQL(CREATE_QUERY);
            db.execSQL(AlarmDbContract.ALARM_DB_CREATE_QUERY);
            Log.d(MedDatabaseContract.MED_DEBUG_TAG, "Tables created...");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(MedDatabaseContract.MED_DROP_QUERY);
            db.execSQL(AlarmDbContract.ALARM_DB_DROP_QUERY);
            Log.d(MedDatabaseContract.MED_DEBUG_TAG, "database dropped...");
            onCreate(db);
        }
    }

    public void saveMedication(int index,
                               String Med_name,
                               String Med_desc,
                               String Med_month,
                               String Med_dosage,
                               String Med_start_date,
                               String Med_end_date,
                               int Med_reminder,
                               int Med_start_time){

        sqLiteDatabase = medDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INDEX, index);
        values.put(MED_NAME, Med_name);
        values.put(MED_DESC, Med_desc);
        values.put(MED_MONTH, Med_month);
        values.put(MED_DOSAGE, Med_dosage);
        values.put(MED_START_DATE,Med_start_date );
        values.put(MED_END_DATE, Med_end_date);
        values.put(MED_REMINDER, Med_reminder);
        values.put(MED_START_TIME, Med_start_time);

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        Log.d(MedDatabaseContract.MED_DEBUG_TAG, "One Row Inserted");
    }

    public Cursor getMedication() {
        sqLiteDatabase = medDatabaseHelper.getReadableDatabase();
        String[] columns = {INDEX,MED_NAME,MED_DESC,MED_MONTH, MED_DOSAGE,MED_START_DATE,MED_END_DATE,MED_REMINDER,MED_START_TIME};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getMedicationById(int index){
        Cursor cursor = null;
        sqLiteDatabase = medDatabaseHelper.getReadableDatabase();
        String[] columns = {INDEX,MED_NAME,MED_DESC,MED_MONTH, MED_DOSAGE,MED_START_DATE,MED_END_DATE,MED_REMINDER,MED_START_TIME};
        cursor = sqLiteDatabase.query(TABLE_NAME,columns,INDEX + "=?",new String[]{String.valueOf(index)},null,null,null,null);
        return cursor;
    }

    public void updateMedById(
            int index,
            String Med_name,
            String Med_desc,
            String Med_month,
            String Med_interval,
            String Med_start_date,
            String Med_end_date,
            int Med_reminder,
            int Med_start_time
    ){

        sqLiteDatabase = medDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MED_NAME, Med_name);
        values.put(MED_DESC, Med_desc);
        values.put(MED_MONTH, Med_month);
        values.put(MED_DOSAGE, Med_interval);
        values.put(MED_START_DATE,Med_start_date );
        values.put(MED_END_DATE, Med_end_date);
        values.put(MED_REMINDER, Med_reminder);
        values.put(MED_START_TIME, Med_start_time);

        sqLiteDatabase.update(TABLE_NAME,values,INDEX + "=?",new String[]{String.valueOf(index)});
        Log.d(MedDatabaseContract.MED_DEBUG_TAG, "Updated Successfully");

    }

    public void deleteMedicationByID(int index){
        sqLiteDatabase = medDatabaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,INDEX + "=?",new String[]{String.valueOf(index)});
        Log.d("DBHELPER","Deletion successful");

    }



    public void saveAlarm(int pendingIntentId, int alarmHour, int alarmInterval){

        sqLiteDatabase = medDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AlarmDbContract.PENDING_INTENT_ID,pendingIntentId);
        values.put(AlarmDbContract.ALARM_HOUR,alarmHour);
        values.put(AlarmDbContract.ALARM_INTERVAL,alarmInterval);

        sqLiteDatabase.insert(AlarmDbContract.TABLE_NAME, null, values);
        Log.d(MedDatabaseContract.MED_DEBUG_TAG, "One Row Inserted in Alarm Db");
    }

    public Cursor getAlarms() {
        sqLiteDatabase = medDatabaseHelper.getReadableDatabase();
        String[] columns = {AlarmDbContract.PENDING_INTENT_ID, AlarmDbContract.ALARM_HOUR, AlarmDbContract.ALARM_INTERVAL};
        Cursor cursor = sqLiteDatabase.query(AlarmDbContract.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void deleteAlarmByID(int pendingIntentId){
        sqLiteDatabase = medDatabaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(AlarmDbContract.TABLE_NAME,INDEX + "=?",new String[]{String.valueOf(pendingIntentId)});
        Toast.makeText(context, "Deleted Alarm", Toast.LENGTH_SHORT).show();
    }

}

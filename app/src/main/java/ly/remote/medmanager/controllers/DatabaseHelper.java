package ly.remote.medmanager.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Risen on 3/30/2018.
 */

public class DatabaseHelper {
    private static final String DB_NAME = "MedicationDatabase";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "MedicationTable";
    private static final String INDEX = "id";
    private static final String MED_NAME = "MedicationName";
    private static final String MED_DESC = "MedicationDescription";
    private static final String MED_START_DATE = "MedStartDate";
    private static final String MED_END_DATE= "MedEndDate";
    private static final String MED_MONTH = "MedMonth";
    private static final String MED_INTERVAL = "MedInterval";
    private static final String MED_REMINDER = "MedReminder";    // One signifies that Reminder is activated and zero signifies de-activated
    private SQLiteDatabase sqLiteDatabase;
    private MedDatabaseHelper medDatabaseHelper;
    private Context context;


    private static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "( "
            + INDEX + " INTEGER, "
            + MED_NAME + " TEXT, "
            + MED_DESC + " TEXT, "
            + MED_MONTH + " TEXT, "
            + MED_INTERVAL + " INTEGER, "
            + MED_START_DATE + " INTEGER, "
            + MED_END_DATE + " INTEGER, "
            + MED_REMINDER + " INTEGER);";

    private static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME + ";";
    private static final String DEBUG_TAG = "Database Debug";

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
        public MedDatabaseHelper(Context context) {
            super(context,DB_NAME, null, DB_VERSION);
            Log.d(DEBUG_TAG, "database created...");

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(DEBUG_TAG, CREATE_QUERY);
            db.execSQL(CREATE_QUERY);
            Log.d(DEBUG_TAG, "Table created...");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_QUERY);
            Log.d(DEBUG_TAG, "database dropped...");
            onCreate(db);
        }
    }

    public void insertMedication(int index,
                                 String Med_name,
                                 String Med_desc,
                                 String Med_month,
                                 String Med_interval,
                                 String Med_start_date,
                                 String Med_end_date,
                                 int Med_reminder){

        sqLiteDatabase = medDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INDEX, index);
        values.put(MED_NAME, Med_name);
        values.put(MED_DESC, Med_desc);
        values.put(MED_MONTH, Med_month);
        values.put(MED_INTERVAL, Med_interval);
        values.put(MED_START_DATE,Med_start_date );
        values.put(MED_END_DATE, Med_end_date);
        values.put(MED_REMINDER, Med_reminder);

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        Log.d(DEBUG_TAG, "One Row Inserted");
    }

    public Cursor getMedication() {
        sqLiteDatabase = medDatabaseHelper.getReadableDatabase();
        String[] columns = {INDEX,MED_NAME,MED_DESC,MED_MONTH, MED_INTERVAL,MED_START_DATE,MED_END_DATE,MED_REMINDER};  //TODO change interval to integer
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void deleteMedicationByID(int index){
        sqLiteDatabase = medDatabaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,INDEX + "=?",new String[]{String.valueOf(index)});
        Log.d("DBHELPER","Deletion successful");

    }

}

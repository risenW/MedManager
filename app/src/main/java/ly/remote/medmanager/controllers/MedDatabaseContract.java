package ly.remote.medmanager.controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Risen on 4/15/2018.
 */

public class MedDatabaseContract {
    public static final String DB_NAME = "MedicationDatabase";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "MedicationTable";
    public static final String INDEX = "id";
    public static final String MED_NAME = "MedicationName";
    public static final String MED_DESC = "MedicationDescription";
    public static final String MED_START_TIME = "MedStartTime";
    public static final String MED_START_DATE = "MedStartDate";
    public static final String MED_END_DATE= "MedEndDate";
    public static final String MED_MONTH = "MedMonth";
    public static final String MED_DOSAGE = "MedInterval";
    public static final String MED_REMINDER = "MedReminder";    // One signifies that Reminder is activated and zero signifies de-activated


    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "( "
            + INDEX + " INTEGER, "
            + MED_NAME + " TEXT, "
            + MED_DESC + " TEXT, "
            + MED_MONTH + " TEXT, "
            + MED_DOSAGE + " TEXT, "
            + MED_START_DATE + " TEXT, "
            + MED_END_DATE + " TEXT, "
            + MED_REMINDER + " INTEGER, "
            + MED_START_TIME + " INTEGER);";

    public static final String MED_DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME + ";";
    public static final String MED_DEBUG_TAG = "Database Debug";
}

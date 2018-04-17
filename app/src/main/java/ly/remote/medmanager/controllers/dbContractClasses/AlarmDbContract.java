package ly.remote.medmanager.controllers.dbContractClasses;

/**
 * Created by Risen on 4/15/2018.
 */

public class AlarmDbContract {

    public static final String TABLE_NAME = "AlarmTable";
    public  static final String PENDING_INTENT_ID = "pendingIntentId";
    public  static final String ALARM_HOUR= "AlarmHour";
    public  static final String ALARM_INTERVAL = "AlarmInterval";


    public  static final String ALARM_DB_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "( "
            + PENDING_INTENT_ID + " INTEGER, "
            + ALARM_HOUR + " INTEGER, "
            + ALARM_INTERVAL + " INTEGER);";

    public  static final String ALARM_DB_DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME + ";";

}

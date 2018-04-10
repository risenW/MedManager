package ly.remote.medmanager.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by Risen on 4/8/2018.
 * A class to set and get User preferences and Local App data
 */

public class LocalData {
    private SharedPreferences appSharedPreferences;
    private  SharedPreferences.Editor prefsEditor;
    private static final String APP_SHARED_PREFS = "MedManagerPrefs";
    public static final int DEFAULT_MIN = 0;
    private final String INDEX_VALUE = "indexValue";   //Key for saving recyclerList items in preference


    public LocalData(Context context) {
        this.appSharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPreferences.edit();
    }

    public int getSavedIndexFromPref(){
        return appSharedPreferences.getInt(INDEX_VALUE,0);  //Default is 0, So that the index starts counting from one when it is a new user
    }

    public void saveIndexInPref(int index){
        prefsEditor.putInt(INDEX_VALUE,index);
        prefsEditor.apply();
        Log.d("Saved Index", "" + index);
    }
}

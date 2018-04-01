package ly.remote.medmanager.controllers;

import ly.remote.medmanager.R;

/**
 * Created by Risen on 4/1/2018.
 */

public class NewMedCreationHelper {
    // a class that encapsulate some majorly used functions

    public int getBooleanValue(String remind_me){
        if (remind_me.equals(R.string.Yes)){
            return 1;
        }else {
            return 0;
        }

    }
}

package ly.remote.medmanager.controllers;

import ly.remote.medmanager.R;

/**
 * Created by Risen on 4/1/2018.
 */

// A class that encapsulate some majorly used functions

public class NewMedCreationHelper {

    public int getBooleanValue(String remind_me){
        if (remind_me.equals("Yes")){
            return 1;
        }else {
            return 0;
        }

    }

    public String constructDateFromNumbers(int mDay, int mMonth, int mYear){
        return getMonth(mMonth) + " " + mDay + " " + mYear;
    }

    public String getMonth(int month){

        String selected_month;
        switch (month){
            case 0:
                selected_month = "Jan";
                break;
            case 1:
                selected_month = "Feb";
                break;
            case 2:
                selected_month = "March";
                break;
            case 3:
                selected_month = "April";
                break;
            case 4:
                selected_month = "May";
                break;
            case 5:
                selected_month = "June";
                break;
            case 6:
                selected_month = "July";
                break;
            case 7:
                selected_month = "Aug";
                break;
            case 8:
                selected_month = "Sept";
                break;
            case 9:
                selected_month = "Oct";
                break;
            case 10:
                selected_month = "Nov";
                break;
            case 11:
                selected_month = "Dec";
                break;
            default:
                selected_month = "Jan";

        }
        return selected_month;
    }

    public int get_selected_reminder_spinner_item(String option){
        if (option.equals("Yes")){
            return 1;
        }else {
            return 0;
        }
    }

    public int get_selected_interval_spinner_item(String option){
        int temp;
        switch (option){
            case "4":
                temp = 0;
                break;
            case "6":
                temp = 1;
                break;
            case "8":
                temp = 2;
                break;
            case "12":
                temp = 3;
                break;
            case "15":
                temp = 4;
                break;
            case "24":
                temp = 5;
                break;
            default:
                temp = 0;

        }
        return temp;
    }
}

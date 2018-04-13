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

    public int getIntervalFromDosage(int dosage){
        int tempDosage = dosage;
        switch (tempDosage){
            case 0:
                tempDosage = 86400000;  // 1x daily. Alarm interval is once every 24 hours coverted to seconds
                break;
            case 1:
                tempDosage =  43200000;  // 2x daily. Alarm interval is once every 12 hours
                break;
            case 3:
                tempDosage = 21600000;  // 3x daily. Alarm interval is once every 6 hours
                break;
            default:
                tempDosage = 86400000;
                break;
        }
        return tempDosage;
    }

    public int getDosageSpinnerIdFromText(String text){
        int tempId = 0;
        switch (text){
            case "1x daily":
                tempId = 0;
                break;
            case "2x daily":
                tempId = 1;
                break;
            case "3x daily":
                tempId = 2;
                break;
            default:
                tempId = 0;
                break;
        }
        return tempId;
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

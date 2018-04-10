package ly.remote.medmanager.models;

/**
 * Created by Risen on 3/30/2018.
 */

public class MedicationModel {
    private String med_name, med_desc, med_month, med_dosage, med_start_date, med_end_date;
    private int index, med_reminder;

    public MedicationModel(int index, String med_name, String med_desc, String med_month,
                             String med_dosage, String med_start_date,     //TODO change interval to integer
                                 String med_end_date, int med_reminder) {

        this.med_name = med_name;
        this.med_desc = med_desc;
        this.med_month = med_month;
        this.med_dosage = med_dosage;
        this.med_start_date = med_start_date;
        this.med_end_date = med_end_date;
        this.index = index;
        this.med_reminder = med_reminder;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getMed_desc() {
        return med_desc;
    }

    public void setMed_desc(String med_desc) {
        this.med_desc = med_desc;
    }

    public String getMed_month() {
        return med_month;
    }

    public void setMed_month(String med_month) {
        this.med_month = med_month;
    }

    public String getMed_dosage() {
        return med_dosage;
    }

    public void setMed_dosage(String med_dosage) {
        this.med_dosage = med_dosage;
    }

    public String getMed_start_date() {
        return med_start_date;
    }

    public void setMed_start_date(String med_start_date) {
        this.med_start_date = med_start_date;
    }

    public String getMed_end_date() {
        return med_end_date;
    }

    public void setMed_end_date(String med_end_date) {
        this.med_end_date = med_end_date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMed_reminder() {
        return med_reminder;
    }

    public void setMed_reminder(int med_reminder) {
        this.med_reminder = med_reminder;
    }
}

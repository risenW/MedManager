package ly.remote.medmanager.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ly.remote.medmanager.R;
import ly.remote.medmanager.controllers.alarmManager.AlarmReceiver;
import ly.remote.medmanager.controllers.DatabaseHelper;
import ly.remote.medmanager.controllers.NewMedCreationHelper;
import ly.remote.medmanager.controllers.alarmManager.NotificationScheduler;
import ly.remote.medmanager.models.LocalData;

public class CreateMedicationActivity extends AppCompatActivity {

    private EditText editText_med_name, editText_med_description;
    private TextView view_start_date, view_end_date, view_med_month, view_med_id;
    private Spinner spinner_daily_interval, spinner_remind_me;
    private Button btn_start_date, btn_end_date, btn_save, btn_edit;
    private static int index;
    private static final String MY_PREF = "my_preference";
    private final String INDEX_VALUE = "indexValue";   //Key for saving in preference
    private String Update;
    private static final int PENDING_REQUEST_CODE = 100;
    private DatePickerDialog datePickerDialog;
    int hour, min; //Time for setting Alarm

    private DatabaseHelper databaseHelper;
    private RecyclerViewActivity recyclerViewObject;
    private NewMedCreationHelper medCreationHelper;
    private LocalData localData;

    @Override
    protected void onStart() {
        super.onStart();
        //disable all views
        disableViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medication);

        //Initializes all views and disables editing.
        initializeViewsAndObjects();
        get_extras_and_populate_views();



        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enable Views and buttons
                enableViews();
                btn_save.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.GONE);
            }
        });

        btn_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int mYear = 2016; int mMonth = 1; int mDay = 4;
                    datePickerDialog = new DatePickerDialog(CreateMedicationActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String selected_date = medCreationHelper.constructDateFromNumbers(dayOfMonth, month, year);
                            view_start_date.setText(selected_date);
                            //Set the current month
                            view_med_month.setText(medCreationHelper.getMonth(month));
                        }
                    }, mYear,mMonth,mDay );

                    datePickerDialog.show();
                }

        });


        btn_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear = 2016; int mMonth = 1; int mDay = 4;
                datePickerDialog = new DatePickerDialog(CreateMedicationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String selected_date = medCreationHelper.constructDateFromNumbers(dayOfMonth, month, year);
                        view_end_date.setText(selected_date);

                    }
                }, mYear,mMonth,mDay );

                datePickerDialog.show();
            }

        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medCreationHelper = new NewMedCreationHelper();

               if (Update.equals("No")){
                   //Save a New Medication in database
                   save_in_database();

               }else {
                   //Update is to be performed
                   update_medication();
               }

                //disable all views
                disableViews();
                btn_edit.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.GONE);
            }
        });

    }

    public void initializeViewsAndObjects(){

        editText_med_name = (EditText)findViewById(R.id.user_med_name);
        editText_med_description = (EditText)findViewById(R.id.user_med_description);
        view_med_month = (TextView)findViewById(R.id.view_med_month);
        view_med_id = (TextView)findViewById(R.id.view_med_id);
        view_start_date = (TextView) findViewById(R.id.user_selected_start_date);
        view_end_date = (TextView) findViewById(R.id.user_selected_end_date);
        spinner_daily_interval = (Spinner)findViewById(R.id.user_selected_spinner_interval);
        spinner_remind_me = (Spinner)findViewById(R.id.user_selected_reminder_option);
        btn_start_date = (Button)findViewById(R.id.btn_start_date);
        btn_end_date = (Button)findViewById(R.id.btn_end_date);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_edit = (Button)findViewById(R.id.btn_edit);
        recyclerViewObject = new RecyclerViewActivity();
        databaseHelper = new DatabaseHelper(this);
        medCreationHelper = new NewMedCreationHelper();
        localData = new LocalData(CreateMedicationActivity.this);

    }

    public void enableViews(){

        editText_med_name.setEnabled(true);
        editText_med_description.setEnabled(true);
        spinner_daily_interval.setEnabled(true);
        view_start_date.setEnabled(true);
        view_end_date.setEnabled(true);
        spinner_daily_interval.setEnabled(true);
        spinner_remind_me.setEnabled(true);
    }

    public void disableViews(){

        editText_med_name.setEnabled(false);
        editText_med_description.setEnabled(false);
        spinner_daily_interval.setEnabled(false);
        view_start_date.setEnabled(false);
        view_end_date.setEnabled(false);
        spinner_daily_interval.setEnabled(false);
        spinner_remind_me.setEnabled(false);
    }

    //Method to get the extras from the RecyclerView list Intent and populate the Create new Medication view
    public void get_extras_and_populate_views(){
        Bundle extras = getIntent().getExtras();
        String new_medication, med_name, med_desc, med_month, med_interval, med_start_date, med_end_date;
        int med_id, med_remind_me;

        med_id = extras.getInt(recyclerViewObject.INDEX);
        new_medication = extras.getString(recyclerViewObject.NEW_MEDICATION);
        med_name = extras.getString(recyclerViewObject.MED_NAME);
        med_desc = extras.getString(recyclerViewObject.MED_DESC);
        med_month = extras.getString(recyclerViewObject.MED_MONTH);
        med_interval = extras.getString(recyclerViewObject.MED_INTERVAL);
        med_start_date = extras.getString(recyclerViewObject.MED_START_DATE);
        med_end_date = extras.getString(recyclerViewObject.MED_END_DATE);
        med_remind_me = extras.getInt(recyclerViewObject.MED_REMINDER);

        if (new_medication.equals("No")){
            Update = "Yes";
            view_med_id.setText(med_id + "");
            editText_med_name.setText(med_name);
            editText_med_description.setText(med_desc);
            view_med_month.setText(med_month);
            view_start_date.setText(med_start_date);
            view_end_date.setText(med_end_date);
            spinner_remind_me.setSelection(med_remind_me);
//            spinner_daily_interval.setSelection(medCreationHelper.get_selected_interval_spinner_item(med_interval));

        }else {
            Update = "No";

        }
    }

    public void save_in_database(){

        try {
            Toast.makeText(CreateMedicationActivity.this, "Saving", Toast.LENGTH_SHORT).show();

            index = localData.getSavedIndexFromPref();  //Index is used when deleting an item from the database
            index++;
            //Saves to database
            databaseHelper.open();
            String temp_med_name, temp_med_desc, temp_med_month, temp_med_interval, temp_med_start_date, temp_med_end_date;
            int temp_remind_me;

            temp_med_name = editText_med_name.getText().toString();
            temp_med_desc = editText_med_description.getText().toString();
            temp_med_interval = spinner_daily_interval.getSelectedItem().toString();
            temp_med_month = view_med_month.getText().toString();
            temp_med_start_date = view_start_date.getText().toString();
            temp_med_end_date = view_end_date.getText().toString();
            temp_remind_me = medCreationHelper.getBooleanValue(spinner_remind_me.getSelectedItem().toString());
            //Makes the insertion in database
            databaseHelper.saveMedication(index,temp_med_name,temp_med_desc,temp_med_month,
                    temp_med_interval,temp_med_start_date,
                    temp_med_end_date,temp_remind_me);

            //Activate reminder if user selects yes
            if (temp_remind_me == 1){
                NotificationScheduler.setReminder(CreateMedicationActivity.this,AlarmReceiver.class,index,10,0);
            }
            databaseHelper.close();
            Toast.makeText(CreateMedicationActivity.this, "Saved Successfully and Alarm started", Toast.LENGTH_LONG).show();
            recyclerViewObject.medicationAdapter.notifyDataSetChanged();


        }catch (Exception e){
            e.printStackTrace();
        }

        //Saves the current index
        localData.saveIndexInPref(index);

    }
//
    private void update_medication() {
        try {
            Toast.makeText(CreateMedicationActivity.this, "Updating", Toast.LENGTH_SHORT).show();
            int id = Integer.parseInt(view_med_id.getText().toString());

            databaseHelper.open();
            String temp_med_name, temp_med_desc, temp_med_month, temp_med_interval, temp_med_start_date, temp_med_end_date;
            int temp_remind_me;

            temp_med_name = editText_med_name.getText().toString();
            temp_med_desc = editText_med_description.getText().toString();
            temp_med_interval = spinner_daily_interval.getSelectedItem().toString();
            temp_med_month = view_med_month.getText().toString();
            temp_med_start_date = view_start_date.getText().toString();
            temp_med_end_date = view_end_date.getText().toString();
            temp_remind_me = medCreationHelper.getBooleanValue(spinner_remind_me.getSelectedItem().toString());
            //Makes the update in database
            databaseHelper.updateMedById(id,temp_med_name,temp_med_desc,temp_med_month,
                    temp_med_interval,temp_med_start_date,temp_med_end_date,temp_remind_me);

//            Cancels the reminder if user selects No
            if (temp_remind_me == 1){

                NotificationScheduler.setReminder(CreateMedicationActivity.this,AlarmReceiver.class,index,10,0);

            }else {
                NotificationScheduler.cancelReminder(CreateMedicationActivity.this,AlarmReceiver.class,id);

            }

            databaseHelper.close();
            Toast.makeText(CreateMedicationActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
            recyclerViewObject.medicationAdapter.notifyDataSetChanged();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

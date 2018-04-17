package ly.remote.medmanager.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import ly.remote.medmanager.R;
import ly.remote.medmanager.controllers.alarmManager.AlarmReceiver;
import ly.remote.medmanager.controllers.DatabaseHelper;
import ly.remote.medmanager.controllers.NewMedCreationHelper;
import ly.remote.medmanager.controllers.alarmManager.NotificationScheduler;
import ly.remote.medmanager.models.LocalData;

public class CreateMedicationActivity extends AppCompatActivity {

    private EditText editText_med_name, editText_med_description;
    private TextView view_start_date, view_end_date, view_med_month, view_med_id, view_med_start_time;
    private Spinner  spinner_remind_me,spinner_dosage;
    private Button btn_start_date, btn_end_date, btn_save, btn_edit, btn_pick_time;
    private static int index;
    private String Update;
    private DatePickerDialog datePickerDialog;
    private Toolbar toolbar;
    int hour, min;
    int saveBtnClickCount = 0;

    private DatabaseHelper databaseHelper;
    private RecyclerViewActivity recyclerViewObject;
    private NewMedCreationHelper medCreationHelper;
    private LocalData localData;


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

        btn_pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateMedicationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        min = minute;
                        view_med_start_time.setText(hourOfDay + getString(R.string.time_zero));
                    }
                },LocalData.DEFAULT_HOUR, LocalData.DEFAULT_MIN,false );

                timePickerDialog.show();
            }
        });

        btn_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear = LocalData.DEFAULT_YEAR;
                int mMonth = LocalData.DEFAULT_MONTH;
                int mDay = LocalData.DEFAULT_DAY;

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
                int mYear = LocalData.DEFAULT_YEAR;
                int mMonth = LocalData.DEFAULT_MONTH;
                int mDay = LocalData.DEFAULT_DAY;
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

                if (TextUtils.isEmpty(view_med_start_time.getText())){
                    Toast.makeText(CreateMedicationActivity.this, "Please set a time", Toast.LENGTH_LONG).show();

                }else {

                    if (saveBtnClickCount == 1 || Update.equals("Yes")){
                        //Saved already, Perform an Update
                        update_medication();
                    }else {
                        //New Medication. Save in database
                        save_in_database();
                        saveBtnClickCount = 1;  //saveClickCounter is used to determine if the save button has been clicked

                    }
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
        view_med_start_time = (TextView)findViewById(R.id.view_med_start_time);
        spinner_remind_me = (Spinner)findViewById(R.id.user_selected_reminder_option);
        spinner_dosage = (Spinner)findViewById(R.id.user_selected_dosage);
        btn_start_date = (Button)findViewById(R.id.btn_start_date);
        btn_end_date = (Button)findViewById(R.id.btn_end_date);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_edit = (Button)findViewById(R.id.btn_edit);
        btn_pick_time = (Button)findViewById(R.id.btn_pick_time);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerViewObject = new RecyclerViewActivity();
        databaseHelper = new DatabaseHelper(this);
        medCreationHelper = new NewMedCreationHelper();
        localData = new LocalData(CreateMedicationActivity.this);
        setSupportActionBar(toolbar);

        //disable all views
        disableViews();

    }

    public void enableViews(){

        editText_med_name.setEnabled(true);
        editText_med_description.setEnabled(true);
        view_start_date.setEnabled(true);
        view_end_date.setEnabled(true);
        spinner_remind_me.setEnabled(true);
        spinner_dosage.setEnabled(true);
        btn_pick_time.setEnabled(true);
        btn_start_date.setEnabled(true);
        btn_end_date.setEnabled(true);

    }

    public void disableViews(){

        editText_med_name.setEnabled(false);
        editText_med_description.setEnabled(false);
        view_start_date.setEnabled(false);
        view_end_date.setEnabled(false);
        spinner_remind_me.setEnabled(false);
        spinner_dosage.setEnabled(false);
        btn_pick_time.setEnabled(false);
        btn_start_date.setEnabled(false);
        btn_end_date.setEnabled(false);


    }

    //Method to get the extras from the RecyclerView list Intent and populate the Create new Medication view
    public void get_extras_and_populate_views(){
        Bundle extras = getIntent().getExtras();
        String  med_name, med_desc, med_month, med_dosage, med_start_date, med_end_date;
        int med_id, med_remind_me, new_medication, med_start_time;

        med_id = extras.getInt(recyclerViewObject.INDEX);
        new_medication = extras.getInt(recyclerViewObject.NEW_MEDICATION);
        med_name = extras.getString(recyclerViewObject.MED_NAME);
        med_desc = extras.getString(recyclerViewObject.MED_DESC);
        med_month = extras.getString(recyclerViewObject.MED_MONTH);
        med_dosage = extras.getString(recyclerViewObject.MED_DOSAGE);
        med_start_date = extras.getString(recyclerViewObject.MED_START_DATE);
        med_end_date = extras.getString(recyclerViewObject.MED_END_DATE);
        med_remind_me = extras.getInt(recyclerViewObject.MED_REMINDER);
        med_start_time = extras.getInt(recyclerViewObject.MED_START_TIME);

        if (new_medication == 0){
            //Old medication
            Update = "Yes";
            view_med_id.setText(String.valueOf(med_id));
            editText_med_name.setText(med_name);
            editText_med_description.setText(med_desc);
            view_med_month.setText(med_month);
            view_start_date.setText(med_start_date);
            view_end_date.setText(med_end_date);
            view_med_start_time.setText(String.valueOf(med_start_time));
            spinner_remind_me.setSelection(med_remind_me);
            spinner_dosage.setSelection(medCreationHelper.getDosageSpinnerIdFromText(med_dosage));

        }else {
            //New medication
            Update = "No";

        }
    }

    public void save_in_database(){

        try {
            Toast.makeText(CreateMedicationActivity.this, R.string.saving, Toast.LENGTH_SHORT).show();

            index = localData.getSavedIndexFromPref();  //Index is used when deleting an item from the database
            index++;
            //Saves to database
            databaseHelper.open();
            String temp_med_name, temp_med_desc, temp_med_month,temp_med_start_date, temp_med_end_date,temp_med_dosage;
            int temp_remind_me,dosage_interval, temp_med_start_time;

            temp_med_name = editText_med_name.getText().toString();
            temp_med_desc = editText_med_description.getText().toString();
            temp_med_dosage = spinner_dosage.getSelectedItem().toString();
            temp_med_month = view_med_month.getText().toString();
            temp_med_start_date = view_start_date.getText().toString();
            temp_med_end_date = view_end_date.getText().toString();
            temp_remind_me = medCreationHelper.getBooleanValue(spinner_remind_me.getSelectedItem().toString());
            temp_med_start_time = hour;

            dosage_interval = medCreationHelper.getIntervalFromDosage(spinner_dosage.getSelectedItemPosition());
            //Makes the insertion in database
            databaseHelper.saveMedication(index,temp_med_name,temp_med_desc,temp_med_month,
                    temp_med_dosage,temp_med_start_date,
                    temp_med_end_date,temp_remind_me,temp_med_start_time);

            //Sets Alarm when user selects yes
            if (temp_remind_me == 1){
                NotificationScheduler.setReminder(CreateMedicationActivity.this,AlarmReceiver.class,index,hour,LocalData.DEFAULT_MIN,dosage_interval);
                //Save Alarm in Alarm database
                databaseHelper.saveAlarm(index,hour,LocalData.DEFAULT_MIN);
            }
            databaseHelper.close();
            Toast.makeText(CreateMedicationActivity.this, R.string.saved_successfully, Toast.LENGTH_LONG).show();
            recyclerViewObject.medicationAdapter.notifyDataSetChanged();


        }catch (Exception e){
            e.printStackTrace();
        }

        //Saves the current index
        localData.saveIndexInPref(index);

    }

    private void update_medication() {
        try {
            Toast.makeText(CreateMedicationActivity.this, R.string.updating, Toast.LENGTH_SHORT).show();
            int id = Integer.parseInt(view_med_id.getText().toString());

            databaseHelper.open();
            String temp_med_name, temp_med_desc, temp_med_month, temp_med_dosage, temp_med_start_date, temp_med_end_date;
            int temp_remind_me, dosage_interval,temp_med_start_time ;

            temp_med_name = editText_med_name.getText().toString();
            temp_med_desc = editText_med_description.getText().toString();
            temp_med_dosage = spinner_dosage.getSelectedItem().toString();
            temp_med_month = view_med_month.getText().toString();
            temp_med_start_date = view_start_date.getText().toString();
            temp_med_end_date = view_end_date.getText().toString();
            temp_med_start_time = hour;  //TODO remove hour, may cause null exception on update
            temp_remind_me = medCreationHelper.getBooleanValue(spinner_remind_me.getSelectedItem().toString());
            dosage_interval = medCreationHelper.getIntervalFromDosage(spinner_dosage.getSelectedItemPosition());

            //Makes the update in database
            databaseHelper.updateMedById(id,temp_med_name,temp_med_desc,temp_med_month,
                    temp_med_dosage,temp_med_start_date,temp_med_end_date,temp_remind_me,temp_med_start_time);

//            Cancels the reminder if user selects No
            if (temp_remind_me == 1){

                NotificationScheduler.setReminder(CreateMedicationActivity.this,AlarmReceiver.class,index,hour,LocalData.DEFAULT_MIN,dosage_interval);

            }else {
                NotificationScheduler.cancelReminder(CreateMedicationActivity.this,AlarmReceiver.class,id);

            }

            databaseHelper.close();
            Toast.makeText(CreateMedicationActivity.this, R.string.updatedsuccessfully, Toast.LENGTH_SHORT).show();
            recyclerViewObject.medicationAdapter.notifyDataSetChanged();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.profile:
                Intent intent1 = new Intent(this,ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.rate:
                //Google play id goes here
                break;
            case R.id.exit:
                //exit code
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.Exit_text);
                builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });

                builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;

            default:
                return super.onOptionsItemSelected(item);


        }
        return true;
    }

}

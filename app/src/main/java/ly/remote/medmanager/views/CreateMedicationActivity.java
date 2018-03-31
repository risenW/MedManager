package ly.remote.medmanager.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ly.remote.medmanager.R;
import ly.remote.medmanager.controllers.DatabaseHelper;

public class CreateMedicationActivity extends AppCompatActivity {
    private EditText med_name, med_description;
    private TextView view_start_date, view_end_date;
    private Spinner daily_interval, remind_me;
    private Button btn_start_date, btn_end_date, btn_save, btn_edit;
    public static int index;
    private DatabaseHelper databaseHelper;
    private RecyclerViewActivity recyclerViewObject;
    private static final String MY_PREF = "my_preference";
    private final String INDEX_VALUE = "indexValue";   //Key for saving in preference

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
        initializeViews();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enable Views and buttons
                enableViews();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Disable all views
//                disableViews();
               try {
                   Toast.makeText(CreateMedicationActivity.this, "Saving", Toast.LENGTH_SHORT).show();

                   index = getSavedIndex();  //Index is used when deleting an item from the database
                   index++;
                   //Saves to database
                   databaseHelper.open();
                   String temp_med_name, temp_med_desc, temp_med_month, temp_med_interval, temp_med_start_date, temp_med_end_date;
                   int temp_remind_me;

                   temp_med_name = med_name.getText().toString();
                   temp_med_desc = med_description.getText().toString();
                   temp_med_interval = "6";  //TODO change interval to integer
                   temp_med_month = "April";  //TODO Hardcoded value. Will add a month method later to get the current month"
                   temp_med_start_date = "April 5th";
                   temp_med_end_date = "June 4th";
                   temp_remind_me = 1;  //TODO hardcoded remind me value.  Change later

                   //Makes the insertion in database
                   databaseHelper.insertMedication(index,temp_med_name,temp_med_desc,temp_med_month,
                                                           temp_med_interval,temp_med_start_date,
                                                           temp_med_end_date,temp_remind_me);

                   Log.d("Insertion","Medication Inserted");
                   Toast.makeText(CreateMedicationActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                   databaseHelper.close();
                   recyclerViewObject.medicationAdapter.notifyDataSetChanged();

               }catch (Exception e){
                   e.printStackTrace();
               }

                //Saves the current index
                saveIndexInPref();
            }
        });

    }

    public void initializeViews(){

        med_name = (EditText)findViewById(R.id.user_med_name);
        med_description = (EditText)findViewById(R.id.user_med_description);
        view_start_date = (TextView) findViewById(R.id.user_selected_start_date);
        view_end_date = (TextView) findViewById(R.id.user_selected_end_date);
        daily_interval = (Spinner)findViewById(R.id.user_selected_spinner_interval);
        remind_me = (Spinner)findViewById(R.id.user_selected_reminder_option);
        btn_start_date = (Button)findViewById(R.id.btn_start_date);
        btn_end_date = (Button)findViewById(R.id.btn_end_date);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_edit = (Button)findViewById(R.id.btn_edit);
        recyclerViewObject = new RecyclerViewActivity();
        databaseHelper = new DatabaseHelper(this);

    }

    public void enableViews(){

        med_name.setEnabled(true);
        med_description.setEnabled(true);
        daily_interval.setEnabled(true);
        view_start_date.setEnabled(true);
        view_end_date.setEnabled(true);
        daily_interval.setEnabled(true);
        remind_me.setEnabled(true);
    }

    public void disableViews(){

        med_name.setEnabled(false);
        med_description.setEnabled(false);
        daily_interval.setEnabled(false);
        view_start_date.setEnabled(false);
        view_end_date.setEnabled(false);
        daily_interval.setEnabled(false);
        remind_me.setEnabled(false);
    }

    public int getSavedIndex(){
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF,0);
        int savedIndex = sharedPreferences.getInt(INDEX_VALUE,-1);
        Log.d("Returned Index:", "" + savedIndex);
        return savedIndex;
    }

    public void saveIndexInPref(){

        SharedPreferences preferences = getSharedPreferences(MY_PREF, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(INDEX_VALUE,index);
        editor.apply();
        Log.d("Saved Index", "" + index);
    }


}

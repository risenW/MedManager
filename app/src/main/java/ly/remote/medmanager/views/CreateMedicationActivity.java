package ly.remote.medmanager.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import ly.remote.medmanager.R;

public class CreateMedicationActivity extends AppCompatActivity {
    private EditText med_name, med_description;
    private TextView view_start_date, view_end_date;
    private Spinner daily_interval, remind_me;
    private Button btn_start_date, btn_end_date, btn_save, btn_edit;

    @Override
    protected void onStart() {
        super.onStart();
        //disable all views
        disableViews();
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
                btn_save.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.GONE);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Disable all views
                disableViews();
                //Saves to database

                btn_save.setVisibility(View.GONE);
                btn_edit.setVisibility(View.VISIBLE);
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
}

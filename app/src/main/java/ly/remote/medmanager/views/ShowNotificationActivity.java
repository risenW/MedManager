package ly.remote.medmanager.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ly.remote.medmanager.R;
import ly.remote.medmanager.controllers.alarmManager.NotificationScheduler;

public class ShowNotificationActivity extends AppCompatActivity {
    private TextView med_desc, med_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);

        med_desc = (TextView)findViewById(R.id.show_notif_view_med_text);
        med_title = (TextView)findViewById(R.id.show_notif_view_med_title);

        Bundle extras = getIntent().getExtras();
        med_title.setText(extras.getString(NotificationScheduler.MED_TITLE_EXTRA_KEY));
        med_desc.setText(extras.getString(NotificationScheduler.MED_DESCRIPTION_KEY));
    }
}

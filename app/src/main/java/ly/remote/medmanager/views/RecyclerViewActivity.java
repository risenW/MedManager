package ly.remote.medmanager.views;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import ly.remote.medmanager.R;
import ly.remote.medmanager.controllers.DatabaseHelper;
import ly.remote.medmanager.controllers.MedicationAdapter;
import ly.remote.medmanager.models.MedicationModel;

public class RecyclerViewActivity extends AppCompatActivity {
     RecyclerView recyclerView;
     RecyclerView.LayoutManager layoutManager;
     MedicationAdapter medicationAdapter;
     ArrayList<MedicationModel> medicationModelArrayList;
     private FloatingActionButton addMedication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicationModelArrayList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Populates the recycler view
        fillAdapter();
        final Intent intent =  new Intent(this,CreateMedicationActivity.class);

        addMedication = (FloatingActionButton)findViewById(R.id.add_mediction);
        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


    }

    private void fillAdapter() {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.open();
            Cursor cursor = databaseHelper.getMedication();

            if (cursor != null){
                do{
                    MedicationModel medicationModel = new MedicationModel(cursor.getInt(0),   //index
                            cursor.getString(1), //name
                            cursor.getString(2),  //description
                            cursor.getString(3),  //month
                            cursor.getString(4),   //interval
                            cursor.getString(5),   //start date
                            cursor.getString(6),   //end date
                            cursor.getInt(7));  //remind me value
                    medicationModelArrayList.add(medicationModel);
                } while (cursor.moveToNext());
            }

            databaseHelper.close();
            medicationAdapter = new MedicationAdapter(medicationModelArrayList);
            recyclerView.setAdapter(medicationAdapter);

        } catch (Exception e) {
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
                break;

            default:
                return super.onOptionsItemSelected(item);


        }
        return true;
    }


}

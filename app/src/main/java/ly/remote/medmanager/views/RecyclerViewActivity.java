package ly.remote.medmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ly.remote.medmanager.R;
import ly.remote.medmanager.controllers.DatabaseHelper;
import ly.remote.medmanager.controllers.Interfaces.MyItemOnClickListener;
import ly.remote.medmanager.controllers.Interfaces.MyItemOnLongClickListener;
import ly.remote.medmanager.controllers.MedicationAdapter;
import ly.remote.medmanager.models.MedicationModel;

public class RecyclerViewActivity extends AppCompatActivity implements MyItemOnClickListener,MyItemOnLongClickListener {
     RecyclerView recyclerView;
     RecyclerView.LayoutManager layoutManager;
     MedicationAdapter medicationAdapter;
     ArrayList<MedicationModel> medicationModelArrayList;
     FloatingActionButton addMedication;

    //Intent Keys
    public final String INDEX = "index";
    public final String MED_NAME = "med_name";
    public final String MED_DESC = "med_desc";
    public final String MED_MONTH = "med_month";
    public final String MED_INTERVAL = "med_interval";
    public final String MED_START_DATE = "med_start_date";
    public final String MED_END_DATE = "med_end_date";
    public final String MED_REMINDER = "med_reminder";
    public final String NEW_MEDICATION = "new_medication";



    @Override
    public void onBackPressed() {
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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicationModelArrayList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fillAdapter();
        final Intent intent =  new Intent(this,CreateMedicationActivity.class);
//        intent.putExtra("UPDATE", "No");

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

            this.medicationAdapter.setClickListener(this);
            this.medicationAdapter.setLongClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        MedicationModel medicationModel =  medicationModelArrayList.get(position);
        //start the CreateMedicationActivity with the clicked list item
        if (medicationModel != null){

            Intent intent = new Intent(RecyclerViewActivity.this, CreateMedicationActivity.class);
            intent.putExtra(NEW_MEDICATION, "No");
            intent.putExtra(INDEX, position);
            intent.putExtra(MED_NAME, medicationModel.getMed_name());
            intent.putExtra(MED_DESC, medicationModel.getMed_desc());
            intent.putExtra(MED_MONTH, medicationModel.getMed_month());
            intent.putExtra(MED_INTERVAL, medicationModel.getMed_interval());
            intent.putExtra(MED_START_DATE, medicationModel.getMed_start_date());
            intent.putExtra(MED_END_DATE, medicationModel.getMed_end_date());
            intent.putExtra(MED_REMINDER, medicationModel.getMed_reminder());

            startActivity(intent);
        }

    }

    @Override
    public void OnItemLongClickListener(View view, final int position) {
        Toast.makeText(this, "Long Clicked: " + position, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.Delete_text);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //Deletes from data base
                DatabaseHelper databaseHelper = new DatabaseHelper(RecyclerViewActivity.this);
                MedicationModel medicationModel = medicationModelArrayList.get(position);

                if (medicationModel != null ){

                    databaseHelper.open();
                    databaseHelper.deleteMedicationByID(medicationModel.getIndex());   //Deletes from the database
                    medicationModelArrayList.remove(position);                          //Removes deleted Item from the list
                    medicationAdapter.notifyItemRemoved(position);
                    medicationAdapter.notifyDataSetChanged();

                    Toast.makeText(RecyclerViewActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    databaseHelper.close();

                }

            }
        });

        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

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

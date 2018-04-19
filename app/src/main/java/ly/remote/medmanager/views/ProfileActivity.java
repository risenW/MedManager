package ly.remote.medmanager.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ly.remote.medmanager.R;
import ly.remote.medmanager.models.UserModel;

public class ProfileActivity extends AppCompatActivity {
    TextView name, user_email;
    EditText username, user_about;
    ImageView prof_pic;
    Button sign_out, edit_profile, save_profile;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Toolbar toolbar;

    @Override
    protected void onStart() {
        super.onStart();
        disableViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("UserProfile");

        initialize_views();


        if (user != null){
            name.setText(user.getDisplayName());
            user_email.setText(user.getEmail());
            String imageUri = user.getPhotoUrl().toString();

            Picasso.with(this).load(imageUri).fit().centerCrop()
                    .placeholder(R.drawable.ic_person_black)
                    .error(R.drawable.ic_person_black)
                    .into(prof_pic);
        }

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(ProfileActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
                                finish();
                            }
                        });
            }
        });

        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableViews();
                edit_profile.setVisibility(View.VISIBLE);
                save_fireBase_db();

            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableViews();
            }
        });

    }

    private void initialize_views() {

        name = (TextView)findViewById(R.id.user_real_name);
        user_email = (TextView)findViewById(R.id.user_email);
        username = (EditText)findViewById(R.id.username);
        user_about = (EditText)findViewById(R.id.user_about);
        prof_pic = (ImageView)findViewById(R.id.profile_pic);
        sign_out = (Button)findViewById(R.id.btn_sign_out);
        edit_profile = (Button)findViewById(R.id.btn_edit_profile);
        save_profile = (Button)findViewById(R.id.btn_save_profile);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void disableViews() {

        user_about.setEnabled(false);
        username.setEnabled(false);
        save_profile.setVisibility(View.GONE);
    }

    private void enableViews() {

        user_about.setEnabled(true);
        username.setEnabled(true);
        save_profile.setVisibility(View.VISIBLE);
        edit_profile.setVisibility(View.GONE);
    }

    private void save_fireBase_db() {
        UserModel userModel = new UserModel(username.getText().toString(),user_about.getText().toString());
        save_profile.setText(R.string.saveState);
        myRef.setValue(userModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(ProfileActivity.this, "Profile updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
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

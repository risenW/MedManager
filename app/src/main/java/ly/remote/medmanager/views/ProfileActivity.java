package ly.remote.medmanager.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import ly.remote.medmanager.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView username, user_email;
    private ImageView prof_pic;
    private Button sign_out;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        username = (TextView)findViewById(R.id.username);
        user_email = (TextView)findViewById(R.id.user_email);
        prof_pic = (ImageView)findViewById(R.id.profile_pic);
        sign_out = (Button)findViewById(R.id.btn_sign_out);

        if (user != null){
            username.setText(user.getDisplayName());
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

    }
}

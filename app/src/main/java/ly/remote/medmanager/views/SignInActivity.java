package ly.remote.medmanager.views;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import ly.remote.medmanager.R;

public class SignInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private Button btn_sign_in;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
        btn_sign_in = (Button)findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Start FirebaseUI flow for google sign in
                if (auth.getCurrentUser() != null) {
                    //User is signed in
                    Intent intent = new Intent(SignInActivity.this, RecyclerViewActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //Not signed In
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
         });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                Intent intent = new Intent(SignInActivity.this,RecyclerViewActivity.class);
                startActivity(intent);
                finish();

            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, R.string.signin_cancelled, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

}

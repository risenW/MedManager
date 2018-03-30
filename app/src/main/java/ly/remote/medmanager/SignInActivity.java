package ly.remote.medmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 123;
    private Button btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_sign_in = (Button)findViewById(R.id.btn_sign_in);
        auth = FirebaseAuth.getInstance();
        final Intent intent = new Intent(this, MainActivity.class);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
                //Start Firebase-UI flow for google sign in
//                startActivityForResult(
//                        AuthUI.getInstance()
//                                .createSignInIntentBuilder()
//                                .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder()))
//                                .build(),
//                        RC_SIGN_IN);
            }
        });

    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
//        if (requestCode == RC_SIGN_IN) {
//            IdpResponse response = IdpResponse.fromResultIntent(data);
//
//            // Successfully signed in
//            if (resultCode == RESULT_OK) {
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            } else {
//                // Sign in failed
//                if (response == null) {
//                    // User pressed back button
//                    Toast.makeText(this, R.string.sign_in_cancelled, Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                    //No internet connection
//                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
//                    Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                //Some unknown error
//                Toast.makeText(this,R.string.unknown_error, Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "Sign-in error: ", response.getError());
//            }
//        }
//    }


}

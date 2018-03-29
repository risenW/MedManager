package ly.remote.medmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class SplashScreen extends AppCompatActivity {
    private ImageView splashImage;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  //Sets the view to fullscreen
        setContentView(R.layout.activity_splash_screen);

        splashImage = (ImageView)findViewById(R.id.splashImage);
//        auth = FirebaseAuth.getInstance();
        final Intent mainActivity = new Intent(this, MainActivity.class);
//        final Intent signIn = new Intent(this, SignIn.class);


        Thread sleep_timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(6000);

                }catch (Exception e){
                    e.printStackTrace();

                }finally {
//                    //Checks if user is signed in
//                    if(auth.getCurrentUser() != null){
//                        //already signed in
//                        startActivity(mainActivity);
//                        SplashScreen.this.finish();
//                    }else {
//                        //not signed in
//                          startActivityForResult(
//                                AuthUI.getInstance()
//                                        .createSignInIntentBuilder()
//                                        .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder()))
//                                        .build(),
//                                RC_SIGN_IN);

//                    }
                    startActivity(mainActivity);
                    SplashScreen.this.finish();
                }
            }
        };
        sleep_timer.start();

    }
}

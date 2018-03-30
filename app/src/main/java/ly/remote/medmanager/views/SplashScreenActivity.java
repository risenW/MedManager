package ly.remote.medmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import ly.remote.medmanager.R;

public class SplashScreenActivity extends AppCompatActivity {
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
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        splashImage.startAnimation(animation);
        final Intent intent = new Intent(this, SignInActivity.class);
//        final Intent signIn = new Intent(this, SignInActivity.class);


        Thread sleep_timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);

                }catch (Exception e){
                    e.printStackTrace();

                }finally {
//                    //Checks if user is signed in
//                    if(auth.getCurrentUser() != null){
//                        //already signed in
//                        startActivity(mainActivity);
//                        SplashScreenActivity.this.finish();
//                    }else {
//                        //not signed in
//                          startActivityForResult(
//                                AuthUI.getInstance()
//                                        .createSignInIntentBuilder()
//                                        .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder()))
//                                        .build(),
//                                RC_SIGN_IN);

//                    }
                    startActivity(intent);
                    SplashScreenActivity.this.finish();
                }
            }
        };
        sleep_timer.start();

    }
}

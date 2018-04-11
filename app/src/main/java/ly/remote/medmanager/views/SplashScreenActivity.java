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
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        splashImage = (ImageView)findViewById(R.id.splashImage);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        splashImage.startAnimation(animation);
        final Intent signInIntent = new Intent(this, SignInActivity.class);
        final Intent recyclerViewIntent = new Intent(this, RecyclerViewActivity.class);


        Thread sleep_timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);

                }catch (Exception e){
                    e.printStackTrace();

                }finally {
                    if (firebaseAuth.getCurrentUser() != null){
                        //User is already signed in
                        startActivity(recyclerViewIntent);
                        SplashScreenActivity.this.finish();

                    }else {

                        startActivity(signInIntent);
                        SplashScreenActivity.this.finish();
                    }

                }
            }
        };
        sleep_timer.start();

    }
}

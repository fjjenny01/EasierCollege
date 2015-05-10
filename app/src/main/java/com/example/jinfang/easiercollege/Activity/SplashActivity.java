package com.example.jinfang.easiercollege.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

import com.example.jinfang.easiercollege.R;


public class SplashActivity extends Activity{

    /** Duration of wait */
    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /** New Handler to start the Main-Activity and close the splash-sceen
         * after some certain time
         */

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                /* Create an Intent that will start the Main-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}

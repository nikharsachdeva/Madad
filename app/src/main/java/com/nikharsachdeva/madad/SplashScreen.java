package com.nikharsachdeva.madad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView copal_icon, purple_circle;
    Animation animZoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        init();
        zoomAnimation();
    }

    private void zoomAnimation() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                purple_circle.startAnimation(animZoomIn);
                animZoomIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        }, 1000);
    }

    private void init() {

        copal_icon = findViewById(R.id.header_icon);
        purple_circle = findViewById(R.id.purple_circle);

    }
}
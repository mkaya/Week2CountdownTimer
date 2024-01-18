package com.example.storyactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private MovieData md =new MovieData();
    private int currentMovie = 0;
    private ProgressBar progressBar;
    private long millisecondPassed=0;
    private CountDownTimer cdt;
    private GestureDetectorCompat gestureDetectorCompat;
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            GoNext();
            return true;
            //return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


    private void GoNext(){
        imageView.setImageDrawable(ContextCompat.getDrawable(this, (int)md.getItem(++currentMovie).get("image")));
        millisecondPassed=0;
        progressBar.setProgress(0);
        createTimer();
    }
    private void createTimer(){
        if(cdt!=null)
            cdt.cancel();
        cdt = new CountDownTimer(5000, 10) {

            public void onTick(long millisUntilFinished) {
                millisecondPassed = 5000-millisUntilFinished;
                progressBar.setProgress((int)millisecondPassed);
                Log.d("CDT",Long.toString(millisecondPassed)+" --- "+Long.toString(millisUntilFinished));
                // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //  mTextField.setText("done!");
                Log.d("CDT","Finish");
                GoNext();
            }
        };
        cdt.start();
    }



    private void resumeCounter(){
        final long newFutureTime = 5000-millisecondPassed;
        Log.d("NewFutureTime",Long.toString( newFutureTime));
        if(cdt!=null)
            cdt.cancel();
        cdt = new CountDownTimer(newFutureTime, 10) {

            public void onTick(long millisUntilFinished) {
                millisecondPassed = 5000-millisUntilFinished;
                progressBar.setProgress((int)millisecondPassed);
                Log.d("CDT",Long.toString(millisecondPassed)+" --- "+Long.toString(millisUntilFinished));
                // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //  mTextField.setText("done!");
                Log.d("CDT","Finish2");
                GoNext();
            }
        };
        cdt.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        imageView=findViewById(R.id.imageView);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, (int)md.getItem(currentMovie).get("image")));
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        cdt.cancel();
                        break;
                    case MotionEvent.ACTION_UP:
                        resumeCounter();
                        break;
                }
                gestureDetectorCompat.onTouchEvent(event);

                return true;
            }
        });
        createTimer();
    }

}
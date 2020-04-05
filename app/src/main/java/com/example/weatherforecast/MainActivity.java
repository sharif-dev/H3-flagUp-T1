package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_start_thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start_thread = findViewById(R.id.go_btn);

        button_start_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this, SecondPageAcitivity.class));

                GoRunnable goRunnable = new GoRunnable();
                Thread goThread = new Thread(goRunnable);
                goThread.start();
            }
        });


    }



    class GoRunnable implements Runnable{
        @Override
        public void run() {
            for (int i = 0 ; i < 15 ; i++){
                try{
                    Thread.sleep(1500);
                    Log.d(TAG, "run: " + i);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }



}

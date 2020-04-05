package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_start_thread;
    private TextView textView; //for testing the server response
    private EditText editText; //edit text that contains the input text
    private String json_url = "https://api.mapbox.com/geocoding/v5/mapbox.places/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start_thread = findViewById(R.id.go_btn);
        editText = findViewById(R.id.inputText);

        button_start_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                json_url += editText.getText();     // text written in the edit text
                json_url += ".json?access_token=";  // finnishing the url
                json_url += "pk.eyJ1IjoiYWxpYXNoOTgiLCJhIjoiY2s4bjdmZHk4MG13bTNmcGU1c3ZmdmZudiJ9.WyMiEFZqNFeI2hxx68CHhg"; //token
                // string is now ready to use

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
            // Sending the query to the API
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });



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

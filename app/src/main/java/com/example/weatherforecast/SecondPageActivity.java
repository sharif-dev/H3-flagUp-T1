package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SecondPageActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private Handler mainHandler = new Handler();
    private String longitude;
    private String latitude;
    private String name, region, country;
    private WeatherCondition currentCondition;
    private final int NUMBER_OF_DAYS = 3;
    private WeatherCondition[] forecastedConditions = new WeatherCondition[NUMBER_OF_DAYS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page_activity);
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.textView);
        String text = intent.getStringExtra("XY");
        String[] parts = text.split(",");
        longitude = parts[0].substring(1);
        latitude = parts[1].substring(0,parts[1].length() - 1);
    }

    class WeatherRunnable implements Runnable{
        @Override
        public void run() {
            String json_url = getString(R.string.weather_api_url, latitude, longitude);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(jsonObjectRequest);
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }
}

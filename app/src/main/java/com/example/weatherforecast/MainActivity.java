package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button button_start_thread;
    private EditText editText; //edit text that contains the input text
//    private String json_url;
    private RequestQueue mQueue;
    private Handler mainHandler = new Handler();
    private ListView myListView;    //it shows the cities found
    private ArrayList<String> cityItems = new ArrayList<String>();
    private ArrayList<String> cityXY = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start_thread = findViewById(R.id.go_btn);
        editText = findViewById(R.id.inputText);
        mQueue = Volley.newRequestQueue(this);
        myListView = findViewById(R.id.listview);

        button_start_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              startActivity(new Intent(MainActivity.this, SecondPageActivity.class));
                GoRunnable goRunnable = new GoRunnable();
                Thread goThread = new Thread(goRunnable);
                goThread.start();
            }
        });
    }

    //Thread that handles the communication with MapBox API
    class GoRunnable implements Runnable{
        @Override
        public void run() {
            String json_url = getString(R.string.mapbox_api_url, editText.getText());
            // text written in the edit text
            // string is now ready to use
            cityItems.clear();
            cityXY.clear();
            // Sending the query to the API
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("features");
                        for (int i = 0 ; i < jsonArray.length() ; i++){
                            JSONObject temp = jsonArray.getJSONObject(i);
                            String place = temp.getString("place_name");
                            String XY = temp.getString("center");
                            cityItems.add(place);
                            cityXY.add(XY);
//                            try {
//                                Thread.sleep(1500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            Log.d(TAG, place);
//                            Log.d(TAG, coordination);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1, cityItems);
                    myListView.setAdapter(adapter);
                    // the city name and coordination gets into the ListView
                }
            });
        }
    }



}

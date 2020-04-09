package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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


		// check internet connection
		button_start_thread = findViewById(R.id.go_btn);
		editText = findViewById(R.id.inputText);
		mQueue = Volley.newRequestQueue(this);
		myListView = findViewById(R.id.listview);

		button_start_thread.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isNetworkConnected(MainActivity.this)) {
					GoRunnable goRunnable = new GoRunnable();
					Thread goThread = new Thread(goRunnable);
					goThread.start();
				} else {
					Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(MainActivity.this, SecondPageActivity.class);
					intent.putExtra("loadPrevious", true);
					MainActivity.this.startActivity(intent);
				}
			}
		});
	}

	//Thread that handles the communication with MapBox API
	class GoRunnable implements Runnable {
		@Override
		public void run() {
			mainHandler.post(new Runnable() {
				@Override
				public void run() {
					ProgressBar progressBar = findViewById(R.id.progressBar);
					progressBar.setVisibility(View.VISIBLE);
				}
			});
			String json_url = getString(R.string.mapbox_api_url, editText.getText());
			// text written in the edit text
			// string is now ready to use
			cityItems.clear();
			cityXY.clear();
			// Sending the query to the API
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						JSONArray jsonArray = response.getJSONArray("features");
						cityItems.clear();
						cityXY.clear();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject temp = jsonArray.getJSONObject(i);
							String place = temp.getString("place_name");
							String XY = temp.getString("center");
							cityItems.add(place);
							cityXY.add(XY);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					error.printStackTrace();
					Toast.makeText(MainActivity.this, "Error in getting response", Toast.LENGTH_SHORT).show();
					mainHandler.post(new Runnable() {
						@Override
						public void run() {
							ProgressBar progressBar = findViewById(R.id.progressBar);
							progressBar.setVisibility(View.INVISIBLE);
						}
					});
				}
			});
			mQueue.add(jsonObjectRequest);
			mainHandler.post(new Runnable() {
				@Override
				public void run() {
					ProgressBar progressBar = findViewById(R.id.progressBar);
					progressBar.setVisibility(View.INVISIBLE);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
							android.R.layout.simple_list_item_1, cityItems);
					myListView.setAdapter(adapter);
					myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							Intent intent = new Intent(MainActivity.this, SecondPageActivity.class);
							intent.putExtra("XY", cityXY.get(position));
							intent.putExtra("loadPrevious", false);
							MainActivity.this.startActivity(intent);
						}
					});
				}
			});


		}
	}

	private boolean isNetworkConnected(Context context) {
		ConnectivityManager cm =
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}

}

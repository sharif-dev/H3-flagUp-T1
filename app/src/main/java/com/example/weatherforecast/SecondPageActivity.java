package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class SecondPageActivity extends AppCompatActivity {
	private RequestQueue mQueue;
	private Handler mainHandler = new Handler();
	private String longitude;
	private String latitude;
	private String name, region, country;
	private WeatherCondition currentCondition;
	private final int NUMBER_OF_DAYS = 7;
	private RecyclerView recyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager layoutManager;
	private WeatherCondition[] forecastedConditions = new WeatherCondition[NUMBER_OF_DAYS];
	private JSONObject lastForcast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_page_activity);
		ConstraintLayout info = findViewById(R.id.weather_info);
		info.setVisibility(View.INVISIBLE);
		Intent intent = getIntent();
		String text = intent.getStringExtra("XY");
		String[] parts = text.split(",");
		longitude = parts[0].substring(1);
		latitude = parts[1].substring(0, parts[1].length() - 1);
		mQueue = Volley.newRequestQueue(this);
		recyclerView = findViewById(R.id.forecastRecyclerView);
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);

		WeatherRunnable weatherRunnable = new WeatherRunnable();
		Thread weatherThread = new Thread(weatherRunnable);
		weatherThread.start();
	}

	class WeatherRunnable implements Runnable {
		@Override
		public void run() {
			String json_url = getString(R.string.weather_api_url, latitude, longitude);
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(final JSONObject response) {
					try {
						name = response.getJSONObject("location").getString("name");
						region = response.getJSONObject("location").getString("region");
						country = response.getJSONObject("location").getString("country");
						final JSONObject currentTemp = response.getJSONObject("current");
						currentCondition = new WeatherCondition.Builder()
								.withTempC(currentTemp.getDouble("temp_c"))
								.withTempF(currentTemp.getDouble("temp_f"))
								.withIsDay(currentTemp.getInt("is_day") != 0)
								.withConditionText(currentTemp.getJSONObject("condition").getString("text"))
								.withConditionCode(Integer.parseInt(WeatherCondition.getIconName(currentTemp.getJSONObject("condition")
									.getString("icon"))))
								.withWindKph(currentTemp.getDouble("wind_kph"))
								.withPressureMb(currentTemp.getDouble("pressure_mb"))
								.withPrecipMm(currentTemp.getDouble("precip_mm"))
								.withHumidity(currentTemp.getInt("humidity"))
								.withFeelsLikeC(currentTemp.getDouble("feelslike_c"))
								.withUv(currentTemp.getDouble("uv"))
								.build();
						JSONArray forecasts = response.getJSONObject("forecast").getJSONArray("forecastday");
						for (int i = 0; i < forecasts.length(); i++) {
							JSONObject forecast = forecasts.getJSONObject(i);
							lastForcast = forecast;
							JSONObject weather = forecast.getJSONObject("day");
							forecastedConditions[i] = new WeatherCondition.Builder()
									.withTimestamp(forecast.getLong("date_epoch"))
									.withMaxTempC(weather.getDouble("maxtemp_c"))
									.withMaxTempF(weather.getDouble("maxtemp_f"))
									.withMinTempC(weather.getDouble("mintemp_c"))
									.withMinTempF(weather.getDouble("mintemp_f"))
									.withTempC(weather.getDouble("avgtemp_c"))
									.withTempF(weather.getDouble("avgtemp_f"))
									.withConditionCode(Integer.parseInt(WeatherCondition.getIconName(weather.getJSONObject("condition")
											.getString("icon"))))
									.withConditionText(weather.getJSONObject("condition").getString("text"))
									.build();
						}

//						//writing the file
//
//						FileOutputStream fos = null;
//						try {
//							fos = openFileOutput("myJSON.json", MODE_PRIVATE);
//							fos.write(lastForcast.toString().getBytes());
//						} catch (FileNotFoundException e) {
//							e.printStackTrace();
//						} catch (IOException e) {
//							e.printStackTrace();
//						} finally {
//							if (fos != null) {
//								try {
//									fos.close();
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//							}
//						}

						mainHandler.post(new Runnable() {
							@Override
							public void run() {
								TextView cityName = findViewById(R.id.cityName);
								cityName.setText(String.format("%s, %s, %s", name, region, country));
								ProgressBar pb = findViewById(R.id.progressBar);
								pb.setVisibility(View.INVISIBLE);
								ConstraintLayout info = findViewById(R.id.weather_info);
								TextView tempC = findViewById(R.id.tempC);
								TextView tempF = findViewById(R.id.tempF);
								tempC.setText(Long.toString(Math.round(currentCondition.getTempC())));
								tempF.setText(Long.toString(Math.round(currentCondition.getTempF())));
								ImageView conditionImage = findViewById(R.id.conditionImage);
								String imageName = currentCondition.getImageName();
                                conditionImage.setImageResource(conditionImage.getContext().getResources()
                                        .getIdentifier(imageName, "drawable", conditionImage.getContext().getPackageName()));
								TextView condition = findViewById(R.id.condition);
								condition.setText(currentCondition.getConditionText());
								TextView wind = findViewById(R.id.wind);
								wind.setText(String.format("%.1f km/h", currentCondition.getWindKph()));
								TextView humidity = findViewById(R.id.humidity);
								humidity.setText(String.format("%d%%", currentCondition.getHumidity()));
								TextView pressure = findViewById(R.id.pressure);
								pressure.setText(String.format("%.1f mb", currentCondition.getPressureMb()));
								mAdapter = new WeatherAdapter(forecastedConditions);
								recyclerView.setAdapter(mAdapter);
                                info.setVisibility(View.VISIBLE);
							}
						});
					} catch (JSONException e) {
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

		}
	}
}

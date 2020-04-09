package com.example.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{
	private WeatherCondition[] weathers;
	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		public TextView weekDay;
		public TextView temperature;
		public ImageView weatherCondition;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			weekDay = itemView.findViewById(R.id.weekDay);
			temperature = itemView.findViewById(R.id.temperature);
			weatherCondition = itemView.findViewById(R.id.weatherCondition);
		}
	}
	public WeatherAdapter(WeatherCondition[] weathers) {
		this.weathers = weathers;
	}
	@NonNull
	@Override
	public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		View forecastView = inflater.inflate(R.layout.forecast_row, parent, false);
		ViewHolder viewHolder = new ViewHolder(forecastView);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		WeatherCondition condition = weathers[position];
		holder.weekDay.setText(condition.getWeekDay());
		String imageName = condition.getImageName();
		Context context = holder.weatherCondition.getContext();
		int id = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
		holder.weatherCondition.setImageResource(id);
		holder.temperature.setText(Math.round(condition.getTempC()) + "");
	}

	@Override
	public int getItemCount() {
		return weathers.length;
	}
}

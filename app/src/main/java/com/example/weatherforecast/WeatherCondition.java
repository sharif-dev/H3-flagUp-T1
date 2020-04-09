package com.example.weatherforecast;

import android.util.Log;

import java.text.SimpleDateFormat;

public class WeatherCondition {
	private double tempC, tempF, feelsLikeC, windKph, pressureMb, precipMm, uv, maxTempC, maxTempF, minTempC, minTempF;
	private int humidity, conditionCode;
	private long timestamp;
	private String conditionText;
	private boolean isDay;

	public WeatherCondition(double tempC, double tempF, double feelsLikeC, double windKph, double pressureMb, double precipMm, double uv, double maxTempC, double maxTempF, double minTempC, double minTempF, int humidity, int conditionCode, long timestamp, boolean isDay, String conditionText) {
		this.tempC = tempC;
		this.tempF = tempF;
		this.feelsLikeC = feelsLikeC;
		this.windKph = windKph;
		this.pressureMb = pressureMb;
		this.precipMm = precipMm;
		this.uv = uv;
		this.maxTempC = maxTempC;
		this.maxTempF = maxTempF;
		this.minTempC = minTempC;
		this.minTempF = minTempF;
		this.humidity = humidity;
		this.conditionCode = conditionCode;
		this.timestamp = timestamp;
		this.isDay = isDay;
		this.conditionText = conditionText;
	}

	static class Builder
	{
		private double tempC, tempF, feelsLikeC, windKph, pressureMb, precipMm, uv, maxTempC, maxTempF, minTempC, minTempF;
		private int humidity, conditionCode;
		private long timestamp;
		private String conditionText;
		private boolean isDay;
		Builder withTempC(double temp)
		{
			tempC = temp;
			return this;
		}
		Builder withTempF(double temp)
		{
			tempF = temp;
			return this;
		}
		Builder withFeelsLikeC(double temp)
		{
			feelsLikeC = temp;
			return this;
		}
		Builder withWindKph(double wind)
		{
			windKph = wind;
			return this;
		}
		Builder withPressureMb(double pressure)
		{
			pressureMb = pressure;
			return this;
		}
		Builder withPrecipMm(double precip)
		{
			precipMm = precip;
			return this;
		}
		Builder withUv(double uv)
		{
			this.uv = uv;
			return this;
		}
		Builder withMaxTempC(double temp)
		{
			maxTempC = temp;
			return this;
		}
		Builder withMinTempC(double temp)
		{
			minTempC = temp;
			return this;
		}
		Builder withMaxTempF(double temp)
		{
			maxTempF = temp;
			return this;
		}
		Builder withMinTempF(double temp)
		{
			minTempF = temp;
			return this;
		}
		Builder withHumidity(int humidity)
		{
			this.humidity = humidity;
			return this;
		}
		Builder withConditionCode(int code)
		{
			conditionCode = code;
			return this;
		}
		Builder withTimestamp(long time)
		{
			timestamp = time;
			return this;
		}
		Builder withIsDay(boolean day)
		{
			isDay = day;
			return this;
		}
		Builder withConditionText(String text)
		{
			conditionText = text;
			return this;
		}
		WeatherCondition build()
		{
			return new WeatherCondition(tempC, tempF, feelsLikeC, windKph, pressureMb, precipMm, uv, maxTempC, maxTempF, minTempC, minTempF, humidity, conditionCode, timestamp, isDay, conditionText);
		}
	}

	public double getTempC() {
		return tempC;
	}

	public double getTempF() {
		return tempF;
	}

	public double getFeelsLikeC() {
		return feelsLikeC;
	}

	public double getWindKph() {
		return windKph;
	}

	public double getPressureMb() {
		return pressureMb;
	}

	public double getPrecipMm() {
		return precipMm;
	}

	public double getUv() {
		return uv;
	}

	public double getMaxTempC() {
		return maxTempC;
	}

	public double getMaxTempF() {
		return maxTempF;
	}

	public double getMinTempC() {
		return minTempC;
	}

	public double getMinTempF() {
		return minTempF;
	}

	public int getHumidity() {
		return humidity;
	}

	public int getConditionCode() {
		return conditionCode;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getImageName()
	{
		return (isDay ? "d" : "n") + conditionCode;
	}

	public String getImageNameDay()
	{
		return "d" + conditionCode;
	}

	public String getWeekDay()
	{
		return (new SimpleDateFormat("EEEE")).format(timestamp * 1000);
	}

	public String getConditionText() {
		return conditionText;
	}

	public boolean isDay() {
		return isDay;
	}

	public static String getIconName(String url)
	{
		String[] iconSplitted = url.split("/");
		String[] icons = iconSplitted[iconSplitted.length - 1].split("\\.");
		return icons[0];
	}
}

package com.example.weatherforecast;

public class WeatherCondition {
	private float tempC, tempF, feelsLikeC, windKph, pressureMb, precipMm, uv, maxTempC, maxTempF, minTempC, minTempF;
	private int humidity, conditionCode;
	private long timestamp;
	private boolean isDay;

	public WeatherCondition(float tempC, float tempF, float feelsLikeC, float windKph, float pressureMb, float precipMm, float uv, float maxTempC, float maxTempF, float minTempC, float minTempF, int humidity, int conditionCode, long timestamp, boolean isDay) {
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
	}

	static class Builder
	{
		private float tempC, tempF, feelsLikeC, windKph, pressureMb, precipMm, uv, maxTempC, maxTempF, minTempC, minTempF;
		private int humidity, conditionCode;
		private long timestamp;
		private boolean isDay;
		Builder withTempC(float temp)
		{
			tempC = temp;
			return this;
		}
		Builder withTempF(float temp)
		{
			tempF = temp;
			return this;
		}
		Builder withFeelsLikeC(float temp)
		{
			feelsLikeC = temp;
			return this;
		}
		Builder withWindKph(float wind)
		{
			windKph = wind;
			return this;
		}
		Builder withPressureMb(float pressure)
		{
			pressureMb = pressure;
			return this;
		}
		Builder withPrecipMm(float precip)
		{
			precipMm = precip;
			return this;
		}
		Builder withUv(float uv)
		{
			this.uv = uv;
			return this;
		}
		Builder withMaxTempC(float temp)
		{
			maxTempC = temp;
			return this;
		}
		Builder withMinTempC(float temp)
		{
			minTempC = temp;
			return this;
		}
		Builder withMaxTempF(float temp)
		{
			maxTempF = temp;
			return this;
		}
		Builder withMinTempF(float temp)
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
		WeatherCondition build()
		{
			return new WeatherCondition(tempC, tempF, feelsLikeC, windKph, pressureMb, precipMm, uv, maxTempC, maxTempF, minTempC, minTempF, humidity, conditionCode, timestamp, isDay);
		}
	}
}

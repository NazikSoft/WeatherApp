package com.naziksoft.wethearapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HourWeather {
    @SerializedName("time")
    @Expose
    private long time;

    @SerializedName("temperature")
    @Expose
    private float temperature;

    @SerializedName("windSpeed")
    @Expose
    private float windSpeed;

    @SerializedName("icon")
    @Expose
    private String icon;

    public HourWeather(long time, float temperature, float windSpeed, String icon) {
        this.time = time;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }


    public long getTime() {
        return time;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    @Override
    public String toString() {
        return "HourWeather{" +
                "time='" + time + '\'' +
                ", temperature=" + temperature +
                ", windSpeed=" + windSpeed +
                ", icon='" + icon + '\'' +
                '}';
    }
}
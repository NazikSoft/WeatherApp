package com.naziksoft.wethearapp;


public class Constants {
    public static final String LOG = "WeatherLog";
    // DB
    public static final String DATABASE_NAME = "weather.db";
    public static final String TABLE_WEATHER = "weather";
    public static final int DATABASE_VERSION = 1;
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_WIND_SPEED = "wind";
    public static final String COLUMN_WEATHER_ICON = "icon";
    public static final double EPSILON = 0.000001;
    // ethernet
    public static final String EXCLUDE = "currently,minutely,daily,alerts,flags";
    public static final String BASE_URL = "https://api.darksky.net/";
    public static final String KEY = "424afc833729199adaf8a7d9f5231bff";

}

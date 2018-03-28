package com.naziksoft.wethearapp;

import com.naziksoft.wethearapp.db.WeatherDAO;
import com.naziksoft.wethearapp.entity.Coordinate;
import com.naziksoft.wethearapp.entity.HourWeather;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Utils {

    public static long convertUnixToMillis(long time) {
        // convert from UNIX time to millis
        return time * 1000L;
    }

    public static float convertTemperatureFToC(float fahrenheit) {
        // convert degrees from F to C
        return Math.round((fahrenheit - 32) * 5 / 9);
    }

    public static String formatData(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());
        return formatter.format(date);
    }

    public static float round(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
        return calendar.getTime();
    }

    public static Date atEndInTwoDays(Date date) {
        Date lastDate = new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastDate);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
        return calendar.getTime();
    }

    public static Date atStartHourBefore(Date date) {
        return new Date(date.getTime() - (4 * 60 * 60 * 1000));
    }

    public static Date atNextDay(Date date) {
        return new Date(date.getTime() + 24 * 60 * 60 * 1000);
    }

    public static List<HourWeather> convertEntity(List<WeatherDAO.WeatherDbEntity> weatherList) {
        List<HourWeather> result = new ArrayList<>();
        for (WeatherDAO.WeatherDbEntity dbEntity : weatherList) {
            result.add(new HourWeather(dbEntity.getTime(), dbEntity.getTemperature(), dbEntity.getWindSpeed(), dbEntity.getIcon()));
        }
        return result;
    }

    public static long atUnixTime(Date date) {
        return date.getTime() / 1000L;
    }

    public static List<WeatherDAO.WeatherDbEntity> convertEntity(Coordinate coordinate, List<HourWeather> weatherList) {
        List<WeatherDAO.WeatherDbEntity> result = new ArrayList<>();
        for (HourWeather entity : weatherList) {
            WeatherDAO.WeatherDbEntity convertedEntity = new WeatherDAO.WeatherDbEntity();
            String id = "" + entity.getTime() + coordinate.getLatitude() + coordinate.getLongitude();
            convertedEntity.setId(id);
            convertedEntity.setLatitude(coordinate.getLatitude());
            convertedEntity.setLongitude(coordinate.getLongitude());
            convertedEntity.setTemperature(entity.getTemperature());
            convertedEntity.setTime(entity.getTime());
            convertedEntity.setWindSpeed(entity.getWindSpeed());
            convertedEntity.setIcon(entity.getIcon());
            result.add(convertedEntity);
        }
        return result;
    }
}

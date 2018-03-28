package com.naziksoft.wethearapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nazar on 25.03.18.
 */

public class ServerResponse {

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("latitude")
    @Expose
    private float latitude;

    @SerializedName("longitude")
    @Expose
    private float longitude;

    @SerializedName("hourly")
    @Expose
    private Hourly hourly;

    public ServerResponse() {
    }

    public String getTimezone() {
        return timezone;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public Hourly getHourly() {
        return hourly;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "timezone='" + timezone + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", hourly=" + hourly +
                '}';
    }

    public class Hourly {
        @SerializedName("data")
        @Expose
        private List<HourWeather> data;

        public Hourly() {
        }

        public List<HourWeather> getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Hourly{" +
                    "data=" + data +
                    '}';
        }
    }
}

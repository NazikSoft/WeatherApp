package com.naziksoft.wethearapp.entity;

/**
 * Created by nazar on 25.03.18.
 */

public class ServerRequest {
    private String key;
    private float latitude;
    private float longitude;

    public ServerRequest(String key, float latitude, float longitude) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerRequest that = (ServerRequest) o;

        if (Float.compare(that.latitude, latitude) != 0) return false;
        if (Float.compare(that.longitude, longitude) != 0) return false;
        return key != null ? key.equals(that.key) : that.key == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServerRequest{" +
                "key='" + key + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude=" + longitude +
                '}';
    }
}

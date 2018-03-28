package com.naziksoft.wethearapp.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.naziksoft.wethearapp.Constants;
import com.naziksoft.wethearapp.Utils;
import com.naziksoft.wethearapp.entity.Coordinate;
import com.naziksoft.wethearapp.entity.HourWeather;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherDAO extends BaseDaoImpl<WeatherDAO.WeatherDbEntity, String> {

    public WeatherDAO(ConnectionSource connectionSource, Class<WeatherDAO.WeatherDbEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public boolean synchronize(Coordinate coordinate, List<HourWeather> list) {
        List<WeatherDAO.WeatherDbEntity> convertedList = Utils.convertEntity(coordinate, list);
        try {
            for (WeatherDAO.WeatherDbEntity hourWeather : convertedList) {
                String id = "" + hourWeather.getTime() + coordinate.getLatitude() + coordinate.getLongitude();
                if (!idExists(id)) {
                    create(hourWeather);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<HourWeather> getThreeDayWeather(Coordinate coordinate, Date date) {
        Date startDay = Utils.atStartHourBefore(date);
        Date endInTwoDays = Utils.atEndInTwoDays(date);
        List<HourWeather> weatherList = new ArrayList<>();
        try {
            List<WeatherDbEntity> dbEntities = queryBuilder()
                    .where()
                    .between(Constants.COLUMN_TIME, Utils.atUnixTime(startDay), Utils.atUnixTime(endInTwoDays))
                    .and()
                    .between(Constants.COLUMN_LATITUDE, coordinate.getLatitude() - Constants.EPSILON, coordinate.getLatitude() + Constants.EPSILON)
                    .and()
                    .between(Constants.COLUMN_LONGITUDE, coordinate.getLongitude() - Constants.EPSILON, coordinate.getLongitude() + Constants.EPSILON)
                    .query();
            weatherList = Utils.convertEntity(dbEntities);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    // entity for database
    @DatabaseTable(tableName = Constants.TABLE_WEATHER, daoClass = WeatherDAO.class)
    public static class WeatherDbEntity {
        @DatabaseField(dataType = DataType.STRING, id = true)
        private String id;

        @DatabaseField(dataType = DataType.LONG, columnName = Constants.COLUMN_TIME)
        private long time;

        @DatabaseField(dataType = DataType.FLOAT, columnName = Constants.COLUMN_LATITUDE)
        private float latitude;

        @DatabaseField(dataType = DataType.FLOAT, columnName = Constants.COLUMN_LONGITUDE)
        private float longitude;

        @DatabaseField(dataType = DataType.FLOAT, columnName = Constants.COLUMN_TEMPERATURE)
        private float temperature;

        @DatabaseField(dataType = DataType.FLOAT, columnName = Constants.COLUMN_WIND_SPEED)
        private float windSpeed;

        @DatabaseField(dataType = DataType.STRING, columnName = Constants.COLUMN_WEATHER_ICON)
        private String icon;

        public WeatherDbEntity() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(float windSpeed) {
            this.windSpeed = windSpeed;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WeatherDbEntity that = (WeatherDbEntity) o;

            return id != null ? id.equals(that.id) : that.id == null;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "WeatherDbEntity{" +
                    "id=" + id +
                    ", time=" + time +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", temperature=" + temperature +
                    ", windSpeed=" + windSpeed +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }
}


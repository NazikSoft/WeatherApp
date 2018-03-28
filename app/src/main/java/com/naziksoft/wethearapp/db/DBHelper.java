package com.naziksoft.wethearapp.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.naziksoft.wethearapp.Constants;
import com.naziksoft.wethearapp.entity.HourWeather;

import java.sql.SQLException;


public class DBHelper extends OrmLiteSqliteOpenHelper {

    private WeatherDAO weatherDAO = null;

    public DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, WeatherDAO.WeatherDbEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, WeatherDAO.WeatherDbEntity.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, WeatherDAO.WeatherDbEntity.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // singleton for WeatherDAO
    public WeatherDAO getWeatherDAO() {
        try {
            if (weatherDAO == null) {
                weatherDAO = new WeatherDAO(getConnectionSource(), WeatherDAO.WeatherDbEntity.class);
            }
            return weatherDAO;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        super.close();
        weatherDAO = null;
    }
}

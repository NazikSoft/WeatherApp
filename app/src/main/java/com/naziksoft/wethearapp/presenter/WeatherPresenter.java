package com.naziksoft.wethearapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.naziksoft.wethearapp.Constants;
import com.naziksoft.wethearapp.R;
import com.naziksoft.wethearapp.db.DBHelperFactory;
import com.naziksoft.wethearapp.db.WeatherDAO;
import com.naziksoft.wethearapp.entity.Coordinate;
import com.naziksoft.wethearapp.entity.HourWeather;
import com.naziksoft.wethearapp.entity.ServerRequest;
import com.naziksoft.wethearapp.entity.ServerResponse;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherPresenter {

    private Context context;
    private ServerConnection connection;
    private OnServerResult feedback;
    private WeatherDAO weatherDAO;

    public interface OnServerResult {
        void onResult(List<HourWeather> weatherList);

        void onError(String message);
    }

    public WeatherPresenter(Context context, OnServerResult feedback) {
        this.context = context;
        this.feedback = feedback;
        initConnection();
        weatherDAO = DBHelperFactory.getDbHelper().getWeatherDAO();
    }

    public void getWeather(Coordinate coordinate) {
        if (isDeviceOnline()) {
            getDataFromServer(new ServerRequest(Constants.KEY, coordinate.getLatitude(), coordinate.getLongitude()));
        } else {
            Log.d(Constants.LOG, "no internet connection");
            feedback.onError(context.getString(R.string.internet_connection_error));
            getDataFromDB(coordinate);
        }
    }

    private void getDataFromDB(Coordinate coordinate) {
        Date currentTime = new Date();
        List<HourWeather> weatherList = weatherDAO.getThreeDayWeather(coordinate, currentTime);
        feedback.onResult(weatherList);
    }

    private void initConnection() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        connection = retrofit.create(ServerConnection.class);
    }

    private void getDataFromServer(final ServerRequest request) {
        String exclude = Constants.EXCLUDE;
        connection.getFromServer(Constants.KEY, request.getLatitude(), request.getLongitude(), exclude)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                        ServerResponse data = response.body();
                        if (data == null) {
                            Log.d(Constants.LOG, "empty request body");
                            return;
                        }
                        Log.d(Constants.LOG, "request OK, + coordinate=" + data.getLatitude() + "," + data.getLatitude());
                        saveToDB(data);
                        feedback.onResult(data.getHourly().getData());
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                        Log.d(Constants.LOG, "request failure");
                        t.printStackTrace();
                        feedback.onError(context.getString(R.string.request_connection_error));
                    }
                });
    }

    @SuppressLint("StaticFieldLeak")
    private void saveToDB(final ServerResponse response) {
         new AsyncTask<ServerResponse,Void,Void>() {
             @Override
             protected Void doInBackground(ServerResponse... serverResponses) {
                 ServerResponse response = serverResponses[0];
                 Coordinate coordinate = new Coordinate(response.getLatitude(), response.getLongitude());
                 List<HourWeather> weatherList = response.getHourly().getData();
                 weatherDAO.synchronize(coordinate, weatherList);
                 return null;
             }
            }.execute(response);
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return false;
        }
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

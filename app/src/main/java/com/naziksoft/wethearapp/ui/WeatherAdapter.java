package com.naziksoft.wethearapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naziksoft.wethearapp.R;
import com.naziksoft.wethearapp.Utils;
import com.naziksoft.wethearapp.entity.HourWeather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.CanalViewHolder> {

    private Context context;
    private List<HourWeather> list;

    public WeatherAdapter(List<HourWeather> weatherList) {
        list = weatherList;
    }

    @NonNull
    @Override
    public CanalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather_per_hours, parent, false);
        return new CanalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanalViewHolder holder, int position) {
        HourWeather weather = list.get(position);
        // icon
        setIcon(weather.getIcon(), holder.weatherIcon);

        // time
        Date date = new Date(Utils.convertUnixToMillis(weather.getTime()));
        holder.tvTime.setText(Utils.formatData(date));

        // temperature
        String temperature = Utils.convertTemperatureFToC(weather.getTemperature()) + context.getString(R.string.celsius);
        holder.tvTemp.setText(temperature);

        // wind
        String windSpeed = Utils.round(weather.getWindSpeed(), 1) + context.getString(R.string.meter_per_sec);
        holder.tvWind.setText(windSpeed);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CanalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_weather_icon)
        ImageView weatherIcon;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_temp)
        TextView tvTemp;
        @BindView(R.id.tv_wind)
        TextView tvWind;

        CanalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setIcon(String weatherState, ImageView icon) {
        int iconId = R.mipmap.ic_launcher;
        switch (weatherState) {
            case "clear-day":
                iconId = R.drawable.clear_sky;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.cloud_sunny;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloud_night;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "cloudy":
                iconId = R.drawable.cloud;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "rain":
                iconId = R.drawable.light_showers;
                break;
            case "heavy-rain":
                iconId = R.drawable.heavy_showers;
                break;
            case "thunder":
                iconId = R.drawable.thunder;
                break;
        }
        Picasso.get().load(iconId).into(icon);
    }
}

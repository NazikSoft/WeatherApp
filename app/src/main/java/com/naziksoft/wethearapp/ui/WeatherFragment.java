package com.naziksoft.wethearapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naziksoft.wethearapp.R;
import com.naziksoft.wethearapp.entity.HourWeather;
import com.naziksoft.wethearapp.entity.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<HourWeather> weatherList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    public void setWeatherData(List<HourWeather> weatherList){
        this.weatherList = weatherList;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        WeatherAdapter adapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(adapter);
    }
}

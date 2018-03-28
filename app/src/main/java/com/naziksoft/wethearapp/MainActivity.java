package com.naziksoft.wethearapp;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.naziksoft.wethearapp.entity.Coordinate;
import com.naziksoft.wethearapp.entity.HourWeather;
import com.naziksoft.wethearapp.presenter.WeatherPresenter;
import com.naziksoft.wethearapp.ui.ViewPagerAdapter;
import com.naziksoft.wethearapp.ui.WeatherFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WeatherPresenter.OnServerResult {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ViewPagerAdapter adapter;
    private WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupTabUI();
        initSpinner();
        presenter = new WeatherPresenter(this, this);
    }

    @Override
    public void onResult(List<HourWeather> weatherList) {
        progressBar.setVisibility(View.GONE);
        adapter.updateData(weatherList);
    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();
    }

    private void setupTabUI() {
        initAdapter();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                Coordinate coordinate = getCoordinateData(position);
                presenter.getWeather(coordinate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(0);
    }

    private void initAdapter() {
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.title_fragment_today));
        titles.add(getString(R.string.title_fragment_tomorrow));
        titles.add(getString(R.string.title_fragment_after_tomorrow));
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),titles);
    }

    private Coordinate getCoordinateData(int position) {
        String[] coordinates = getResources().getStringArray(R.array.spinner_coordinates);
        String[] parsedData = coordinates[position].split(";");
        float latitude = Float.valueOf(parsedData[0]);
        float longitude = Float.valueOf(parsedData[1]);
        return new Coordinate(latitude, longitude);
    }
}

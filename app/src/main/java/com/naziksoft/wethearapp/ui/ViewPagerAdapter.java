package com.naziksoft.wethearapp.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.naziksoft.wethearapp.Utils;
import com.naziksoft.wethearapp.entity.HourWeather;
import com.naziksoft.wethearapp.entity.ServerResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> fragmentTitleList = new ArrayList<>();
    private List<List<HourWeather>> fragmentsData = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        setTitles(titles);
        setDefaultFragmentData();
    }

    @Override
    public Fragment getItem(int position) {
        WeatherFragment fragment = new WeatherFragment();
        if (position < fragmentsData.size()) {
            fragment.setWeatherData(fragmentsData.get(position));
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    // the only way to start working notifyDataSetChanged()
    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void updateData(List<HourWeather> weatherList) {
        //clear current fragment data
        setDefaultFragmentData();
        // update fragment data
        int index = 0;
        Date date = new Date();
        for (HourWeather hourWeather : weatherList) {
            Date weatherDate = new Date(Utils.convertUnixToMillis(hourWeather.getTime()));
            if (weatherDate.after(Utils.atEndOfDay(date))) {
                ++index;
                date = Utils.atNextDay(date);
            }
            fragmentsData.get(index).add(hourWeather);
        }
        notifyDataSetChanged();
    }

    private void setTitles(List<String> titles) {
        fragmentTitleList = titles;
    }

    private void setDefaultFragmentData() {
        fragmentsData = new ArrayList<>();
        for (int counter = 0; counter < fragmentTitleList.size(); counter++) {
            fragmentsData.add(new ArrayList<HourWeather>());
        }
    }
}
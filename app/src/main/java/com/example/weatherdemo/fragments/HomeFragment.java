package com.example.weatherdemo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherdemo.MainActivity;
import com.example.weatherdemo.R;
import com.example.weatherdemo.Utils.JsonUtils;
import com.example.weatherdemo.entities.CityInfo;
import com.example.weatherdemo.home_page;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private TextView tv_ptime, tv_city, tv_weather, tv_maxTemp, tv_temp1, tv_temp2;

    private CityInfo cityInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_home_page, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        //默认条件下的初始化
        initCityInfoFromJson();
    }


    private void initView() {
        tv_ptime = getActivity().findViewById(R.id.tv_ptime);
        tv_city = getActivity().findViewById(R.id.tv_city);
        tv_weather = getActivity().findViewById(R.id.tv_weather);
        tv_maxTemp = getActivity().findViewById(R.id.tv_maxTemp);
        tv_temp1 = getActivity().findViewById(R.id.tv_temp1);
        tv_temp2 = getActivity().findViewById(R.id.tv_temp2);
    }

    /**
     * 请求城市网络资源
     */
    private void initCityInfoFromJson() {

        System.out.println("开始进行城市信息初始化");

        //请求网络资源
        Handler mainHandler = new Handler(Looper.getMainLooper());
        String url = "http://www.weather.com.cn/data/cityinfo/101280101.html";

        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    final String str = response.body().string();

                    System.out.println("CityInfo：" + str);

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            cityInfo = JsonUtils.getInstance()
                                    .getCityInfoFromJson(getActivity(), str);
                            chgCityData();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void chgCityData() {
        tv_ptime.setText(cityInfo.getPtime());
        tv_city.setText(cityInfo.getCity());
        tv_weather.setText(cityInfo.getWeather());
        tv_maxTemp.setText(cityInfo.getTemp2());
        tv_temp2.setText("最高 "+cityInfo.getTemp2());
        tv_temp1.setText("最低 "+cityInfo.getTemp1());
    }

}

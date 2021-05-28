package com.example.weatherdemo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherdemo.Bean.WeatherBean;
import com.example.weatherdemo.R;
import com.example.weatherdemo.Utils.JsonUtils;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";

    private TextView tv_ptime2, tv_city2, tv_weather2, tv_maxTemp2, tv_temp12, tv_temp22, tv_update2;
    private TextView tv_pm25,tv_pm10,tv_shidu,tv_fengsu,tv_sunrise,tv_sunset,tv_notice;

    private WeatherBean weatherBean = null;
    private String str = "空";

    private boolean isGetData = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_details_page, null);
        return inflate;
    }

//    @Override
//    public Animation onCreateAnimation(int transit,boolean enter,int nextAnim){
//        //进入当前Fragment
//        if(enter&&isGetData){
//            isGetData=true;
//            //这里可以做网络请求或者需要数据刷新的操作
//            initWeatherInfoFromJson2();
//        }else{
//            isGetData=false;
//        }
//        return super.onCreateAnimation(transit,enter,nextAnim);
//    }

    @Override
    public void onResume() {
        if (!isGetData) {
            initView();
            //这里可以做网络请求或者需要数据刷新的操作
            initWeatherInfoFromJson2();
            isGetData=true;
        }
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        isGetData=false;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {

        tv_ptime2 = getActivity().findViewById(R.id.tv_ptime2);
        tv_city2 = getActivity().findViewById(R.id.tv_city2);
        tv_weather2 = getActivity().findViewById(R.id.tv_weather2);
        tv_maxTemp2 = getActivity().findViewById(R.id.tv_maxTemp2);
        tv_temp12 = getActivity().findViewById(R.id.tv_temp12);
        tv_temp22 = getActivity().findViewById(R.id.tv_temp22);
        tv_update2 = getActivity().findViewById(R.id.tv_update2);

        tv_pm25=getActivity().findViewById(R.id.tv_pm25);
        tv_pm10=getActivity().findViewById(R.id.tv_pm10);
        tv_shidu=getActivity().findViewById(R.id.tv_shidu);
        tv_fengsu=getActivity().findViewById(R.id.tv_fengsu);
        tv_sunrise=getActivity().findViewById(R.id.tv_sunrise);
        tv_sunset=getActivity().findViewById(R.id.tv_sunset);
        tv_notice=getActivity().findViewById(R.id.tv_notice);

    }

    /**
     * 请求城市预报天气信息网络资源
     */
    private void initWeatherInfoFromJson2() {
        //请求网络资源
        Handler mainHandler = new Handler(Looper.getMainLooper());
        String url = "http://t.weather.itboy.net/api/weather/city/101280101";

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

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            weatherBean = JsonUtils.getInstance()
                                    .getWeatherFromJson(getActivity(), str);

                            Log.d(TAG, "detailsFragment：" + str);
                            //给详情页更替信息
                            chgDetailsData();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void chgDetailsData() {
        tv_ptime2.setText(weatherBean.getTime());
        tv_city2.setText(weatherBean.getCityInfo().getCity());
        tv_weather2.setText(weatherBean.getData().getForecast().get(0).getType() + "  " + "空气" + weatherBean.getData().getQuality());
        tv_maxTemp2.setText(weatherBean.getData().getWendu() + "℃");
        tv_temp22.setText(weatherBean.getData().getForecast().get(0).getHigh());
        tv_temp12.setText(weatherBean.getData().getForecast().get(0).getLow());

        tv_pm25.setText(weatherBean.getData().getPm25());
        tv_pm10.setText(weatherBean.getData().getPm10());
        tv_shidu.setText(weatherBean.getData().getShidu());
        tv_fengsu.setText(weatherBean.getData().getForecast().get(0).getFx() + "    "  + weatherBean.getData().getForecast().get(0).getFl());
        tv_sunrise.setText(weatherBean.getData().getForecast().get(0).getSunrise());
        tv_sunset.setText(weatherBean.getData().getForecast().get(0).getSunset());
        tv_notice.setText(weatherBean.getData().getForecast().get(0).getNotice());
    }

}

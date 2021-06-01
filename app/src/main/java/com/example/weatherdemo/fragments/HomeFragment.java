package com.example.weatherdemo.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherdemo.Bean.WeatherBean;
import com.example.weatherdemo.Bean.forecast;
import com.example.weatherdemo.BroadCastManager;
import com.example.weatherdemo.R;
import com.example.weatherdemo.Utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private static final String CITYKEY = "101280101";

    private TextView tv_ptime, tv_city, tv_weather, tv_maxTemp, tv_temp1, tv_temp2, tv_update;
    private ListView lv_yugao;

    private WeatherBean weatherBean;

    private boolean isGetData = false;
    private LocalReceiver mReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_home_page, null);
        return inflate;
    }

    public WeatherBean getWeatherBean() {
        return weatherBean;
    }

    public void setWeatherBean(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initWeatherInfoFromJson(CITYKEY);
    }

    @Override
    public void onResume() {

        initView();
        //接收广播
        try {
            mReceiver = new LocalReceiver();
            IntentFilter filter=new IntentFilter("cityKey");
            BroadCastManager.getInstance().registerReceiver(getActivity(),mReceiver,filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isGetData = true;
        super.onResume();
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播后的处理
            String citykey = intent.getStringExtra("citykey");
            if(citykey!=null){
                initWeatherInfoFromJson(citykey);
                Log.d(TAG, "更新成功！");
                abortBroadcast();
            }
        }
    }

    @Override
    public void onDestroy() {
        BroadCastManager.getInstance().unregisterReceiver(getActivity(), mReceiver);//注销广播接收者
        super.onDestroy();
    }

    private void initView() {
        tv_ptime = getActivity().findViewById(R.id.tv_ptime2);
        tv_city = getActivity().findViewById(R.id.tv_city2);
        tv_weather = getActivity().findViewById(R.id.tv_weather2);
        tv_maxTemp = getActivity().findViewById(R.id.tv_maxTemp2);
        tv_temp1 = getActivity().findViewById(R.id.tv_temp12);
        tv_temp2 = getActivity().findViewById(R.id.tv_temp22);
        tv_update = getActivity().findViewById(R.id.tv_update2);

        lv_yugao = getActivity().findViewById(R.id.lv_yugao);
    }
    
    /**
     * 请求城市预报天气信息网络资源
     */
    private void initWeatherInfoFromJson(String citykey) {
        //请求网络资源
        Handler mainHandler = new Handler(Looper.getMainLooper());
        String url = "http://t.weather.itboy.net/api/weather/city/" + citykey;

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
                            Log.d(TAG,str);

                            //给首页更替信息
                            chgHomeData();
                            //给listView配上适配器
                            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.activity_yugao_item, new String[]{
                                    "tv_ymd", "tv_week", "iv_pic", "tv_high", "tv_low"}, new int[]{R.id.tv_ymd, R.id.tv_week, R.id.iv_pic, R.id.tv_high, R.id.tv_low});
                            lv_yugao.setAdapter(simpleAdapter);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        List<forecast> forecasts = weatherBean.getData().getForecast();

        for (forecast shuju : forecasts) {
            //因为每次数据是要刷新的，所以要new一个新的
            map = new HashMap<String, Object>();

            map.put("tv_ymd", shuju.getYmd());
            map.put("tv_week", shuju.getWeek());
            switch (shuju.getType()) {
                case "小雨":
                    map.put("iv_pic", R.drawable.sprinkle);
                    break;
                case "中雨":
                    map.put("iv_pic", R.drawable.middle_rain);
                    break;
                case "大雨":
                    map.put("iv_pic", R.drawable.heavy_rain);
                    break;
                case "暴雨":
                    map.put("iv_pic", R.drawable.catsanddog);
                    break;
                case "多云":
                    map.put("iv_pic", R.drawable.cloudy);
                    break;
                case "阴":
                    map.put("iv_pic", R.drawable.gloomy);
                    break;
                case "雷阵雨":
                    map.put("iv_pic", R.drawable.thouderstorm);
                    break;
                case "晴":
                    map.put("iv_pic", R.drawable.sunny);
                    break;
                case "阵雨":
                    map.put("iv_pic", R.drawable.showery_rain);
                    break;
                default:
                    break;
            }
            map.put("tv_high", shuju.getHigh());
            map.put("tv_low", shuju.getLow());

            list.add(map);
        }

        return list;
    }

    private void chgHomeData() {
        tv_ptime.setText(weatherBean.getTime());
        tv_city.setText(weatherBean.getCityInfo().getCity());
        tv_weather.setText(weatherBean.getData().getForecast().get(0).getType() + "  " + "空气" + weatherBean.getData().getQuality());
        tv_maxTemp.setText(weatherBean.getData().getWendu() + "℃");
        tv_temp2.setText(weatherBean.getData().getForecast().get(0).getHigh());
        tv_temp1.setText(weatherBean.getData().getForecast().get(0).getLow());
    }
}

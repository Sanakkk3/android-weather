package com.example.weatherdemo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

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
    private WeatherBean weatherBean = null;
    private String str = "空";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_details_page, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initWeatherInfoFromJson();

    }

    /**
     * 可以对fragment的hide和show状态进行监听
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            //Fragment隐藏时调用
        }else {
            //Fragment显示时调用
            Bundle bundle = getArguments();
            str = bundle.getString("1");
            Log.d(TAG,"str:"+str);
        }
    }

    private void initView() {

    }

    /**
     * 请求城市预报天气信息网络资源
     */
    private void initWeatherInfoFromJson() {
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

                            //给详情页更替信息
                            chgDetailsData();

                            Log.d(TAG, "我又来测试了：" + weatherBean.getMessage());

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void chgDetailsData() {

    }

}

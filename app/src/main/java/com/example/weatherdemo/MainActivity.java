package com.example.weatherdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherdemo.Utils.JsonUtils;
import com.example.weatherdemo.entities.CityInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2 = null;

    private ArrayList<View> viewContainter = new ArrayList<View>();

    private CityInfo cityInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        viewPager2 = findViewById(R.id.vp_show);

        View homeView = LayoutInflater.from(this).inflate(R.layout.activity_home_page, null);
        View detailsView = LayoutInflater.from(this).inflate(R.layout.activity_details_page, null);

        //  viewPager2添加view
        viewContainter.add(homeView);
        viewContainter.add(detailsView);

        //设置Adapter
        viewPager2.setAdapter(new MyPagerAdapters());
    }

    /**
     * ViewPager2数据适配器
     */
    class MyPagerAdapters extends PagerAdapter {

        //返回可以滑动的VIew的个数
        @Override
        public int getCount() {
            return viewContainter.size();
        }

        //滑动切换的时候销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            ((ViewPager2) container).removeView(viewContainter.get(position));
        }

        //将当前视图添加到container中并返回当前View视图
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager2) container).addView(viewContainter.get(position));
            return viewContainter.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void initCityInfoFromJson() {
        //请求网络资源
        Handler mainHandler = new Handler(getMainLooper());
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
                                    .getCityInfoFromJson(MainActivity.this, str);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}


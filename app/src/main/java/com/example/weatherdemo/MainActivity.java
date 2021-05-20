package com.example.weatherdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;

import com.example.weatherdemo.Utils.JsonUtils;
import com.example.weatherdemo.Utils.MyAdapter;
import com.example.weatherdemo.entities.CityInfo;
import com.example.weatherdemo.fragments.DetailsFragment;
import com.example.weatherdemo.fragments.HomeFragment;
import com.example.weatherdemo.fragments.SettingsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 myPager2;
    private TabLayout myTab;

    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();

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

        myPager2 = findViewById(R.id.vp2_show);
        myTab = findViewById(R.id.tb_01);

        //添加标题
        titles.add("首页");
        titles.add("详情页");
        titles.add("设置页");

        //添加Fragment
        fragments.add(new HomeFragment());
        fragments.add(new DetailsFragment());
        fragments.add(new SettingsFragment());

        //实例化设配器
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), getLifecycle(), fragments);

        //设置适配器
        myPager2.setAdapter(myAdapter);

        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(myTab, myPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        }).attach();
    }


    /**
     * 请求城市网络资源
     */
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

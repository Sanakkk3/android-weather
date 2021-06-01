package com.example.weatherdemo.Utils;

import android.content.Context;

import com.example.weatherdemo.Bean.WeatherBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class JsonUtils {

    public static JsonUtils instance; //单例模式

    private static final String TAG = "JsonUtils";

    private JsonUtils() {

    }

    public static JsonUtils getInstance() {
        if (instance == null) {
            instance = new JsonUtils();
        }
        return instance;
    }

    /**
     * 读取输入文件内容
     *
     * @param is 输入流
     * @return json字符串
     */
    private String read(InputStream is) {
        BufferedReader reader = null; //带缓存的读取器
        StringBuilder sb = null;  //字符串构造器

        String line = null;

        try {
            sb = new StringBuilder();
            //从is中读取内容
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (is != null) is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * 从文件中读取  城市天气预告信息
     *
     * @param context
     * @return
     */
    public WeatherBean getWeatherFromFile(Context context) {
        WeatherBean weatherBean = new WeatherBean();
        InputStream is = null;

        try {
            is = context.getResources().getAssets().open("weather.json");
            String json = read(is);

            Gson gson = new Gson();
            weatherBean = gson.fromJson(json, WeatherBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherBean;
    }

    /**
     * 从网络中读取  城市天气预告信息
     *
     * @param context
     * @param json
     * @return
     */
    public WeatherBean getWeatherFromJson(Context context, String json) {
        WeatherBean weatherBean = new WeatherBean();

        Gson gson = new Gson();
        weatherBean = gson.fromJson(json, WeatherBean.class);

        return weatherBean;
    }
}

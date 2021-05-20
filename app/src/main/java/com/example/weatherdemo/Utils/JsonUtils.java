package com.example.weatherdemo.Utils;

import android.content.Context;

import com.example.weatherdemo.MainActivity;
import com.example.weatherdemo.entities.CityInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.FileStore;

public class JsonUtils {

    public static JsonUtils instance; //单例模式

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

    public CityInfo getCityInfoFromFile(Context context) {

        CityInfo cityInfo = new CityInfo();
        InputStream is = null;

        try {
            is = context.getResources().getAssets().open("cityinfo.json");
            String json = read(is);

            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonData = jsonObject.getJSONObject("weatherinfo");

            Gson gson = new Gson();
            //通过反射机制，定义一类型解析器
            Type type = new TypeToken<CityInfo>() {
            }.getType();
            //  使用gson库实例，解析jsonData.toString()字符串
            cityInfo = gson.fromJson(jsonData.toString(), type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityInfo;
    }

    public CityInfo getCityInfoFromJson(Context context, String json) {

        CityInfo cityInfo=new CityInfo();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonDate = jsonObject.getJSONObject("weatherinfo");
            Gson gson = new Gson();
            cityInfo = gson.fromJson(String.valueOf(jsonDate), (Type) CityInfo.class);

            System.out.println(cityInfo.getCity());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return cityInfo;
    }
}

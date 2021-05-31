package com.example.weatherdemo.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //收到广播后的处理
        String citykey=intent.getStringExtra("citykey");
        loadData(citykey);
    }
    private void loadData(String citykey){

    }
}

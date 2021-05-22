package com.example.weatherdemo.Bean;

import com.example.weatherdemo.entities.CityInfo;

public class cityInfo {

    private String city;
    private String citykey;
    private String parent;
    private String updateTime;

    public cityInfo(){

    }

    public cityInfo(String city, String citykey, String parent, String updateTime) {
        this.city = city;
        this.citykey = citykey;
        this.parent = parent;
        this.updateTime = updateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitykey() {
        return citykey;
    }

    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

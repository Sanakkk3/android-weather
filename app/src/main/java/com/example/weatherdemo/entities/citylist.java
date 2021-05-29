package com.example.weatherdemo.entities;

public class citylist {
    private String citykey;
    private String city;
    private String province;

    public citylist(String citykey, String city, String province) {
        this.citykey = citykey;
        this.city = city;
        this.province = province;
    }

    public String getCitykey() {
        return citykey;
    }

    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}

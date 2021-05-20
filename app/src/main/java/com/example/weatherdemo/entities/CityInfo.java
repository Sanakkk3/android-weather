package com.example.weatherdemo.entities;

public class CityInfo {
    private String city;
    private String cityid;
    private String temp1;
    private String temp2;
    private String weather;
    private String imag1;
    private String imag2;
    private String ptime;

    public CityInfo(){

    }

    public CityInfo(String city, String cityid, String temp1, String temp2, String weather, String imag1, String imag2, String ptime) {
        this.city = city;
        this.cityid = cityid;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.weather = weather;
        this.imag1 = imag1;
        this.imag2 = imag2;
        this.ptime = ptime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getImag1() {
        return imag1;
    }

    public void setImag1(String imag1) {
        this.imag1 = imag1;
    }

    public String getImag2() {
        return imag2;
    }

    public void setImag2(String imag2) {
        this.imag2 = imag2;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
}


package com.example.weatherdemo.Bean;

public class WeatherBean {

    private String message;
    private String status;
    private String date;
    private String time;

    private cityInfo cityInfo;
    private data data;

    public WeatherBean(){

    }

    public WeatherBean(String message, String status, String date, String time, com.example.weatherdemo.Bean.cityInfo cityInfo, com.example.weatherdemo.Bean.data data) {
        this.message = message;
        this.status = status;
        this.date = date;
        this.time = time;
        this.cityInfo = cityInfo;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public com.example.weatherdemo.Bean.cityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(com.example.weatherdemo.Bean.cityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public com.example.weatherdemo.Bean.data getData() {
        return data;
    }

    public void setData(com.example.weatherdemo.Bean.data data) {
        this.data = data;
    }
}

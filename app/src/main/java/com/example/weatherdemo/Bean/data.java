package com.example.weatherdemo.Bean;

import java.util.List;

import lombok.Data;

@Data
public class data {

    private String shidu;
    private String pm25;
    private String pm10;
    private String quality;
    private String wendu;
    private String ganmao;

    private List<forecast> forecast;

    public data(){

    }

    public data(String shidu, String pm25, String pm10, String quality, String wendu, String ganmao, List<forecast> forecast) {
        this.shidu = shidu;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.quality = quality;
        this.wendu = wendu;
        this.ganmao = ganmao;
        this.forecast = forecast;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public List<forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<forecast> forecast) {
        this.forecast = forecast;
    }
}

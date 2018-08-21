package com.hanyutech.hanyuiot01;

public class Data {

    private double tempture,humidity,latitude,longitude;

    public Data(double tempture, double humidity, double latitude, double longitude) {
        this.tempture = tempture;
        this.humidity = humidity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getTempture() {
        return tempture;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setTempture(double tempture) {
        this.tempture = tempture;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

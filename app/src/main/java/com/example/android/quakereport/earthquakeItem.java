package com.example.android.quakereport;

public class earthquakeItem {
    private double mMagnitude;
    private String mLocation;
    private long mDate;
    private String mUrl;

    public earthquakeItem(double magnitude, String location, long date, String url){
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
        mUrl = url;
    }

    public double getMagnitude(){
        return mMagnitude;
    }

    public String getLocations(){
        return mLocation;
    }

    public long getDate(){ return mDate; }

    public String getUrl(){ return mUrl; }
}

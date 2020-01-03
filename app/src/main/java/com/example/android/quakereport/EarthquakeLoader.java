package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<earthquakeItem>> {

    /** Query URL */
    private String mUrl;

    private static final String LOG_TAG = EarthquakeActivity.class.getName();


    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.d( LOG_TAG, "TEST: onStartLoading");
        forceLoad();
    }

    @Override
    public List<earthquakeItem> loadInBackground() {
        Log.d( LOG_TAG, "TEST: loadInBackground");
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }
        ArrayList<earthquakeItem> earthquakeItems = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakeItems;
    }
}
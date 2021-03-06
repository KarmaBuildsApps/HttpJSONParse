package com.waracle.androidtest.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.AsyncTaskLoader;

import com.waracle.androidtest.Constant;
import com.waracle.androidtest.Utils.StreamUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Karma on 16/05/16.
 */
public class BackgroundDataLoader extends AsyncTaskLoader<JSONArray> {
    private final String url;
    private JSONArray jsonArray;

    public BackgroundDataLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        if (jsonArray == null)
            forceLoad();
        else
            deliverResult(jsonArray);
    }

    @Override
    public JSONArray loadInBackground() {
        JSONArray jsonArray = JSONLoader.loadData(url);
        return jsonArray;
    }

    @Override
    public void deliverResult(JSONArray jsonArray) {
        if (jsonArray != null)
            this.jsonArray = jsonArray;
        super.deliverResult(jsonArray);
    }
}

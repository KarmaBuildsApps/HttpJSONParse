package com.waracle.androidtest.Presenter;

import android.graphics.Bitmap;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Karma on 13/05/16.
 */
public interface CakeListView {
    public void loadCakeData(String url);

    public ArrayList<Bitmap> loadImageData(JSONArray jsonArray);
}

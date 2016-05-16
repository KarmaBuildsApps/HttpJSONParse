package com.waracle.androidtest.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.waracle.androidtest.Constant;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Karma on 16/05/16.
 */
public class BackgroundImageLoader extends AsyncTaskLoader<ArrayList<Bitmap>> {
    private final String TAG = BackgroundImageLoader.class.getSimpleName();

    private final JSONArray jsonArray;
    private ArrayList<Bitmap> bitmaps;

    public BackgroundImageLoader(Context context, JSONArray jsonArray) {
        super(context);
        this.jsonArray = jsonArray;
    }

    @Override
    protected void onStartLoading() {
        if (bitmaps == null)
            forceLoad();
        else
            deliverResult(bitmaps);
    }

    @Override
    public ArrayList<Bitmap> loadInBackground() {
        ArrayList<Bitmap> arrayList = new ArrayList<>();
        String imageURL = "";
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    imageURL = jsonArray.getJSONObject(i).getString(Constant.JSON_CAKE_IMAGE);
                    Bitmap bitmap = ImageLoader.convertToBitmap(ImageLoader.loadImageData(imageURL));
                    arrayList.add(bitmap);
                } catch (JSONException e) {
                    Log.d(TAG, "loadInBackground: JSON Error");
                } catch (IOException e) {
                    Log.d(TAG, "loadInBackground: Loading image from Internet Error");
                }
            }
        }
        return arrayList;
    }

    @Override
    public void deliverResult(ArrayList<Bitmap> data) {
        bitmaps = data;
        super.deliverResult(bitmaps);
    }
}

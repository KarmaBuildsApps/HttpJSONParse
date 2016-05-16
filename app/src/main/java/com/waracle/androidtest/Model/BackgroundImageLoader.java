package com.waracle.androidtest.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.waracle.androidtest.Constant;
import com.waracle.androidtest.Utils.StreamUtils;

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

    public BackgroundImageLoader(Context context, JSONArray jsonArray) {
        super(context);
        this.jsonArray = jsonArray;
    }

    @Override
    public ArrayList<Bitmap> loadInBackground() {
        ArrayList<Bitmap> arrayList = null;
        String imageURL = "";
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    imageURL = jsonArray.getJSONObject(i).getString(Constant.JSON_CAKE_IMAGE);
                    Bitmap bitmap = ImageLoader.convertToBitmap(ImageLoader.loadImageData(imageURL));
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
        super.deliverResult(data);
    }
}

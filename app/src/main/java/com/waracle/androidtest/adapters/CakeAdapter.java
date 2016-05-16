package com.waracle.androidtest.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.Constant;
import com.waracle.androidtest.Model.BackgroundImageLoader;
import com.waracle.androidtest.Model.ImageLoader;
import com.waracle.androidtest.R;
import com.waracle.androidtest.View.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Karma on 12/05/16.
 */
public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.ViewHolder> {
    public final String TAG = CakeAdapter.class.getSimpleName();

    private final Context context;
    private JSONArray jsonArray;
    private ArrayList<Bitmap> cakeImageBitmaps;

    public CakeAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            String url = "";
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            holder.title.setText(jsonObject.getString(Constant.JSON_CAKE_TITLE));
            holder.desc.setText(jsonObject.getString(Constant.JSON_CAKE_DESC));
            url = jsonObject.getString(Constant.JSON_CAKE_IMAGE);
            Log.i(TAG, "onBindViewHolder: URL: " + url);
            if (cakeImageBitmaps != null)
                holder.listImage.setImageBitmap(cakeImageBitmaps.get(position));
        } catch (JSONException e) {
            Log.d(TAG, "onBindViewHolder: JSONObject read error");
        }
    }


    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public void setBitmaps(ArrayList<Bitmap> data) {
        this.cakeImageBitmaps = data;
    }

    public void setData(JSONArray data) {
        this.jsonArray = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView listImage;
        TextView title, desc;

        public ViewHolder(View itemView) {
            super(itemView);
            listImage = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
        }
    }

}

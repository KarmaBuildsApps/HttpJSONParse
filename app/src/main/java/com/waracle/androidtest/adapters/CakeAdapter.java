package com.waracle.androidtest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.Constant;
import com.waracle.androidtest.Utils.ImageLoader;
import com.waracle.androidtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karma on 12/05/16.
 */
public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.ViewHolder> {
    public final String TAG = CakeAdapter.class.getSimpleName();

    private final Context context;
    private final JSONArray jsonArray;
    private ImageLoader imageLoader;

    public CakeAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
        imageLoader = new ImageLoader();
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
            imageLoader.load(url, holder.listImage);
        } catch (JSONException e) {
            Log.d(TAG, "onBindViewHolder: JSONObject read error");
        }
    }


    @Override
    public int getItemCount() {
        return jsonArray.length();
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

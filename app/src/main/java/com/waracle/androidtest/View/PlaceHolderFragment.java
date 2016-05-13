package com.waracle.androidtest.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waracle.androidtest.Constant;
import com.waracle.androidtest.R;
import com.waracle.androidtest.Utils.JSONLoader;
import com.waracle.androidtest.adapters.CakeAdapter;

import org.json.JSONArray;

/**
 * Created by Karma on 12/05/16.
 */
public class PlaceHolderFragment extends Fragment {
    private final String TAG = PlaceHolderFragment.class.getSimpleName();
    private RecyclerView mRV;
    private CakeAdapter adapter;
    private JSONArray cakeData;
    private ProgressDialogLoader loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        loader = (MainActivity) getActivity();
        mRV = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        new BackgroundDataLoader().execute();
        return view;

    }

    public class BackgroundDataLoader extends AsyncTask<Void, Integer, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.loadProgressDialog();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            loader.stopProgressDialog();
            adapter = new CakeAdapter(getActivity(), jsonArray);
            mRV.setAdapter(adapter);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray jsonArray = JSONLoader.loadData(Constant.URL);
            Log.i(TAG, "doInBackground: jsonArray:"+ jsonArray);
            return jsonArray;
        }
    }

    public interface ProgressDialogLoader {
        public void loadProgressDialog();

        public void stopProgressDialog();
    }
}

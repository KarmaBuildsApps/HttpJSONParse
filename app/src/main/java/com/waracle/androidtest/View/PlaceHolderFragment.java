package com.waracle.androidtest.View;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waracle.androidtest.Constant;
import com.waracle.androidtest.Model.BackgroundDataLoader;
import com.waracle.androidtest.Presenter.CakeListPresenter;
import com.waracle.androidtest.Presenter.CakeListView;
import com.waracle.androidtest.R;
import com.waracle.androidtest.adapters.CakeAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Karma on 12/05/16.
 */
public class PlaceHolderFragment extends Fragment implements CakeListView {
    private CakeListPresenter presenter;
    private final String TAG = PlaceHolderFragment.class.getSimpleName();
    private RecyclerView mRV;
    private CakeAdapter adapter;
    private JSONArray cakeData;
    private ProgressDialogLoader progressDialogLoader;
    private LoaderManager.LoaderCallbacks<JSONArray> jsonArrayLoaderCallbacks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        progressDialogLoader = (MainActivity) getActivity();
        mRV = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        initiateCallbacks();
        getActivity().getSupportLoaderManager().initLoader(Constant.DATA_LOADER_ID, null, jsonArrayLoaderCallbacks);
        return view;
    }

    private void initiateCallbacks() {
        jsonArrayLoaderCallbacks = new LoaderManager.LoaderCallbacks<JSONArray>() {
            @Override
            public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
                progressDialogLoader.loadProgressDialog();
                BackgroundDataLoader dataLoader = new BackgroundDataLoader(getActivity(), Constant.URL);
                return dataLoader;
            }

            @Override
            public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
                progressDialogLoader.stopProgressDialog();
                cakeData = data;
                adapter = new CakeAdapter(getActivity(), cakeData);
                mRV.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<JSONArray> loader) {
                loader.cancelLoad();
            }
        };
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void loadCakeData(String url) {

    }

    @Override
    public ArrayList<Bitmap> loadImageData(JSONArray jsonArray) {
        return null;
    }

    public interface ProgressDialogLoader {
        public void loadProgressDialog();

        public void stopProgressDialog();
    }
}

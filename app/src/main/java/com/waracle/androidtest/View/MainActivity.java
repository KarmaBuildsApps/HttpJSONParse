package com.waracle.androidtest.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.waracle.androidtest.Constant;
import com.waracle.androidtest.R;


public class MainActivity extends AppCompatActivity implements PlaceHolderFragment.ProgressDialogLoader {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait, Loading Cake Data ...");
        PlaceHolderFragment fragment = (PlaceHolderFragment) getSupportFragmentManager().findFragmentByTag(Constant.FRAGMENT_TAG);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceHolderFragment(), Constant.FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadProgressDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    @Override
    public void stopProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}

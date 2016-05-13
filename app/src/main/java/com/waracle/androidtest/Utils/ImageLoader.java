package com.waracle.androidtest.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.waracle.androidtest.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

/**
 * Created by Riad on 20/05/2015.
 */
public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    public ImageLoader() { /**/ }

    /**
     * Simple function for loading a bitmap image from the web
     *
     * @param url       image url
     * @param imageView view to set image too.
     */
    public void load(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            throw new InvalidParameterException("URL is empty!");
        }

        // Can you think of a way to improve loading of bitmaps
        // that have already been loaded previously??
        new BackgroundImageLoader(imageView).execute(url);
    }

    private static byte[] loadImageData(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        int statusCode = connection.getResponseCode();
        if (statusCode / 100 != Constant.STATUS_OK) {
            connection.disconnect();
            return null;
        }

        InputStream inputStream = null;
        byte[] data = null;
        try {
            // Read data from workstation
            inputStream = connection.getInputStream();
            data = StreamUtils.readUnknownFully(inputStream);
        } catch (IOException e) {
            // Read the error from the workstation
            inputStream = connection.getErrorStream();
            // Can you think of a way to make the entire
            // HTTP more efficient using HTTP headers??
        } finally {
            // Close the input stream if it exists.
            StreamUtils.close(inputStream);
            // Disconnect the connection
            connection.disconnect();
        }

        return data;
    }

    private Bitmap convertToBitmap(byte[] data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inMutable = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    private void setImageView(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    class BackgroundImageLoader extends AsyncTask<String, Void, byte[]> {
        private final ImageView imageView;

        public BackgroundImageLoader(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPostExecute(byte[] data) {
            Bitmap imageBitmap = convertToBitmap(data);
            setImageView(this.imageView, imageBitmap);
        }

        @Override
        protected byte[] doInBackground(String... params) {
            String url = params[0];
            byte[] data = null;
            try {
                data = loadImageData(url);
            } catch (IOException e) {
                Log.d(TAG, "doInBackground: Loading image data from internet Error");
            }
            return data;
        }
    }
}

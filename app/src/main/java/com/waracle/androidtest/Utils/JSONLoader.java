package com.waracle.androidtest.Utils;

import com.waracle.androidtest.Constant;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Karma on 12/05/16.
 */
public class JSONLoader {

    public static JSONArray loadData(String JSON_URL) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        JSONArray jsonArray = null;
        try {
            url = new URL(JSON_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode / 100 != Constant.STATUS_OK) {
                urlConnection.disconnect();
                return null;
            }
            in = new BufferedInputStream(urlConnection.getInputStream());

            // Can you think of a way to improve the performance of loading data
            // using HTTP headers???

            // Also, Do you trust any utils thrown your way????

            byte[] bytes = StreamUtils.readUnknownFully(in);

            // Read in charset of HTTP content.
            String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));

            // Convert byte array to appropriate encoded string.
            String jsonText = new String(bytes, charset);

            // Read string as JSON.
            jsonArray = new JSONArray(jsonText);
        } catch (IOException e) {

        } catch (JSONException e) {

        } finally {
            urlConnection.disconnect();
        }
        return jsonArray;
    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }

}

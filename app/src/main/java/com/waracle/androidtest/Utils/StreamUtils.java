package com.waracle.androidtest.Utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Riad on 20/05/2015.
 */
public class StreamUtils {
    private static final String TAG = StreamUtils.class.getSimpleName();

    // Can you see what's wrong with this???

    public static byte[] readUnknownFully(InputStream stream) {
        byte[] dataInByte = null;
        BufferedInputStream bis = new BufferedInputStream(stream);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        byte[] dataRead = new byte[1024];
        int current = -1;
        try {
            while ((current = bis.read(dataRead, 0, dataRead.length)) != -1) {
                bas.write(dataRead, 0, current);
            }
        } catch (IOException e) {
            Log.d(TAG, "readUnknownFully: Reading Error" + e.toString());
        } finally {
            try {
                bis.close();
                dataInByte = bas.toByteArray();
                bas.close();
            } catch (IOException e) {
                Log.d(TAG, "readUnknownFully: BufferedInputStreamClose Error: " + e.toString());
            }

        }
        return dataInByte;

        // Read in stream of bytes
//        ArrayList<Byte> data = new ArrayList<>();
//        while (true) {
//            int result = stream.read();
//            if (result == -1) {
//                break;
//            }
//            data.add((byte) result);
//        }
//
//        // Convert ArrayList<Byte> to byte[]
//        byte[] bytes = new byte[data.size()];
//        for (int i = 0; i < bytes.length; i++) {
//            bytes[i] = data.get(i);
//        }
//
//        // Return the raw byte array.
//        return bytes;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}

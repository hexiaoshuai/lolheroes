package cn.studyjams.s2.sj0115.hexiaoshuai.lolheroes.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {
    public static String getJson(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            json = EncodingUtils.getString(bytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Bitmap getImage(Context context, String fileName) {
        Bitmap image = null;
        try {
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            image = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}

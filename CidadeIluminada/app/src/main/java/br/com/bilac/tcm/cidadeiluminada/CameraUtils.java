package br.com.bilac.tcm.cidadeiluminada;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.File;

/**
 * Created by arthur on 04/04/15.
 */
public class CameraUtils {
    public static Bitmap decodeSampledBitmapFromFile(String path, int requiredWidth,
                                                      int requiredHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;
        if (height > requiredHeight) {
            inSampleSize = Math.round((float)height / (float)requiredHeight);
        }
        int expectedWidth = width / inSampleSize;
        if (expectedWidth > requiredWidth) {
            inSampleSize = Math.round((float)width / (float)requiredWidth);
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Uri getOutputMediaFileUri(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), Constants.APPLICATION_NAME);
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        DateTime date = new DateTime(DateTimeZone.UTC);
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + "IMG_" +
                date.toString() + Constants.PHOTO_EXTENSION));
    }
}

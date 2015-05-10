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

        Bitmap bmp = BitmapFactory.decodeFile(path, null);
        return Bitmap.createScaledBitmap(bmp, requiredWidth, requiredHeight, true);
    }

    public static Uri getOutputMediaFileUri(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), Constants.PACKAGE);
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

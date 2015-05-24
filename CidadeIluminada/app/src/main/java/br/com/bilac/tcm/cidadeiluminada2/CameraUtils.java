package br.com.bilac.tcm.cidadeiluminada2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
        String filename = getFileName();
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + filename));
    }


    private static String getFileName(){
        String prefix = "IMG_";
        String extension = Constants.PHOTO_EXTENSION;

        Date today = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");

        String trunk = formatter.format(today);



        return prefix + trunk + extension;
    }
}



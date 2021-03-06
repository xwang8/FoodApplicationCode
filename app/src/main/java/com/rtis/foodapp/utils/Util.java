package com.rtis.foodapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by admin on 2/12/16.
 */
public class Util {

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    public static final int BREAKFAST = 0;
    public static final int MORNING_SNACK = 1;
    public static final int LUNCH = 2;
    public static final int AFTERNOON_SNACK = 3;
    public static final int DINNER = 4;
    public static final int EVENING_SNACK = 5;

    public static final String BREAKFAST_STRING = "Breakfast";
    public static final String MORNING_SNACK_STRING = "Snack (morning)";
    public static final String LUNCH_STRING = "Lunch";
    public static final String AFTERNOON_SNACK_STRING = "Snack (afternoon)";
    public static final String DINNER_STRING = "Dinner";
    public static final String EVENING_SNACK_STRING = "Snack (evening)";

    // Identifiers for each meal. How "meal" is saved in database.
    public static final String BREAKFAST_FILE = "b";
    public static final String MORNING_SNACK_FILE = "ms";
    public static final String LUNCH_FILE = "l";
    public static final String AFTERNOON_SNACK_FILE = "as";
    public static final String DINNER_FILE = "d";
    public static final String EVENING_SNACK_FILE = "es";

    // Time formats
    public static final String TIMESTAMP_FORMAT = "hh:mm aaa";
    public static final String DATE_FORMAT = "yyMMdd"; //"ddMMyy";

    // File naming
    public static final String TEXT_PREFIX = "TXT_";
    public static final String TEXT_SUFFIX = ".txt";
    public static final String IMAGE_PREFIX = "JPEG_";
    public static final String IMAGE_SUFFIX = ".jpg";
    public static final String TIME_FORMAT = "HHmmss";

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public static Typeface getCustomTypeFace(Context context, int index) {
        String fontPath = null;
        Typeface tf = null;
        try {
            switch (index) {
                case 0:
                    fontPath = "fonts/Roboto-Regular.ttf";
                    break;
                case 1:
                    fontPath = "fonts/GothamLight.ttf";
            }
            // Loading Font Face
            tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        } catch (Exception e) {
            tf = Typeface.DEFAULT;
        }
        return tf;
    }

    public static File getStorageDirectory(Context ctx) {
        //Logger.v(" Ext Storage "+ isExternalStorageAvailable());
        File storage = ctx.getExternalFilesDir(null);
        if(storage!=null && !storage.exists())
            storage.mkdir();
        return storage;
    }

    /**
     * Image Scale Down function, resize a bitmap
     * @param originalBitmap Bitmap of image
     * @param maxSize maximum size of image
     * @param filter true if source should be filtered
     */
    public static Bitmap scaleDown(Bitmap originalBitmap, float maxSize, boolean filter) {
        float ratio = Math.min((float) maxSize / originalBitmap.getWidth(), (float) maxSize / originalBitmap.getHeight());
        int prWidth = Math.round((float) ratio * originalBitmap.getWidth());
        int prHeight = Math.round((float) ratio * originalBitmap.getHeight());
        Bitmap bitmap = Bitmap.createScaledBitmap(originalBitmap, prWidth, prHeight, filter);
        return bitmap;
    }

    /**
     * Resize Image for upload/download
     * @param image File of image
     * @param maxSize maximum size of image
     * @param filter true if source should be filtered
     * @return File object for image
     */
    public static File resizeImage(File image, int maxSize, boolean filter) {
        //https://stackoverflow.com/questions/11688982/pick-image-from-sd-card-resize-the-image-and-save-it-back-to-sd-card
        Bitmap b = BitmapFactory.decodeFile(image.getAbsolutePath());
        Logger.v("Image path " + image.getAbsolutePath());
        Bitmap out = Util.scaleDown(b, maxSize, filter);
        //overwrite File with new bitmap
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(image);
            out.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
        } catch (Exception e) {}

        return image;
    }

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public static boolean isPhoneNoValid(String phno) {
        //TODO: Replace this with your own logic
        return phno.length() == 10;
    }

    public static boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length()>0;
    }

    public static void showToast(Context ctx, String message)
    {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }


}

package com.cbstudio.colautility;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Colabear on 2016-03-24.
 * processing image, screen
 */
public class ImageUtil {
    ///------------- Screen
    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


    ///--------------- Image
    public static Bitmap cropBitmap(Context context,Bitmap srcBmp, int dpX, int dpY)
    {
        return cropBitmap(srcBmp, (int)dpToPx(context, dpX), (int)dpToPx(context, dpY));
    }

    public static Bitmap cropBitmap(Bitmap srcBmp , int width, int height) {
        if(srcBmp == null) return null;

        if(srcBmp.getWidth() < width) width = srcBmp.getWidth();
        if(srcBmp.getHeight() < height) height = srcBmp.getHeight();

        return Bitmap.createBitmap(srcBmp, 0, 0, width,
                height);
    }
}

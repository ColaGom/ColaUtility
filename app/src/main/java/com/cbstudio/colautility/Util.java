package com.cbstudio.colautility;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Colabear on 2016-03-24.
 */
public class Util {
    private static SimpleDateFormat defalutFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static Date convertStringtoDate(String date)
    {
        return convertStringToDate(date, defalutFormat);
    }

    public static Date convertStringToDate(String date, SimpleDateFormat format) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static void setClipBoard(Context c , CharSequence s)
    {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipBoard = (ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
            clipBoard.setText(s);
        }else{
            ClipboardManager clipBoard = (ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Befull Copied", s);
            clipBoard.setPrimaryClip(clip);
        }
    }
}

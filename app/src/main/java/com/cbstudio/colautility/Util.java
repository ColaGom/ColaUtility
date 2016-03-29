package com.cbstudio.colautility;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Colabear on 2016-03-24.
 * processing file , date, string
 */
public class Util {

    ///------------ Common
    public static String getPhoneNumber(Context context)
    {
        try {
            TelephonyManager mManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = mManager.getLine1Number();

            phoneNumber = phoneNumber.replace("+82", "0");

            phoneNumber = PhoneNumberUtils.formatNumber(phoneNumber);

            return phoneNumber;
        } catch (Exception e) {
            return "";
        }
    }

    public static void setFont(View view, Typeface typeface) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        if (((TextView) v).getTypeface() != null && ((TextView) v).getTypeface().isBold())
                            ((TextView) v).setTypeface(typeface);
                        else
                            ((TextView) v).setTypeface(typeface);
                    }
                    setFont(v, typeface);
                }
            }
        } else {

        }
    }

    /**
     *
     * @param name original name.
     * @return simplename eg) Mark Ethan => ME , 01022230220 => 010
     */
    public  static String getSimpleNmae(String name){
        if (name.split(" ")[0].equals(name.split(" ")[0].toUpperCase())) {
            return name.charAt(0) + "";
        } else {
            if (name.lastIndexOf(" ") != -1) {
                String[] str = name.split(" ");
                if (TextUtils.isEmpty(str[1])) {
                    return str[0].charAt(0) + "";
                }
                return str[0].charAt(0) + "" + str[1].charAt(0) + "";
            } else
                return name.charAt(0) + "";
        }
    }


    ///------------ Date, String
    private static SimpleDateFormat defalutFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static Date convertStringToDate(String date)
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
    private static String[] dayStrings = new String[]{ "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    /* if you want dayStrings implements via xml try this.
    public static void setDayOfWeek(Context c) {
        dayStrings = c.getResources().getStringArray(R.array.day_of_week);
    }
    */
    public static String getDayString(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return dayStrings[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    ///------------ ClipBoard
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


    ///--------------- File
    public static String getExtension(String url) {
        String ext = null;
        String s = url;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return "." + ext;
    }

    public static void playAudioFile(Context context, String path)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + path);
        intent.setDataAndType(uri, "audio/*");

        context.startActivity(intent);
    }

    public static String getStringFileSize(File file) {
        String value = null;
        long size = getFolderSize(file) / 1024;

        if (size > 1024)
            value = size / 1024 + " MB";
        else
            value = size + " KB";

        return value;
    }

    private static long getFolderSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFolderSize(file);
            }
        } else {
            size = f.length();
        }
        return size;
    }

    public static File ConvertDrawableToTempFile(Drawable d) {
        String sd = Environment.getExternalStorageDirectory().toString();
        String fileName = "/share_temp.jpg";
        String path = sd + fileName;
        Bitmap bmp = ((BitmapDrawable) d).getBitmap();

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your
            // Bitmap
            // instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new File(path);
    }

    private static final String[] imageFileExtensions = new String[] {"jpg", "jpeg","png"};
    private static final String[] videoFileExtensions = new String[] {"jpg", "jpeg","png"};

    public static File[] getListOfImageFiles(String dir)
    {
        return getListOfFiles(new File(dir), imageFileExtensions);
    }

    public static File[] getListOfImageFile(File dir)
    {
        return getListOfFiles(dir, imageFileExtensions);
    }

    public static File[] getListOfVideoFile(File dir)
    {
        return getListOfFiles(dir, videoFileExtensions);
    }

    public static File[] getListOfVideoFile(String dir)
    {
        return getListOfFiles(new File(dir), videoFileExtensions);
    }

    private static File[] getListOfFiles(File dir, final String[] extensions)
    {
        return dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                for (String ext : extensions) {
                    if (pathname.getName().toLowerCase().endsWith(ext)) return true;
                }
                return false;
            }
        });
    }

}

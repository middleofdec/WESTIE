package com.westie.westieproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefs
{   final static String FileName = "MyFileNAME";

    public static String readNameSharedSetting(Context ctx, String settingName, String defaultValue)
    {
        SharedPreferences sp = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return sp.getString(settingName,defaultValue);
    }


    public static void saveSharedSetting(Context ctx,String settingName,String settingValue)
    {
        SharedPreferences sp = ctx.getSharedPreferences(FileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(settingName,settingValue);
        editor.apply();
    }


    public static void SharePrefsSAVE(Context ctx,String Name)
    {
        SharedPreferences prefs = ctx.getSharedPreferences("NAME",0);
        SharedPreferences.Editor prefEDIT = prefs.edit();
        prefEDIT.putString("NAME",Name);
        prefEDIT.commit();
    }
}

package com.example.secondproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

public class MusicUtilty {

    static SharedPreferences sPref;
    private static final String SHARED_PREFS = "sharedPrefs";

    public static void savePosition(Activity activity, int position) {
        sPref = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("musicPositionKey", position);
        ed.commit();
    }

    public static void saveData(Context context, String position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY", position);
        editor.apply();
    }

    public static String loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString("KEY", "");
        return text;
    }

}

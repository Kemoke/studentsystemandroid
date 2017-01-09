package kemoke.ius.studentsystemandroid.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for handling application preferences
 */
public class ApplicationPrefs {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    ApplicationPrefs(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public <T> void saveList(List<T> list, String tag) {
        try {
            String listJson = LoganSquare.serialize(list);
            editor.putString(tag, listJson);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getList(Class<T> clazz, String tag){
        try {
            String json = preferences.getString(tag, "[]");
            return LoganSquare.parseList(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void add(String tag, String content){
        editor.putString(tag, content);
        editor.apply();
    }

    public String get(String tag){
        return preferences.getString(tag, "NOT_FOUND");
    }
}

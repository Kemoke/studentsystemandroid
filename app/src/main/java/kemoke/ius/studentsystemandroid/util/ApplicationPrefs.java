package kemoke.ius.studentsystemandroid.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationPrefs {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public ApplicationPrefs(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public <T> void saveList(List<T> list, String tag){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String listJson = mapper.writeValueAsString(list);
            editor.putString(tag, listJson);
            editor.apply();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getList(Class<T> clazz, String tag){
        ObjectMapper mapper = new ObjectMapper();
        String listJson = preferences.getString(tag, "[]");
        CollectionType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        try {
            return mapper.readValue(listJson, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void add(String tag, String content){
        editor.putString(tag, content);
        editor.apply();
    }

    public String get(String tag){
        return preferences.getString(tag, "NOT_FOUND");
    }
}

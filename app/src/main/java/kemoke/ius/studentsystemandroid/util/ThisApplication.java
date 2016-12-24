package kemoke.ius.studentsystemandroid.util;

import android.app.Application;

import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.User;

public class ThisApplication extends Application {
    private static ThisApplication application;

    private User user;
    private ApplicationPrefs prefs;
    private String token;
    private String userType;

    public static ThisApplication getThisApplication(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        prefs = new ApplicationPrefs(this);
        token = prefs.get("token");
        userType = prefs.get("usertype");
        HttpApi.init(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApplicationPrefs getPreferences(){
        return prefs;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        prefs.add("token", token);
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
        prefs.add("usertype", userType);
    }
}

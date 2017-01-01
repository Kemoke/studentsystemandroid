package kemoke.ius.studentsystemandroid.api;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;

import kemoke.ius.studentsystemandroid.activities.LoginActivity;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static kemoke.ius.studentsystemandroid.util.ThisApplication.getThisApplication;

/**
 * Interceptor for adding auth tokens to requests, and handling authentication.
 */
class AuthInterceptor implements Interceptor {
    private Context context;

    AuthInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url();
        Request intercept = request.newBuilder()
                .header("X-Auth-Token", getThisApplication().getToken())
                .header("Accept", "application/json")
                .build();
        Response response = chain.proceed(intercept);
        if(httpUrl.toString().contains("auth")){
            return response;
        }
        if(response.code() == 410){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(context, "Session expired", Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        }
        return response;
    }
}

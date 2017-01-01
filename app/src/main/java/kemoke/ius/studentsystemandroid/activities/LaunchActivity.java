package kemoke.ius.studentsystemandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.User;
import kemoke.ius.studentsystemandroid.util.ThisApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This activity loads auth token and user type from cache and checks if they are valid.
 * Also used for showing the initial splash screen.
 * If token is valid user is redirected to MainActivity.
 * If token is invalid user is redirected to LoginActivity.
 */
public class LaunchActivity extends AppCompatActivity implements Callback<User> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpApi.AuthApi().self().enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() != 200){
            try {
                Log.e("err", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            ThisApplication.getThisApplication().setUser(response.body());
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.e("err", t.getMessage());
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}

package kemoke.ius.studentsystemandroid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.api.AuthApi;
import kemoke.ius.studentsystemandroid.models.User;
import kemoke.ius.studentsystemandroid.util.TokenJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kemoke.ius.studentsystemandroid.util.ThisApplication.getThisApplication;

/**
 * This application handles admin registration.
 * Only used for testing, will be removed when app goes live.
 */
public class RegisterActivity extends AppCompatActivity implements Callback<User>{

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.register_button)
    Button registerButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.register_button)
    public void onRegisterClick() {
        AuthApi authApi = HttpApi.AuthApi();
        authApi.register(username.getText().toString(), email.getText().toString(), password.getText().toString())
                .enqueue(this);
        progressDialog.setMessage("Registering");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() == 200){
            User user = response.body();
            getThisApplication().setUser(user);
            HttpApi.AuthApi().login(email.getText().toString(), password.getText().toString()).enqueue(loginCallback);
        } else {
            Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.hide();
    }

    private Callback<TokenJson> loginCallback = new Callback<TokenJson>() {
        @Override
        public void onResponse(Call<TokenJson> call, Response<TokenJson> response) {
            if(response.code() == 200){
                TokenJson tokenJson = response.body();
                getThisApplication().setToken(tokenJson.jwt);
                getThisApplication().setUserType(tokenJson.type);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                try {
                    Toast.makeText(RegisterActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.hide();
        }

        @Override
        public void onFailure(Call<TokenJson> call, Throwable t) {
            Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }
    };
}

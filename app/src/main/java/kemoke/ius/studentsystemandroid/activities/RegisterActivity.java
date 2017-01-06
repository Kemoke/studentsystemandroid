package kemoke.ius.studentsystemandroid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.api.AuthApi;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.User;
import kemoke.ius.studentsystemandroid.util.callback.BaseCallback;
import kemoke.ius.studentsystemandroid.util.TokenJson;

import static kemoke.ius.studentsystemandroid.util.ThisApplication.getThisApplication;

/**
 * This application handles admin registration.
 * Only used for testing, will be removed when app goes live.
 */
public class RegisterActivity extends AppCompatActivity{

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
        progressDialog.setMessage("Registering");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        registerCallback.setProgressDialog(progressDialog);
        loginCallback.setProgressDialog(progressDialog);
    }

    @OnClick(R.id.register_button)
    public void onRegisterClick() {
        AuthApi authApi = HttpApi.authApi();
        authApi.register(username.getText().toString(), email.getText().toString(), password.getText().toString())
                .enqueue(registerCallback);
    }

    private BaseCallback<User> registerCallback = new BaseCallback<User>(this, "Failed to register") {
        @Override
        public void onSuccess(User body) {
            getThisApplication().setUser(body);
            HttpApi.authApi().login(email.getText().toString(), password.getText().toString()).enqueue(loginCallback);
        }
    };

    private BaseCallback<TokenJson> loginCallback = new BaseCallback<TokenJson>(this, "Failed to login") {
        @Override
        public void onSuccess(TokenJson body) {
            getThisApplication().setToken(body.jwt);
            getThisApplication().setUserType(body.type);
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };
}

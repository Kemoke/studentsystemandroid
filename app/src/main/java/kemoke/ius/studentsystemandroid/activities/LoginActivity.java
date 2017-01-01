package kemoke.ius.studentsystemandroid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.util.TokenJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kemoke.ius.studentsystemandroid.api.HttpApi.AuthApi;
import static kemoke.ius.studentsystemandroid.util.ThisApplication.getThisApplication;

public class LoginActivity extends AppCompatActivity implements Callback<TokenJson>, TextView.OnEditorActionListener {

    @BindView(R.id.login_email)
    EditText loginEmail;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_button)
    Button loginButton;
    ProgressDialog progressDialog;
    @BindView(R.id.register_button)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPassword.setOnEditorActionListener(this);
    }

    @OnClick(R.id.login_button)
    public void onLoginClick() {
        AuthApi().login(loginEmail.getText().toString(), loginPassword.getText().toString()).enqueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<TokenJson> call, Response<TokenJson> response) {
        progressDialog.dismiss();
        if(response.code() != 200) {
            Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
            return;
        }
        getThisApplication().setToken(response.body().jwt);
        getThisApplication().setUserType(response.body().type);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Call<TokenJson> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.register_button)
    public void onRegisterClick() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
        if(actionID == EditorInfo.IME_ACTION_DONE){
            onLoginClick();
        }
        return true;
    }
}

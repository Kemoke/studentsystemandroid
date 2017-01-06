package kemoke.ius.studentsystemandroid.util.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("WeakerAccess")
public abstract class BaseCallback<T> implements Callback<T> {
    protected Context context;
    protected String errorMessage;

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    protected ProgressDialog progressDialog;

    public BaseCallback(Context context, String errorMessage, ProgressDialog progressDialog){
        this.context = context;
        this.errorMessage = errorMessage;
        this.progressDialog = progressDialog;
    }

    public BaseCallback(Context context, String errorMessage){
        this(context,errorMessage, null);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.code() == 200){
            onSuccess(response.body());
        } else {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            try {
                Log.e("srv err", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(progressDialog != null)
            progressDialog.hide();
    }

    public abstract void onSuccess(T body);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("net err", t.getMessage());
        if(progressDialog != null)
            progressDialog.hide();
    }


}

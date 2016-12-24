package kemoke.ius.studentsystemandroid.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import kemoke.ius.studentsystemandroid.models.BaseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCallback<T extends BaseModel> implements Callback<T> {
    private Activity context;
    private ProgressDialog progressDialog;
    public EditCallback(Activity context, ProgressDialog progressDialog){
        this.context = context;
        this.progressDialog = progressDialog;
    }
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.code() == 200){
            T item = response.body();
            Intent intent = new Intent();
            intent.putExtra("item", item);
            context.setResult(item.id, intent);
            context.finish();
        } else {
            try {
                Log.e("err", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e("err", t.getMessage());
        progressDialog.hide();
    }
}

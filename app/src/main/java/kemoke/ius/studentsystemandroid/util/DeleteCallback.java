package kemoke.ius.studentsystemandroid.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import kemoke.ius.studentsystemandroid.adapters.CrudAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCallback<T> implements Callback<String> {
    private final List<T> dataSet;
    private final RecyclerView recyclerView;
    private final ProgressDialog progressDialog;
    private final Context context;
    private final int position;

    public DeleteCallback(List<T> dataSet, RecyclerView recyclerView, ProgressDialog progressDialog, Context context, int position) {
        this.dataSet = dataSet;
        this.recyclerView = recyclerView;
        this.progressDialog = progressDialog;
        this.context = context;
        this.position = position;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.code() == 200){
            ((CrudAdapter)recyclerView.getAdapter()).delete(position);
        } else {
            try {
                Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
        progressDialog.hide();
    }
}

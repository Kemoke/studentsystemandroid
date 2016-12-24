package kemoke.ius.studentsystemandroid.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import kemoke.ius.studentsystemandroid.adapters.CrudAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitCallback<T> implements Callback<List<T>> {
    private final List<T> dataSet;
    private final RecyclerView recyclerView;
    private final ProgressDialog progressDialog;
    private final Context context;

    public InitCallback(Context context, List<T> dataSet, RecyclerView recyclerView, ProgressDialog progressDialog){
        this.dataSet = dataSet;
        this.recyclerView = recyclerView;
        this.progressDialog = progressDialog;
        this.context = context;
    }

    @Override
    public void onResponse(Call<List<T>> call, Response<List<T>> response) {
        if(response.code() == 200){
            dataSet.addAll(response.body());
            try {
                Log.d("response", new ObjectMapper().writeValueAsString(response.body()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ((CrudAdapter)recyclerView.getAdapter()).getFilter().filter("");
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
    public void onFailure(Call<List<T>> call, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
        progressDialog.hide();
    }
}

package kemoke.ius.studentsystemandroid.util.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import kemoke.ius.studentsystemandroid.adapters.crud.CrudAdapter;

public class InitCallback<T> extends BaseCallback<List<T>> {
    private final List<T> dataSet;
    private final RecyclerView recyclerView;

    public InitCallback(Context context, List<T> dataSet, RecyclerView recyclerView, ProgressDialog progressDialog){
        super(context, "Failed to load items", progressDialog);
        this.dataSet = dataSet;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onSuccess(List<T> body) {
        dataSet.addAll(body);
        ((CrudAdapter)recyclerView.getAdapter()).getFilter().filter("");
    }
}

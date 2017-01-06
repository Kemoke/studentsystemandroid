package kemoke.ius.studentsystemandroid.util.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import kemoke.ius.studentsystemandroid.adapters.crud.CrudAdapter;

public class DeleteCallback<T> extends BaseCallback<String> {
    private final RecyclerView recyclerView;
    private final int position;

    public DeleteCallback(RecyclerView recyclerView, ProgressDialog progressDialog, Context context, int position) {
        super(context, "Failed to delete item", progressDialog);
        this.recyclerView = recyclerView;
        this.position = position;
    }

    @Override
    public void onSuccess(String body) {
        ((CrudAdapter)recyclerView.getAdapter()).delete(position);
    }
}

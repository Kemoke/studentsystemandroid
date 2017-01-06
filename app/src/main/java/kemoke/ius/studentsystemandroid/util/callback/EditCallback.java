package kemoke.ius.studentsystemandroid.util.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import kemoke.ius.studentsystemandroid.models.BaseModel;

public class EditCallback<T extends BaseModel> extends BaseCallback<T> {
    public EditCallback(Activity context, ProgressDialog progressDialog){
        super(context, "Failed to edit item", progressDialog);
    }

    @Override
    public void onSuccess(T body) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.putExtra("item", body);
        activity.setResult(body.id, intent);
        activity.finish();
    }
}

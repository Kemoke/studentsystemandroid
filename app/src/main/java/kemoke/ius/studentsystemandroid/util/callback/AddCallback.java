package kemoke.ius.studentsystemandroid.util.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import kemoke.ius.studentsystemandroid.models.BaseModel;

public class AddCallback<T extends BaseModel> extends BaseCallback<T> {
    public AddCallback(Activity context, ProgressDialog progressDialog){
        super(context, "Failed to add item", progressDialog);
    }

    @Override
    public void onSuccess(T body) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.putExtra("item", body);
        activity.setResult(1, intent);
        progressDialog.hide();
        activity.finish();
    }
}

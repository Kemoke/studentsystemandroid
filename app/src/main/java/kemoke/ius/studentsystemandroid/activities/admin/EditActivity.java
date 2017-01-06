package kemoke.ius.studentsystemandroid.activities.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.models.BaseModel;
import kemoke.ius.studentsystemandroid.util.callback.EditCallback;

/**
 * This activity serves as generic implementation of Editing an item.
 * @param <T> Model for crud operations
 */
@SuppressWarnings("ConstantConditions")
public abstract class EditActivity<T extends BaseModel> extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected ProgressDialog progressDialog;
    protected T item;
    int layout;
    protected EditActivity(int layout){
        this.layout = layout;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending");
        item = getIntent().getParcelableExtra("item");
        loadItem(item);
    }

    protected abstract void loadItem(T item);

    protected abstract void editItem(EditCallback<T> callback, T item);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add_btn){
            onClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClick() {
        editItem(new EditCallback<T>(this, progressDialog), item);
        progressDialog.show();
    }
}

package kemoke.ius.studentsystemandroid.activities.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.models.BaseModel;

public abstract class ShowActivity<T extends BaseModel> extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    int layout;
    protected ShowActivity(int layout){
        this.layout = layout;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        T item = getIntent().getParcelableExtra("item");
        bindView(item);
    }

    protected abstract void bindView(T item);
}

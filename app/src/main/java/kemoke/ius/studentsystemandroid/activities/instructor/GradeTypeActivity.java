package kemoke.ius.studentsystemandroid.activities.instructor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.instructor.GradeTypeAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.fragments.EditGradeTypeFragment;
import kemoke.ius.studentsystemandroid.models.GradeType;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.util.callback.BaseCallback;

@SuppressWarnings("ConstantConditions")
public class GradeTypeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_view)
    RecyclerView listView;
    private Section section;
    private ArrayList<GradeType> gradeTypes;
    private GradeTypeAdapter adapter;
    private ProgressDialog progressDialog;
    private BaseCallback<Section> saveCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_type);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        saveCallback = new BaseCallback<Section>
                (this, "Failed to save grade types", progressDialog) {
            @Override
            public void onSuccess(Section body) {
                finish();
            }
        };
        section = getIntent().getParcelableExtra("section");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        if(savedInstanceState != null){
            ArrayList<GradeType> saved = savedInstanceState.getParcelableArrayList("gradeTypes");
            setupListView(saved);
        } else {
            progressDialog.setTitle("Getting grade types");
            progressDialog.show();
            HttpApi.instructorActionsApi().getGradeTypes(section.id).enqueue(new BaseCallback<List<GradeType>>
                    (this, "Failed to load grade types", progressDialog) {
                @Override
                public void onSuccess(List<GradeType> body) {
                    setupListView((ArrayList<GradeType>) body);
                }
            });
        }
    }

    private void setupListView(ArrayList<GradeType> data){
        gradeTypes = data;
        adapter = new GradeTypeAdapter(gradeTypes);
        adapter.setOnDeleteListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(adapter.getPosition());
            }
        });
        adapter.setOnEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final GradeType gradeType = gradeTypes.get(adapter.getPosition());
                final EditGradeTypeFragment fragment = new EditGradeTypeFragment();
                final Bundle bundle = new Bundle();
                bundle.putParcelable("gradeType", gradeType);
                bundle.putString("title", "Edit GradeType");
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(), "dialog");
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_btn:
                onGradeSave();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_button)
    public void onClick() {
        final EditGradeTypeFragment fragment = new EditGradeTypeFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable("section", section);
        bundle.putString("title", "Add GradeType");
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "dialog");
    }

    public void onGradeEdit(GradeType gradeType){
        adapter.edit(gradeType);
    }

    public void onGradeAdd(GradeType gradeType){
        adapter.add(gradeType);
    }

    public void onGradeSave(){
        HttpApi.instructorActionsApi().setGradeTypes(section.id, gradeTypes).enqueue(saveCallback);
        progressDialog.setTitle("Setting grade types");
        progressDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("gradeTypes", gradeTypes);
    }
}

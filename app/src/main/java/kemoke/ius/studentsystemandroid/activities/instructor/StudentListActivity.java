package kemoke.ius.studentsystemandroid.activities.instructor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.instructor.students.InstructorStudentListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.models.Student;
import kemoke.ius.studentsystemandroid.util.callback.BaseCallback;

@SuppressWarnings("ConstantConditions")
public class StudentListActivity extends AppCompatActivity {
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_button)
    FloatingActionButton addButton;
    private BaseCallback<List<Student>> initCallback;
    private InstructorStudentListAdapter adapter;
    private ArrayList<Student> students;
    private Section section;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        section = getIntent().getParcelableExtra("section");
        setContentView(R.layout.activity_grade_type);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        initCallback = new BaseCallback<List<Student>>
                (this, "Failed to load students", progressDialog) {
            @Override
            public void onSuccess(List<Student> body) {
                updateItems(new ArrayList<>(body));
            }
        };
        setupListView();
        if (savedInstanceState != null) {
            updateItems(savedInstanceState.<Student>getParcelableArrayList("students"));
        } else {
            progressDialog.setTitle("Loading students");
            progressDialog.show();
            HttpApi.instructorActionsApi().sectionStudents(section.id).enqueue(initCallback);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("students", students);
    }

    private void setupListView() {
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new InstructorStudentListAdapter(this);
        listView.setAdapter(adapter);
    }

    private void updateItems(ArrayList<Student> newStudents) {
        students = newStudents;
        adapter.setItems(students);
        adapter.getFilter().filter("");
    }
}

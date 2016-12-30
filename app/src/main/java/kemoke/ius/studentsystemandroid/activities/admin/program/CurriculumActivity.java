package kemoke.ius.studentsystemandroid.activities.admin.program;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.CourseListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.fragments.CurriculumAddFragment;
import kemoke.ius.studentsystemandroid.models.Course;
import kemoke.ius.studentsystemandroid.models.CurriculumCourse;
import kemoke.ius.studentsystemandroid.models.Program;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurriculumActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.courses)
    SearchableSpinner courseSpinner;
    @BindView(R.id.add_button)
    FloatingActionButton addButton;
    CourseListAdapter adapter;
    List<Course> displayCourses;
    List<Course> courses;
    Program program;
    CurriculumCourse addedCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        program = getIntent().getParcelableExtra("program");
        adapter = new CourseListAdapter();
        displayCourses = new ArrayList<>();
        for (CurriculumCourse course : program.curriculum) {
            course.course.program = program;
            displayCourses.add(course.course);
        }
        adapter.setItems(displayCourses);
        adapter.getFilter().filter("");
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        registerForContextMenu(listView);
        HttpApi.CourseApi().listWithProps("program").enqueue(courseCallback);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        courseSpinner.setOnItemSelectedListener(CurriculumActivity.this);
                    }
                });
            }
        }, 1000);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        adapter.add(courses.get(position));
        addedCourse = new CurriculumCourse();
        program.curriculum.add(addedCourse);
        addedCourse.course = courses.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("course", addedCourse);
        CurriculumAddFragment fragment = new CurriculumAddFragment();
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "alert");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        int deletePos = adapter.getPosition();
        Course item = displayCourses.get(deletePos);
        switch (menuItem.getItemId()){
            case R.id.menu_delete:
                adapter.delete(deletePos);
                return true;
        }
        return super.onContextItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_btn) {
            HttpApi.ProgramApi().setCurriculum(program.id, program.curriculum).enqueue(curriculumCallback);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_button)
    public void onClick() {
        courseSpinner.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis()+100,
                MotionEvent.ACTION_UP,0,0,0));
    }

    private Callback<List<Course>> courseCallback = new Callback<List<Course>>() {
        @Override
        public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
            courses = response.body();
            for (CurriculumCourse curriculumCourse : program.curriculum) {
                courses.remove(curriculumCourse.course);
            }
            ArrayList<String> names = new ArrayList<>(courses.size());
            for (Course course : courses) {
                names.add(course.code + " " + course.name);
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(CurriculumActivity.this, android.R.layout.simple_spinner_item, names);
            courseSpinner.setAdapter(spinnerAdapter);
            courseSpinner.setTitle("Select Course");
        }

        @Override
        public void onFailure(Call<List<Course>> call, Throwable t) {

        }
    };

    private Callback<List<CurriculumCourse>> curriculumCallback = new Callback<List<CurriculumCourse>>() {
        @Override
        public void onResponse(Call<List<CurriculumCourse>> call, Response<List<CurriculumCourse>> response) {
            if(response.code() == 200){
                Toast.makeText(CurriculumActivity.this, "yay", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CurriculumActivity.this, "nay", Toast.LENGTH_SHORT).show();
                try {
                    Log.e("err", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<List<CurriculumCourse>> call, Throwable t) {
            Toast.makeText(CurriculumActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}

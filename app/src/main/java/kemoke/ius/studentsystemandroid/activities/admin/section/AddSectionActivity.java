package kemoke.ius.studentsystemandroid.activities.admin.section;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.AddActivity;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Course;
import kemoke.ius.studentsystemandroid.models.Instructor;
import kemoke.ius.studentsystemandroid.models.Section;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSectionActivity extends AddActivity<Section> {
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.capacity)
    EditText capacity;
    @BindView(R.id.course)
    SearchableSpinner course;
    @BindView(R.id.instructor)
    SearchableSpinner instructor;
    private List<Course> courses;
    private List<Instructor> instructors;

    public AddSectionActivity() {
        super(R.layout.activity_add_section);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpApi.courseApi().list().enqueue(courseCallback);
        HttpApi.instructorApi().list().enqueue(instructorCallback);
        course.setTitle("Select Section");
        course.setPositiveButton("Close");
        instructor.setTitle("Select Section");
        instructor.setPositiveButton("Close");
    }

    @Override
    protected void addItem(Callback<Section> callback) {
        Section section = new Section();
        section.number = Integer.parseInt(number.getText().toString());
        section.capacity = Integer.parseInt(capacity.getText().toString());
        section.course = courses.get(course.getSelectedItemPosition());
        section.instructor = instructors.get(instructor.getSelectedItemPosition());
        HttpApi.sectionApi().add(section).enqueue(callback);
    }

    private Callback<List<Course>> courseCallback = new Callback<List<Course>>() {
        @Override
        public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
            courses = response.body();
            String[] depNames = new String[courses.size()];
            for (int i = 0; i < courses.size(); i++) {
                depNames[i] = courses.get(i).name;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(AddSectionActivity.this, android.R.layout.simple_spinner_item, depNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            course.setAdapter(adapter);
            progressDialog.hide();
        }

        @Override
        public void onFailure(Call<List<Course>> call, Throwable t) {

        }
    };

    private Callback<List<Instructor>> instructorCallback = new Callback<List<Instructor>>() {
        @Override
        public void onResponse(Call<List<Instructor>> call, Response<List<Instructor>> response) {
            instructors = response.body();
            String[] depNames = new String[instructors.size()];
            for (int i = 0; i < instructors.size(); i++) {
                Instructor ins = instructors.get(i);
                depNames[i] = ins.firstName + " " + ins.lastName;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(AddSectionActivity.this, android.R.layout.simple_spinner_item, depNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            instructor.setAdapter(adapter);
            progressDialog.hide();
        }

        @Override
        public void onFailure(Call<List<Instructor>> call, Throwable t) {

        }
    };
}

package kemoke.ius.studentsystemandroid.activities.admin.section;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.EditActivity;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Course;
import kemoke.ius.studentsystemandroid.models.Instructor;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.util.EditCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSectionActivity extends EditActivity<Section>{
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.capacity)
    EditText capacity;
    @BindView(R.id.course)
    SearchableSpinner course;
    @BindView(R.id.instructor)
    SearchableSpinner instructor;
    List<Course> courses;
    List<Instructor> instructors;

    public EditSectionActivity(){
        super(R.layout.activity_add_section);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpApi.CourseApi().list().enqueue(courseCallback);
        HttpApi.InstructorApi().list().enqueue(instructorCallback);
        course.setTitle("Select Section");
        course.setPositiveButton("Close");
        instructor.setTitle("Select Section");
        instructor.setPositiveButton("Close");
    }

    @Override
    protected void loadItem(Section item) {
        number.setText(item.number);
        capacity.setText(item.capacity);
    }

    @Override
    protected void editItem(EditCallback<Section> callback, Section item) {
        item.number = Integer.parseInt(number.getText().toString());
        item.capacity = Integer.parseInt(capacity.getText().toString());
        item.course = courses.get(course.getSelectedItemPosition());
        item.instructor = instructors.get(instructor.getSelectedItemPosition());
        HttpApi.SectionApi().edit(item.id, item).enqueue(callback);
    }

    Callback<List<Course>> courseCallback = new Callback<List<Course>>() {
        @Override
        public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
            courses = response.body();
            String[] depNames = new String[courses.size()];
            for (int i = 0; i < courses.size(); i++) {
                depNames[i] = courses.get(i).name;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(EditSectionActivity.this, android.R.layout.simple_spinner_item, depNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            course.setAdapter(adapter);
            progressDialog.hide();
        }

        @Override
        public void onFailure(Call<List<Course>> call, Throwable t) {

        }
    };

    Callback<List<Instructor>> instructorCallback = new Callback<List<Instructor>>() {
        @Override
        public void onResponse(Call<List<Instructor>> call, Response<List<Instructor>> response) {
            instructors = response.body();
            String[] depNames = new String[instructors.size()];
            for (int i = 0; i < instructors.size(); i++) {
                Instructor ins = instructors.get(i);
                depNames[i] = ins.firstName + " " + ins.lastName;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(EditSectionActivity.this, android.R.layout.simple_spinner_item, depNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            instructor.setAdapter(adapter);
            progressDialog.hide();
        }

        @Override
        public void onFailure(Call<List<Instructor>> call, Throwable t) {

        }
    };
}

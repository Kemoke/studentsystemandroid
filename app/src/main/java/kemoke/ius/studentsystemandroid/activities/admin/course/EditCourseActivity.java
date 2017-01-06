package kemoke.ius.studentsystemandroid.activities.admin.course;

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
import kemoke.ius.studentsystemandroid.models.Program;
import kemoke.ius.studentsystemandroid.util.callback.EditCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCourseActivity extends EditActivity<Course> implements Callback<List<Program>> {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.ects)
    EditText ects;
    @BindView(R.id.program)
    SearchableSpinner program;
    List<Program> programs;

    public EditCourseActivity(){
        super(R.layout.activity_add_course);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpApi.programApi().list().enqueue(this);
    }

    @Override
    protected void loadItem(Course item) {
        name.setText(item.name);
        code.setText(item.code);
        ects.setText(String.valueOf(item.ects));
    }

    @Override
    protected void editItem(EditCallback<Course> callback, Course item) {
        item.name = name.getText().toString();
        item.code = code.getText().toString();
        item.ects = Integer.valueOf(ects.getText().toString());
        item.program = programs.get(program.getSelectedItemPosition());
    }


    @Override
    public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
        programs = response.body();
        String[] depNames = new String[programs.size()];
        for (int i = 0; i < programs.size(); i++) {
            depNames[i] = programs.get(i).name;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, depNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        program.setAdapter(adapter);
        program.setSelection(adapter.getPosition(item.program.name));
        program.setTitle("Select program");
        program.setPositiveButton("Close");
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<List<Program>> call, Throwable t) {

    }
}

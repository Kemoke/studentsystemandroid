package kemoke.ius.studentsystemandroid.activities.admin.student;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.EditActivity;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Program;
import kemoke.ius.studentsystemandroid.models.Student;
import kemoke.ius.studentsystemandroid.util.callback.EditCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditStudentActivity extends EditActivity<Student> implements Callback<List<Program>> {
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.studentId)
    EditText studentId;
    @BindView(R.id.year)
    EditText year;
    @BindView(R.id.semester)
    EditText semester;
    @BindView(R.id.cgpa)
    EditText cgpa;
    @BindView(R.id.program)
    SearchableSpinner program;
    List<Program> programs;
    public EditStudentActivity() {
        super(R.layout.activity_add_student);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        password.setVisibility(View.GONE);
        HttpApi.programApi().list().enqueue(this);
        progressDialog.show();
    }

    @Override
    protected void loadItem(Student item) {
        email.setText(item.email);
        firstName.setText(item.firstName);
        lastName.setText(item.lastName);
        studentId.setText(item.studentId);
        year.setText(String.valueOf(item.year));
        semester.setText(String.valueOf(item.semester));
        cgpa.setText(String.valueOf(item.cgpa));
    }

    @Override
    protected void editItem(EditCallback<Student> callback, Student item) {
        item.email = email.getText().toString();
        item.firstName = firstName.getText().toString();
        item.lastName = lastName.getText().toString();
        item.studentId = studentId.getText().toString();
        item.firstName = firstName.getText().toString();
        item.lastName = lastName.getText().toString();
        item.year = Integer.valueOf(year.getText().toString());
        item.semester = Integer.valueOf(semester.getText().toString());
        item.cgpa = Double.valueOf(cgpa.getText().toString());
        item.program = programs.get(program.getSelectedItemPosition());
        HttpApi.studentApi().edit(item.id, item).enqueue(callback);
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
        program.setPositiveButton("Close");
        program.setTitle("Select Program");
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<List<Program>> call, Throwable t) {

    }
}

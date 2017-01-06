package kemoke.ius.studentsystemandroid.activities.admin.student;

import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.AddActivity;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Program;
import kemoke.ius.studentsystemandroid.models.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentActivity extends AddActivity<Student> implements Callback<List<Program>> {
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
    private List<Program> programs;

    public AddStudentActivity() {
        super(R.layout.activity_add_student);
        HttpApi.programApi().list().enqueue(this);
    }

    @Override
    protected void addItem(Callback<Student> callback) {
        Student student = new Student();
        student.email = email.getText().toString();
        student.password = password.getText().toString();
        student.studentId = studentId.getText().toString();
        student.firstName = firstName.getText().toString();
        student.lastName = lastName.getText().toString();
        student.year = Integer.valueOf(year.getText().toString());
        student.semester = Integer.valueOf(semester.getText().toString());
        student.cgpa = Double.valueOf(cgpa.getText().toString());
        student.program = programs.get(program.getSelectedItemPosition());
        HttpApi.studentApi().add(student).enqueue(callback);
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
        program.setPositiveButton("Close");
        program.setTitle("Select Program");
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<List<Program>> call, Throwable t) {

    }
}

package kemoke.ius.studentsystemandroid.activities.admin.instructor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.AddActivity;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Department;
import kemoke.ius.studentsystemandroid.models.Instructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInstructorActivity extends AddActivity<Instructor> implements Callback<List<Department>> {
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.instructorId)
    EditText instructorId;
    @BindView(R.id.department)
    SearchableSpinner department;
    List<Department> departments;

    public AddInstructorActivity() {
        super(R.layout.activity_add_instructor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpApi.DepartmentApi().list().enqueue(this);
    }

    @Override
    protected void addItem(Callback<Instructor> callback) {
        Instructor instructor = new Instructor();
        instructor.email = email.getText().toString();
        instructor.password = password.getText().toString();
        instructor.firstName = firstName.getText().toString();
        instructor.lastName = lastName.getText().toString();
        instructor.instructorId = instructorId.getText().toString();
        instructor.department = departments.get(department.getSelectedItemPosition());
        HttpApi.InstructorApi().add(instructor).enqueue(callback);
    }

    @Override
    public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
        departments = response.body();
        String[] depNames = new String[departments.size()];
        for (int i = 0; i < departments.size(); i++) {
            depNames[i] = departments.get(i).name;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, depNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(adapter);
        department.setTitle("Select Department");
        department.setPositiveButton("Close");
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<List<Department>> call, Throwable t) {

    }
}

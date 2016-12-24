package kemoke.ius.studentsystemandroid.activities.admin.instructor;

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
import kemoke.ius.studentsystemandroid.models.Department;
import kemoke.ius.studentsystemandroid.models.Instructor;
import kemoke.ius.studentsystemandroid.util.EditCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInstructorActivity extends EditActivity<Instructor> implements Callback<List<Department>> {
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

    public EditInstructorActivity(){
        super(R.layout.activity_add_instructor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpApi.DepartmentApi().list().enqueue(this);
        password.setVisibility(View.GONE);
    }

    @Override
    protected void loadItem(Instructor item) {
        email.setText(item.email);
        firstName.setText(item.firstName);
        lastName.setText(item.lastName);
        instructorId.setText(item.instructorId);
    }

    @Override
    protected void editItem(EditCallback<Instructor> callback, Instructor item) {
        item.email = email.getText().toString();
        item.firstName = firstName.getText().toString();
        item.lastName = lastName.getText().toString();
        item.instructorId = instructorId.getText().toString();
        item.department = departments.get(department.getSelectedItemPosition());
        HttpApi.InstructorApi().edit(item.id, item).enqueue(callback);
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
        department.setSelection(adapter.getPosition(item.department.name));
        department.setTitle("Select Department");
        department.setPositiveButton("Close");
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<List<Department>> call, Throwable t) {

    }
}

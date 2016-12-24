package kemoke.ius.studentsystemandroid.activities.admin.program;

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
import kemoke.ius.studentsystemandroid.models.Program;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProgramActivity extends AddActivity<Program> implements Callback<List<Department>> {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.department)
    SearchableSpinner deptSpinner;
    List<Department> departments;
    Department selectedDepartment;

    protected AddProgramActivity() {
        super(R.layout.activity_add_program);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpApi.DepartmentApi().list().enqueue(this);
        progressDialog.show();
    }

    @Override
    protected void addItem(Callback<Program> callback) {
        Program program = new Program(name.getText().toString(), departments.get(deptSpinner.getSelectedItemPosition()));
        HttpApi.ProgramApi().add(program).enqueue(callback);
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
        deptSpinner.setAdapter(adapter);
        deptSpinner.setTitle("Select Department");
        deptSpinner.setPositiveButton("Close");
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<List<Department>> call, Throwable t) {
        progressDialog.hide();
    }
}

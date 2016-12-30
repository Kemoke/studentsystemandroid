package kemoke.ius.studentsystemandroid.activities.admin.program;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.EditActivity;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Department;
import kemoke.ius.studentsystemandroid.models.Program;
import kemoke.ius.studentsystemandroid.util.EditCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("unchecked")
public class EditProgramActivity extends EditActivity<Program> implements Callback<List<Department>> {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.department)
    SearchableSpinner deptSpinner;
    List<Department> departments;

    public EditProgramActivity() {
        super(R.layout.activity_add_program);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpApi.DepartmentApi().list().enqueue(this);
        progressDialog.show();
    }

    @Override
    protected void loadItem(final Program item) {
        name.setText(item.name);
    }

    @Override
    protected void editItem(EditCallback<Program> callback, Program item) {
        item.name = name.getText().toString();
        item.department = departments.get(deptSpinner.getSelectedItemPosition());
        HttpApi.ProgramApi().edit(item.id, item).enqueue(callback);
    }

    @Override
    public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
        departments = response.body();
        String[] depNames = new String[departments.size()];
        for (int i = 0; i < departments.size(); i++) {
            depNames[i] = departments.get(i).name;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, depNames);
        deptSpinner.setAdapter(adapter);
        deptSpinner.setSelection(((ArrayAdapter<String>)deptSpinner.getAdapter()).getPosition(item.department.name));
        deptSpinner.setTitle("Select Department");
        deptSpinner.setPositiveButton("Close");
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<List<Department>> call, Throwable t) {
        progressDialog.hide();
    }

}

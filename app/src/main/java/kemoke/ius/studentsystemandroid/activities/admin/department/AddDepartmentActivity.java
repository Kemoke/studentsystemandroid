package kemoke.ius.studentsystemandroid.activities.admin.department;

import android.widget.EditText;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.AddActivity;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Department;
import retrofit2.Callback;

public class AddDepartmentActivity extends AddActivity<Department>{

    @BindView(R.id.dept_name)
    EditText deptName;

    public AddDepartmentActivity(){
        super(R.layout.activity_add_department);
    }

    @Override
    protected void addItem(Callback<Department> callback) {
        Department department = new Department(deptName.getText().toString());
        HttpApi.departmentApi().add(department).enqueue(callback);
    }
}

package kemoke.ius.studentsystemandroid.activities.admin.department;

import android.widget.EditText;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.EditActivity;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Department;
import kemoke.ius.studentsystemandroid.util.callback.EditCallback;

public class EditDepartmentActivity extends EditActivity<Department> {

    @BindView(R.id.dept_name)
    EditText deptName;

    protected EditDepartmentActivity() {
        super(R.layout.activity_add_department);
    }

    @Override
    protected void loadItem(Department item) {
        deptName.setText(item.name);
    }

    @Override
    protected void editItem(EditCallback<Department> callback, Department item) {
        item.name = deptName.getText().toString();
        HttpApi.departmentApi().edit(item.id, item).enqueue(callback);
    }
}

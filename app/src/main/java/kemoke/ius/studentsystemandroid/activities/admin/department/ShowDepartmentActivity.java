package kemoke.ius.studentsystemandroid.activities.admin.department;

import android.widget.TextView;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.ShowActivity;
import kemoke.ius.studentsystemandroid.models.Department;

public class ShowDepartmentActivity extends ShowActivity<Department> {

    @BindView(R.id.name)
    TextView nameView;

    public ShowDepartmentActivity() {
        super(R.layout.activity_show_department);
    }


    @Override
    protected void bindView(Department item) {
        nameView.setText(item.name);
    }
}

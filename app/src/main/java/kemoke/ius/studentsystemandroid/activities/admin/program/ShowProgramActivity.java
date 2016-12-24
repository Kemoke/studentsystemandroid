package kemoke.ius.studentsystemandroid.activities.admin.program;

import android.widget.TextView;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.ShowActivity;
import kemoke.ius.studentsystemandroid.models.Program;

public class ShowProgramActivity extends ShowActivity<Program> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.dept)
    TextView dept;

    protected ShowProgramActivity() {
        super(R.layout.activity_show_program);
    }

    @Override
    protected void bindView(Program item) {
        name.setText(item.name);
        dept.setText(item.department.name);
    }

}

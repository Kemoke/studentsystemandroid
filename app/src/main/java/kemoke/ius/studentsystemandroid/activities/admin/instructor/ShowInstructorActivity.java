package kemoke.ius.studentsystemandroid.activities.admin.instructor;

import android.widget.TextView;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.ShowActivity;
import kemoke.ius.studentsystemandroid.models.Instructor;

public class ShowInstructorActivity extends ShowActivity<Instructor> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.dept)
    TextView dept;

    public ShowInstructorActivity() {
        super(R.layout.activity_show_instructor);
    }

    @Override
    protected void bindView(Instructor item) {
        name.setText(item.firstName + " " + item.lastName);
        email.setText(item.email);
        id.setText(item.instructorId);
        dept.setText(item.department.name);
    }
}

package kemoke.ius.studentsystemandroid.activities.admin.course;

import android.widget.TextView;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.ShowActivity;
import kemoke.ius.studentsystemandroid.models.Course;

public class ShowCourseActivity extends ShowActivity<Course> {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.program)
    TextView program;

    public ShowCourseActivity() {
        super(R.layout.activity_show_course);
    }

    @Override
    protected void bindView(Course item) {
        name.setText(item.name);
        code.setText(item.code);
        program.setText(item.program.name);
    }
}

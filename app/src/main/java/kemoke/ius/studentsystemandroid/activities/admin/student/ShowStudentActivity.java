package kemoke.ius.studentsystemandroid.activities.admin.student;

import android.widget.TextView;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.ShowActivity;
import kemoke.ius.studentsystemandroid.models.Student;

public class ShowStudentActivity extends ShowActivity<Student> {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.program)
    TextView program;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.semester)
    TextView semester;
    @BindView(R.id.cgpa)
    TextView cgpa;

    public ShowStudentActivity() {
        super(R.layout.activity_show_student);
    }

    @Override
    protected void bindView(Student item) {
        name.setText(item.firstName + " " + item.lastName);
        email.setText(item.email);
        id.setText(item.studentId);
        program.setText(item.program.name);
        year.setText(String.valueOf(item.year));
        semester.setText(String.valueOf(item.semester));
        cgpa.setText(String.valueOf(item.cgpa));
    }
}

package kemoke.ius.studentsystemandroid.activities.admin.section;

import android.widget.TextView;

import butterknife.BindView;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.ShowActivity;
import kemoke.ius.studentsystemandroid.models.Section;

public class ShowSectionActivity extends ShowActivity<Section> {
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.course)
    TextView course;
    @BindView(R.id.capacity)
    TextView capacity;

    public ShowSectionActivity() {
        super(R.layout.activity_show_section);
    }

    @Override
    protected void bindView(Section item) {
        number.setText(String.valueOf(item.number));
        course.setText(item.course.code + " " + item.course.name);
        capacity.setText(item.students.size() + "/" + item.capacity);
    }
}

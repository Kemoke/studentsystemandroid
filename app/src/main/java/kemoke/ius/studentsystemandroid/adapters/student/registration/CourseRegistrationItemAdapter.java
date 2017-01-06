package kemoke.ius.studentsystemandroid.adapters.student.registration;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.student.RegisterFragment;
import kemoke.ius.studentsystemandroid.models.Course;
import kemoke.ius.studentsystemandroid.models.Section;

public class CourseRegistrationItemAdapter extends RecyclerView.Adapter<CourseRegistrationItemAdapter.ViewHolder> {

    private RegisterFragment fragment;
    private List<Course> courses;

    public void setFragment(RegisterFragment fragment){
        this.fragment = fragment;
    }

    public CourseRegistrationItemAdapter(RegisterFragment fragment, List<Course> courses) {
        this.fragment = fragment;
        this.courses = courses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_registration_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.courseName.setText(course.name);
        holder.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course selectedCourse = courses.get(holder.getAdapterPosition());
                Section section = selectedCourse.sections.get(holder.sectionPicker.getSelectedItemPosition());
                fragment.onRegister(section);
            }
        });
        String[] sections = new String[course.sections.size()];
        for (int i = 0; i < course.sections.size(); i++) {
            Section section = course.sections.get(i);
            sections[i] = "Section " + section.number + ", " + section.instructor.firstName + " " + section.instructor.lastName;
        }
        holder.sectionPicker.setAdapter(new ArrayAdapter<>(fragment.getContext(), android.R.layout.simple_spinner_item, sections));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.course_name)
        TextView courseName;
        @BindView(R.id.register_button)
        AppCompatImageButton registerButton;
        @BindView(R.id.section_picker)
        Spinner sectionPicker;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

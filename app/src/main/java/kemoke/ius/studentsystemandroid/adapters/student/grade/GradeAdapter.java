package kemoke.ius.studentsystemandroid.adapters.student.grade;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.models.Course;
import kemoke.ius.studentsystemandroid.models.StudentGrade;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private List<List<StudentGrade>> indexedGrades;
    private List<Course> courses;
    private Context context;

    public GradeAdapter(Context context, List<StudentGrade> grades) {
        this.context = context;
        indexedGrades = new ArrayList<>();
        courses = new ArrayList<>();
        for (StudentGrade grade : grades) {
            if (!courses.contains(grade.gradeType.section.course)) {
                courses.add(grade.gradeType.section.course);
                indexedGrades.add(new ArrayList<StudentGrade>());
            }
        }
        for (StudentGrade grade : grades) {
            int index = courses.indexOf(grade.gradeType.section.course);
            indexedGrades.get(index).add(grade);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student_grades, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Course course = courses.get(position);
        final List<StudentGrade> studentGrades = indexedGrades.get(position);
        holder.courseName.setText(course.name);
        holder.gradeList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.gradeList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        holder.gradeList.setAdapter(new GradeListAdapter(studentGrades));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.course_name)
        TextView courseName;
        @BindView(R.id.grade_list)
        RecyclerView gradeList;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package kemoke.ius.studentsystemandroid.adapters.student.grade;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.models.StudentGrade;

public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.ViewHolder> {
    private List<StudentGrade> grades;

    public GradeListAdapter(List<StudentGrade> grades) {
        this.grades = grades;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StudentGrade grade = grades.get(position);
        holder.type.setText(grade.gradeType.name + "(" + grade.gradeType.value + ")");
        holder.value.setText(String.valueOf(grade.score));
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.value)
        TextView value;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package kemoke.ius.studentsystemandroid.adapters.student.registration;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.student.RegisterFragment;
import kemoke.ius.studentsystemandroid.models.Course;
import kemoke.ius.studentsystemandroid.models.CurriculumCourse;

@SuppressWarnings("unchecked")
public class CourseRegistrationAdapter extends RecyclerView.Adapter<CourseRegistrationAdapter.ViewHolder> {
    private ArrayList<Course>[] courseCategories;
    private CourseRegistrationItemAdapter[] courseAdapters;
    private Context context;

    public void setContext(RegisterFragment fragment){
        this.context = fragment.getContext();
        for (int i = 0; i < 4; i++) {
            courseAdapters[i] = new CourseRegistrationItemAdapter(fragment, courseCategories[i]);
        }
    }

    public CourseRegistrationAdapter() {
        courseCategories = new ArrayList[4];
        courseAdapters = new CourseRegistrationItemAdapter[4];
        for (int i = 0; i < 4; i++) {
            courseCategories[i] = new ArrayList<>();
        }
    }

    private void clearCategories() {
        for (int i = 0; i < 4; i++) {
            courseCategories[i].clear();
        }
    }

    public void updateCourses(List<CurriculumCourse> curriculumCourses) {
        clearCategories();
        for (CurriculumCourse curriculumCourse : curriculumCourses) {
            courseCategories[curriculumCourse.elective.ordinal()].add(curriculumCourse.course);
        }
        for (int i = 0; i < 4; i++) {
            courseAdapters[i].notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_registration, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String label = "";
        switch (position){
            case 0:
                label = "Required";
                break;
            case 1:
                label = "University Elective";
                break;
            case 2:
                label = "Faculty Elective";
                break;
            case 3:
                label = "Program Elective";
                break;
        }
        holder.label.setText(label);
        holder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "bee", Toast.LENGTH_SHORT).show();
                ViewGroup.LayoutParams params = holder.list.getLayoutParams();
                if(params.height == 0){
                    params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
                } else {
                    params.height = 0;
                }
                holder.list.requestLayout();
            }
        });
        holder.list.setAdapter(courseAdapters[holder.getAdapterPosition()]);
        holder.list.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.label)
        AppCompatTextView label;
        @BindView(R.id.list)
        RecyclerView list;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

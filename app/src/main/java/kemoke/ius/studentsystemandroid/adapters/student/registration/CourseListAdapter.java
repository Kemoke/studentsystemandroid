package kemoke.ius.studentsystemandroid.adapters.student.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.student.TimeTableAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.util.callback.BaseCallback;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private List<Section> sections;
    private Context context;
    private ProgressDialog progressDialog;
    private Section removedSection;
    private TimeTableAdapter timeTableAdapter;

    public CourseListAdapter(List<Section> sections, Context context, TimeTableAdapter timeTableAdapter) {
        this.sections = sections;
        this.context = context;
        this.timeTableAdapter = timeTableAdapter;
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Removing section");
        unregCallback.setProgressDialog(progressDialog);
    }

    public void add(Section section) {
        sections.add(section);
        notifyItemInserted(sections.size() - 1);
        timeTableAdapter.refreshTable(sections);
    }

    public void addRange(List<Section> sections){
        this.sections.addAll(sections);
        notifyDataSetChanged();
        timeTableAdapter.refreshTable(this.sections);
    }

    public void delete(Section section) {
        int index = sections.indexOf(section);
        sections.remove(index);
        notifyItemRemoved(index);
        timeTableAdapter.refreshTable(sections);
    }

    private Section getItem(int position){
        return sections.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student_registration_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Section section = getItem(position);
        holder.name.setText(section.course.code + "." + section.number + " " + section.course.name);
        holder.instructor.setText(section.instructor.firstName + " " + section.instructor.lastName);
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpApi.studentActionsApi().unregister(section).enqueue(unregCallback);
                removedSection = section;
                progressDialog.show();
            }
        });
    }

    private BaseCallback<Section> unregCallback = new BaseCallback<Section>(context, "Failed to unregister section") {
        @Override
        public void onSuccess(Section body) {
            delete(removedSection);
            timeTableAdapter.refreshTable(sections);
        }
    };

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.instructor)
        TextView instructor;
        @BindView(R.id.del_btn)
        AppCompatImageButton delBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

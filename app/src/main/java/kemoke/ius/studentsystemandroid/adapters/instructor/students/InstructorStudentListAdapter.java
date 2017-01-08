package kemoke.ius.studentsystemandroid.adapters.instructor.students;

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
import kemoke.ius.studentsystemandroid.adapters.crud.CrudAdapter;
import kemoke.ius.studentsystemandroid.adapters.student.grade.GradeListAdapter;
import kemoke.ius.studentsystemandroid.models.Student;

public class InstructorStudentListAdapter extends CrudAdapter<InstructorStudentListAdapter.ViewHolder, Student> {
    private List<GradeListAdapter> displayGradeAdapters;
    private final Context context;

    public InstructorStudentListAdapter(Context context){
        this.context = context;
        displayGradeAdapters = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_instructor_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = displayItems.get(position);
        holder.name.setText(student.firstName + " " + student.lastName);
        holder.studentId.setText(student.studentId);
        holder.program.setText(student.program.name);
        holder.gradeList.setAdapter(displayGradeAdapters.get(position));
        holder.gradeList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.gradeList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
    }

    @Override
    public Filter getFilter() {
        return new Filter();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.studentId)
        TextView studentId;
        @BindView(R.id.program)
        TextView program;
        @BindView(R.id.grade_list)
        RecyclerView gradeList;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class Filter extends android.widget.Filter{
        @Override
        protected FilterResults performFiltering(CharSequence input) {
            FilterResults results = new FilterResults();
            ArrayList<Student> filtered = new ArrayList<>();
            if(input.length() == 0){
                filtered.addAll(items);
            } else {
                String filter = input.toString().toLowerCase().trim();
                for (Student item : items) {
                    if(item.firstName.toLowerCase().contains(filter) || item.lastName.toLowerCase().contains(filter)
                            || item.studentId.toLowerCase().contains(filter) || item.email.toLowerCase().contains(filter)){
                        filtered.add(item);
                    }
                }
            }
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence input, FilterResults results) {
            InstructorStudentListAdapter.this.displayItems.clear();
            displayGradeAdapters.clear();
            //noinspection unchecked
            InstructorStudentListAdapter.this.displayItems.addAll((List<Student>) results.values);
            for (Student displayItem : displayItems) {
                displayGradeAdapters.add(new GradeListAdapter(displayItem.grades));
            }
            InstructorStudentListAdapter.this.notifyDataSetChanged();
        }
    }
}

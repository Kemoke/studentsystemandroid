package kemoke.ius.studentsystemandroid.adapters;

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
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.models.TimeIndex;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {
    private List<Section[]> sectionstacks;

    public TimeTableAdapter(List<Course> courses) {
        sectionstacks = new ArrayList<>(40);
        for (Section[] section : sectionstacks) {
            section = new Section[5];
        }
        for (Course course : courses) {
            for (Section section : course.sections) {
                for (TimeIndex timeIndex : section.timeTable) {
                    for (int i = timeIndex.startTime; i < timeIndex.endTime; i++) {
                        sectionstacks.get(i)[timeIndex.day] = section;
                    }
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Section[] sections = sectionstacks.get(position);
        holder.hourLabel.setText(String.valueOf(position+8));
        for (int i = 0; i < 5; i++) {
            if(sections[i] == null){
                holder.getDayView(i).setText("");
            } else {
                holder.getDayView(i).setText(holder.getDayView(i).getText() + "\n" + sections[i].course.code);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 40;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hourLabel)
        TextView hourLabel;
        @BindView(R.id.monLabel)
        TextView monLabel;
        @BindView(R.id.tueLabel)
        TextView tueLabel;
        @BindView(R.id.wedLabel)
        TextView wedLabel;
        @BindView(R.id.thuLabel)
        TextView thuLabel;
        @BindView(R.id.friLabel)
        TextView friLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getDayView(int i){
            switch (i){
                case 0:
                    return monLabel;
                case 1:
                    return tueLabel;
                case 2:
                    return wedLabel;
                case 3:
                    return thuLabel;
                case 4:
                    return friLabel;
                default:
                    throw new IndexOutOfBoundsException("That day is not supported :( " + i);
            }
        }
    }
}

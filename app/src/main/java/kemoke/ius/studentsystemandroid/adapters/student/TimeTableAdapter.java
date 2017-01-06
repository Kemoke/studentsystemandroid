package kemoke.ius.studentsystemandroid.adapters.student;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.models.TimeIndex;
import kemoke.ius.studentsystemandroid.util.ThisApplication;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {
    private String[][] courseCodes;
    private ThisApplication application;

    public TimeTableAdapter(List<Section> sections) {
        application = ThisApplication.getThisApplication();
        refreshTable(sections);
    }

    public void refreshTable(List<Section> sections){
        courseCodes = new String[11][5];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 5; j++) {
                courseCodes[i][j] = "";
            }
        }
        for (Section section : sections) {
            for (TimeIndex timeIndex : section.timeTable) {
                for (int i = timeIndex.startTime; i < timeIndex.endTime; i++) {
                    if(section.course != null)
                        courseCodes[i-8][timeIndex.day] += (courseCodes[i-8][timeIndex.day].length() != 0 ? "\n" : "") + section.course.code;
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timetable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] codes = courseCodes[position];
        holder.hourLabel.setText(String.valueOf(position + 8));
        for (int i = 0; i < 5; i++) {
            TextView dayView = holder.getDayView(i);
            dayView.setText(codes[i]);
            dayView.setBackgroundColor(codes[i].contains("\n") ?
                    ContextCompat.getColor(application, R.color.red) :
                    ContextCompat.getColor(application, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return 11;
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

        TextView getDayView(int i) {
            switch (i) {
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

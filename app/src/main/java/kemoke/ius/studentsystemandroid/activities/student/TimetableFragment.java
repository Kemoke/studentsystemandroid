package kemoke.ius.studentsystemandroid.activities.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.student.TimeTableAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.util.callback.BaseCallback;

public class TimetableFragment extends Fragment {
    @BindView(R.id.timetable)
    RecyclerView timetable;
    List<Section> sections;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_timetable, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        sectionsCallback.setProgressDialog(progressDialog);
        timetable.setLayoutManager(new GridLayoutManager(getContext(), 1));
        HttpApi.studentActionsApi().registeredSections().enqueue(sectionsCallback);
        return view;
    }

    private BaseCallback<List<Section>> sectionsCallback = new BaseCallback<List<Section>>
            (getContext(), "Failed to load sections") {
        @Override
        public void onSuccess(List<Section> body) {
            sections = body;
            timetable.setAdapter(new TimeTableAdapter(sections));
        }
    };
}

package kemoke.ius.studentsystemandroid.activities.instructor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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
    ArrayList<Section> sections;
    ProgressDialog progressDialog;
    private BaseCallback<List<Section>> sectionsCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        sectionsCallback = new BaseCallback<List<Section>>
                (context, "Failed to load sections", progressDialog) {
            @Override
            public void onSuccess(List<Section> body) {
                sections = new ArrayList<>(body);
                timetable.setAdapter(new TimeTableAdapter(sections));
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_timetable, container, false);
        ButterKnife.bind(this, view);
        timetable.setLayoutManager(new GridLayoutManager(getContext(), 1));
        if(savedInstanceState != null){
            sections = savedInstanceState.getParcelableArrayList("sections");
            timetable.setAdapter(new TimeTableAdapter(sections));
        } else {
            progressDialog.show();
            HttpApi.instructorActionsApi().sections().enqueue(sectionsCallback);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("sections", sections);
    }
}

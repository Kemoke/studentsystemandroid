package kemoke.ius.studentsystemandroid.activities.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.student.grade.GradeAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.StudentGrade;
import kemoke.ius.studentsystemandroid.util.callback.BaseCallback;

public class GradeFragment extends Fragment {

    @BindView(R.id.list_view)
    RecyclerView listView;
    private BaseCallback<List<StudentGrade>> gradeCallback;
    private ProgressDialog progressDialog;
    private ArrayList<StudentGrade> studentGrades;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Loading grades");
        progressDialog.setCancelable(false);
        gradeCallback = new BaseCallback<List<StudentGrade>>(context, "Failed to load grades", progressDialog) {
            @Override
            public void onSuccess(List<StudentGrade> body) {
                studentGrades = new ArrayList<>(body);
                listView.setAdapter(new GradeAdapter(context, studentGrades));
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        ButterKnife.bind(this, view);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        if(savedInstanceState != null){
            studentGrades = savedInstanceState.getParcelableArrayList("studentGrades");
            listView.setAdapter(new GradeAdapter(getContext(), studentGrades));
        } else {
            progressDialog.show();
            HttpApi.studentActionsApi().grades().enqueue(gradeCallback);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("studentGrades", studentGrades);
    }
}

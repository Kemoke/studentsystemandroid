package kemoke.ius.studentsystemandroid.activities.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.student.registration.CourseListAdapter;
import kemoke.ius.studentsystemandroid.adapters.student.TimeTableAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.fragments.RegisterCourseFragment;
import kemoke.ius.studentsystemandroid.models.CurriculumCourse;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.util.callback.BaseCallback;

public class RegisterFragment extends Fragment {

    @BindView(R.id.timetable)
    RecyclerView timetable;
    @BindView(R.id.courses)
    RecyclerView courses;
    @BindView(R.id.add_button)
    FloatingActionButton addButton;

    private List<Section> sections;
    private Section addedSection;
    private ProgressDialog progressDialog;
    private CourseListAdapter courseListAdapter;
    private RegisterCourseFragment registerCourseFragment;
    private ArrayList<CurriculumCourse> curriculumCourses;
    private BaseCallback<List<Section>> initCallback;
    private BaseCallback<Section> registerCallback;
    private BaseCallback<List<CurriculumCourse>> courseCallback;

    public RegisterFragment() {
        sections = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initCallback = new BaseCallback<List<Section>>
                (context, "Failed to load registered sections") {
            @Override
            public void onSuccess(List<Section> body) {
                courseListAdapter.addRange(body);
            }
        };
        registerCallback = new BaseCallback<Section>
                (context, "Failed to register section") {
            @Override
            public void onSuccess(Section body) {
                courseListAdapter.add(body);
                for (Iterator<CurriculumCourse> iterator = curriculumCourses.iterator(); iterator.hasNext(); ) {
                    CurriculumCourse course = iterator.next();
                    if(course.course.sections.contains(addedSection)) {
                        iterator.remove();
                        break;
                    }
                }
                registerCourseFragment.dismiss();
            }
        };
        courseCallback = new BaseCallback<List<CurriculumCourse>>
                (context, "Failed to load available courses") {
            @Override
            public void onSuccess(List<CurriculumCourse> body) {
                curriculumCourses = new ArrayList<>(body);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_registration, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getContext());
        registerCallback.setProgressDialog(progressDialog);
        courseCallback.setProgressDialog(progressDialog);
        registerCourseFragment = new RegisterCourseFragment();
        setupListViews();
        HttpApi.studentActionsApi().registeredSections().enqueue(initCallback);
        HttpApi.studentActionsApi().courses().enqueue(courseCallback);
        progressDialog.show();
        return view;
    }

    @OnClick(R.id.add_button)
    public void onAddClick() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("curriculumCourses", curriculumCourses);
        registerCourseFragment.setArguments(bundle);
        registerCourseFragment.show(getChildFragmentManager(), "alert");
    }

    public void onRegister(Section section) {
        HttpApi.studentActionsApi().register(section).enqueue(registerCallback);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
    }

    private void setupListViews() {
        TimeTableAdapter timeTableAdapter = new TimeTableAdapter(sections);
        timetable.setLayoutManager(new LinearLayoutManager(getContext()));
        timetable.setAdapter(timeTableAdapter);
        courseListAdapter = new CourseListAdapter(sections, getContext(), timeTableAdapter);
        courses.setLayoutManager(new LinearLayoutManager(getContext()));
        courses.setAdapter(courseListAdapter);
    }
}

package kemoke.ius.studentsystemandroid.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kemoke.ius.studentsystemandroid.activities.student.RegisterFragment;
import kemoke.ius.studentsystemandroid.adapters.student.registration.CourseRegistrationAdapter;
import kemoke.ius.studentsystemandroid.models.CurriculumCourse;

/**
 * Fragment that contains view for student registration
 */
@SuppressWarnings("ConstantConditions")
public class RegisterCourseFragment extends AppCompatDialogFragment {
    CourseRegistrationAdapter courseAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        courseAdapter = new CourseRegistrationAdapter();
        courseAdapter.setContext((RegisterFragment) getParentFragment());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<CurriculumCourse> curriculumCourses = getArguments().getParcelableArrayList("curriculumCourses");
        updateCourseList(curriculumCourses);
            for (CurriculumCourse course : curriculumCourses) {
                CurriculumCourse curriculumCourse = new CurriculumCourse();
                curriculumCourse.elective = course.elective;
            }
        RecyclerView listView = new RecyclerView(getContext());
        listView.setAdapter(courseAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        return new AlertDialog.Builder(getContext())
                .setView(listView)
                .setTitle("Choose a course")
                .create();
    }

    public void updateCourseList(List<CurriculumCourse> curriculumCourses){
        courseAdapter.updateCourses(curriculumCourses);
    }
}

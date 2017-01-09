package kemoke.ius.studentsystemandroid.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.models.CurriculumCourse;

import static butterknife.ButterKnife.findById;

/**
 * Fragment that defines dialog view for setting Course properties when adding them into Curriculum
 */
@SuppressWarnings("ConstantConditions")
@SuppressLint("InflateParams")
public class CurriculumAddFragment extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final CurriculumCourse addedCourse = getArguments().getParcelable("course");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Course");
        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_curriculum, null, false);
        builder.setView(alertView);
        final EditText year = findById(alertView, R.id.year);
        final EditText semester = findById(alertView, R.id.semester);
        final Spinner elective = findById(alertView, R.id.elective);
        elective.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"No", "University", "Faculty", "Program"}));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addedCourse.year = Integer.parseInt(year.getText().toString());
                addedCourse.semester = Integer.parseInt(semester.getText().toString());
                addedCourse.elective = CurriculumCourse.ElectiveType.values()[elective.getSelectedItemPosition()];
            }
        });
        return builder.create();
    }
}

package kemoke.ius.studentsystemandroid.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.instructor.GradeTypeActivity;
import kemoke.ius.studentsystemandroid.models.GradeType;

@SuppressWarnings("ConstantConditions")
@SuppressLint("InflateParams")
public class EditGradeTypeFragment extends AppCompatDialogFragment {

    @BindView(R.id.grade_type)
    EditText gradeTypeField;
    @BindView(R.id.grade_weight)
    EditText gradeWeightField;
    private GradeTypeActivity activity;
    private GradeType gradeType;
    private boolean isEdit;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GradeTypeActivity) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(activity).inflate(R.layout.fragment_edit_grade_type, null, false);
        ButterKnife.bind(this, view);
        isEdit = getArguments().containsKey("gradeType");
        final String title = getArguments().getString("title");
        if (isEdit) {
            gradeType = getArguments().getParcelable("gradeType");
            gradeTypeField.setText(gradeType.name);
            gradeWeightField.setText(String.valueOf(gradeType.value));
        } else {
            gradeType = new GradeType();
            gradeType.section = getArguments().getParcelable("section");
        }
        return new AlertDialog.Builder(activity)
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gradeType.name = gradeTypeField.getText().toString();
                        gradeType.value = Integer.parseInt(gradeWeightField.getText().toString());
                        if (isEdit) {
                            activity.onGradeEdit(gradeType);
                        } else {
                            activity.onGradeAdd(gradeType);
                        }
                    }
                })
                .setTitle(title)
                .create();
    }
}

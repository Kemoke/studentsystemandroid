package kemoke.ius.studentsystemandroid.activities.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;

/**
 * Created by Kemal on 03.01.2017..
 */

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_registration, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}

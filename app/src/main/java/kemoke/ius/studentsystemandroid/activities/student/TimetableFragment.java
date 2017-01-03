package kemoke.ius.studentsystemandroid.activities.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.TimeTableAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Course;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimetableFragment extends Fragment implements Callback<List<Course>> {
    @BindView(R.id.timetable)
    RecyclerView timetable;
    List<Course> courses;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_timetable, container, false);
        ButterKnife.bind(this, view);
        timetable.setLayoutManager(new GridLayoutManager(getContext(), 1));
        HttpApi.StudentApi().courses().enqueue(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return view;
    }

    @Override
    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
        if(response.code() == 200){
            courses = response.body();
            timetable.setAdapter(new TimeTableAdapter(courses));
        }else{
            try {
                Log.e("err", response.errorBody().string());
                Toast.makeText(getContext(), "Failed to load sections", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<List<Course>> call, Throwable t) {
        progressDialog.hide();
        Log.e("err", t.getMessage());
        Toast.makeText(getContext(), "Failed to load sections", Toast.LENGTH_SHORT).show();
    }
}

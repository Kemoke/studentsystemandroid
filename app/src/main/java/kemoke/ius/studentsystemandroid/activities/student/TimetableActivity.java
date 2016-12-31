package kemoke.ius.studentsystemandroid.activities.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class TimetableActivity extends AppCompatActivity implements Callback<List<Course>> {
    @BindView(R.id.timetable)
    RecyclerView timetable;
    List<Course> courses;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ButterKnife.bind(this);
        timetable.setLayoutManager(new GridLayoutManager(this, 5));
        HttpApi.StudentApi().courses().enqueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
        if(response.code() == 200){
            courses = response.body();
            timetable.setAdapter(new TimeTableAdapter(courses));
        }else{
            try {
                Log.e("err", response.errorBody().string());
                Toast.makeText(this, "Failed to load sections", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Failed to load sections", Toast.LENGTH_SHORT).show();
    }
}

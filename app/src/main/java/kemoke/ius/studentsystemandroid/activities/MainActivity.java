package kemoke.ius.studentsystemandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.ListFragment;
import kemoke.ius.studentsystemandroid.activities.admin.course.CourseListFragment;
import kemoke.ius.studentsystemandroid.activities.admin.department.DepartmentListFragment;
import kemoke.ius.studentsystemandroid.activities.admin.instructor.InstructorListFragment;
import kemoke.ius.studentsystemandroid.activities.admin.program.ProgramListFragment;
import kemoke.ius.studentsystemandroid.activities.admin.section.SectionListFragment;
import kemoke.ius.studentsystemandroid.activities.admin.student.StudentListFragment;
import kemoke.ius.studentsystemandroid.activities.instructor.SectionFragment;
import kemoke.ius.studentsystemandroid.activities.news.NewsFragment;
import kemoke.ius.studentsystemandroid.activities.student.GradeFragment;
import kemoke.ius.studentsystemandroid.activities.student.RegisterFragment;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kemoke.ius.studentsystemandroid.util.ThisApplication.getThisApplication;

/**
 * This activity serves as the container for the whole application.
 * It contains the navigation drawer and toolbar.
 * Depending on which nav item is selected the corresponding Fragment is injected into ContentView.
 */
@SuppressWarnings("SameReturnValue")
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Callback<User> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    SearchView searchView;
    TextView nameView;
    TextView emailView;
    int selectedItem;
    Fragment currentFragment;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initNavDrawer();
        loadUserInfo();
        loadViewFragment(savedInstanceState);
    }

    private void initNavDrawer() {
        nameView = (TextView) navView.getHeaderView(0).findViewById(R.id.nav_full_name);
        emailView = (TextView) navView.getHeaderView(0).findViewById(R.id.nav_email);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        switch (getThisApplication().getUserType()) {
            case "Admin":
                navView.inflateMenu(R.menu.drawer_admin_menu);
                break;
            case "Student":
                navView.inflateMenu(R.menu.drawer_student_menu);
                break;
            case "Instructor":
                navView.inflateMenu(R.menu.drawer_instructor_menu);
        }
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    private void loadViewFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt("selectedItem", -1);
            currentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, currentFragment, "main")
                    .commit();
            navView.setCheckedItem(selectedItem);
        } else {
            onNavigationItemSelected(R.id.nav_news);
        }
    }

    private void loadUserInfo() {
        user = getThisApplication().getUser();
        if (user == null) {
            HttpApi.authApi().self().enqueue(this);
        } else {
            nameView.setText(user.firstName + " " + user.lastName);
            emailView.setText(user.email);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (getThisApplication().getUserType().equals("Admin") && currentFragment != null && currentFragment.getClass() != NewsFragment.class) {
            searchView.setOnQueryTextListener((ListFragment) currentFragment);
            searchView.setVisibility(View.VISIBLE);
        } else {
            searchView.setVisibility(View.GONE);
        }
        return true;
    }

    private boolean onNavigationItemSelected(int id) {
        navView.setCheckedItem(id);
        selectedItem = id;
        switch (selectedItem) {
            case R.id.nav_news:
                currentFragment = new NewsFragment();
                setTitle("News");
                break;
            case R.id.nav_depts:
                currentFragment = new DepartmentListFragment();
                setTitle("Departments");
                break;
            case R.id.nav_programs:
                currentFragment = new ProgramListFragment();
                setTitle("Programs");
                break;
            case R.id.nav_students:
                currentFragment = new StudentListFragment();
                setTitle("Students");
                break;
            case R.id.nav_instructors:
                currentFragment = new InstructorListFragment();
                setTitle("Instructors");
                break;
            case R.id.nav_courses:
                currentFragment = new CourseListFragment();
                setTitle("Courses");
                break;
            case R.id.nav_sections:
                currentFragment = new SectionListFragment();
                setTitle("sections");
                break;
            case R.id.nav_timetable:
                if(getThisApplication().getUserType().equals("Instructor"))
                    currentFragment = new kemoke.ius.studentsystemandroid.activities.instructor.TimetableFragment();
                else
                    currentFragment = new kemoke.ius.studentsystemandroid.activities.student.TimetableFragment();
                setTitle("Timetable");
                break;
            case R.id.nav_registration:
                currentFragment = new RegisterFragment();
                setTitle("Registration");
                break;
            case R.id.nav_grades:
                currentFragment = new GradeFragment();
                setTitle("Grades");
                break;
            case R.id.nav_instructor_sections:
                currentFragment = new SectionFragment();
                setTitle("Sections");
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, currentFragment, "main")
                .commit();
        if (getThisApplication().getUserType().equals("Admin") && currentFragment.getClass() != NewsFragment.class
                || currentFragment.getClass() == SectionFragment.class) {
            if (searchView != null) {
                searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) currentFragment);
                searchView.setVisibility(View.VISIBLE);
            }
        } else {
            if (searchView != null)
                searchView.setVisibility(View.GONE);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_logout:
                getThisApplication().setToken("");
                getThisApplication().setUserType("");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return onNavigationItemSelected(item.getItemId());
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.code() == 200) {
            User user = response.body();
            getThisApplication().setUser(user);
            nameView.setText(user.firstName + " " + user.lastName);
            emailView.setText(user.email);
        } else {
            try {
                Log.e("srv err", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Failed to load user info", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.e("net err", t.getMessage());
        Toast.makeText(this, "Failed to load user info", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"fragment", currentFragment);
        outState.putInt("selectedItem", selectedItem);
    }
}

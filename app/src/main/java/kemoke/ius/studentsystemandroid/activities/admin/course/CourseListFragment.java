package kemoke.ius.studentsystemandroid.activities.admin.course;

import kemoke.ius.studentsystemandroid.activities.admin.ListFragment;
import kemoke.ius.studentsystemandroid.adapters.CourseListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Course;
import kemoke.ius.studentsystemandroid.util.DeleteCallback;
import kemoke.ius.studentsystemandroid.util.InitCallback;

public class CourseListFragment extends ListFragment<Course> {

    public CourseListFragment(){
        super(new CourseListAdapter(), EditCourseActivity.class,
                ShowCourseActivity.class, AddCourseActivity.class);
    }
    @Override
    protected void loadItems(InitCallback<Course> callback) {
        HttpApi.CourseApi().listWithProps("program").enqueue(callback);
    }

    @Override
    protected void deleteItem(DeleteCallback<Course> callback, int itemId) {
        HttpApi.CourseApi().delete(itemId).enqueue(callback);
    }
}

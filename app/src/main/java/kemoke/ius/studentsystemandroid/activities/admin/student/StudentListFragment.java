package kemoke.ius.studentsystemandroid.activities.admin.student;

import kemoke.ius.studentsystemandroid.activities.admin.ListFragment;
import kemoke.ius.studentsystemandroid.adapters.StudentListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Student;
import kemoke.ius.studentsystemandroid.util.DeleteCallback;
import kemoke.ius.studentsystemandroid.util.InitCallback;

public class StudentListFragment extends ListFragment<Student> {
    public StudentListFragment() {
        super(new StudentListAdapter(), EditStudentActivity.class, ShowStudentActivity.class, AddStudentActivity.class);
    }

    @Override
    protected void loadItems(InitCallback<Student> callback) {
        HttpApi.StudentApi().listWithProps("program").enqueue(callback);
    }

    @Override
    protected void deleteItem(DeleteCallback<Student> callback, int itemId) {
        HttpApi.StudentApi().delete(itemId).enqueue(callback);
    }
}

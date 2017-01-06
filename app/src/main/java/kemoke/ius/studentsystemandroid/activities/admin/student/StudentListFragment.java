package kemoke.ius.studentsystemandroid.activities.admin.student;

import kemoke.ius.studentsystemandroid.activities.admin.ListFragment;
import kemoke.ius.studentsystemandroid.adapters.crud.StudentListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Student;
import kemoke.ius.studentsystemandroid.util.callback.DeleteCallback;
import kemoke.ius.studentsystemandroid.util.callback.InitCallback;

public class StudentListFragment extends ListFragment<Student> {
    public StudentListFragment() {
        super(new StudentListAdapter(), EditStudentActivity.class, ShowStudentActivity.class, AddStudentActivity.class);
    }

    @Override
    protected void loadItems(InitCallback<Student> callback) {
        HttpApi.studentApi().listWithProps("program").enqueue(callback);
    }

    @Override
    protected void deleteItem(DeleteCallback<Student> callback, int itemId) {
        HttpApi.studentApi().delete(itemId).enqueue(callback);
    }
}

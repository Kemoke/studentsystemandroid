package kemoke.ius.studentsystemandroid.activities.admin.instructor;

import kemoke.ius.studentsystemandroid.activities.admin.ListFragment;
import kemoke.ius.studentsystemandroid.adapters.crud.InstructorListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Instructor;
import kemoke.ius.studentsystemandroid.util.callback.DeleteCallback;
import kemoke.ius.studentsystemandroid.util.callback.InitCallback;

public class InstructorListFragment extends ListFragment<Instructor>{
    public InstructorListFragment(){
        super(new InstructorListAdapter(), EditInstructorActivity.class,
                ShowInstructorActivity.class, AddInstructorActivity.class);
    }
    @Override
    protected void loadItems(InitCallback<Instructor> callback) {
        HttpApi.instructorApi().listWithProps("department").enqueue(callback);
    }

    @Override
    protected void deleteItem(DeleteCallback<Instructor> callback, int itemId) {
        HttpApi.instructorApi().delete(itemId).enqueue(callback);
    }
}

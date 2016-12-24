package kemoke.ius.studentsystemandroid.activities.admin.program;

import kemoke.ius.studentsystemandroid.activities.admin.ListFragment;
import kemoke.ius.studentsystemandroid.adapters.ProgramListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Program;
import kemoke.ius.studentsystemandroid.util.DeleteCallback;
import kemoke.ius.studentsystemandroid.util.InitCallback;

public class ProgramListFragment extends ListFragment<Program> {
    public ProgramListFragment(){
        super(new ProgramListAdapter(), EditProgramActivity.class,
                ShowProgramActivity.class, AddProgramActivity.class);
    }

    @Override
    public void loadItems(InitCallback<Program> callback) {
        HttpApi.ProgramApi().listWithProps("department").enqueue(callback);
    }

    @Override
    public void deleteItem(DeleteCallback<Program> callback, int itemId) {
        HttpApi.ProgramApi().delete(itemId).enqueue(callback);
    }
}

package kemoke.ius.studentsystemandroid.activities.admin.program;

import android.content.Intent;
import android.view.MenuItem;

import kemoke.ius.studentsystemandroid.R;
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
        HttpApi.ProgramApi().listWithProps("department,curriculum.course").enqueue(callback);
    }

    @Override
    public void deleteItem(DeleteCallback<Program> callback, int itemId) {
        HttpApi.ProgramApi().delete(itemId).enqueue(callback);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        Program item = items.get(adapter.getPosition());
        switch (id){
            case R.id.menu_curriculum:
                Intent intent = new Intent(getContext(), CurriculumActivity.class);
                intent.putExtra("program", item);
                startActivity(intent);
                return true;
        }

        return super.onContextItemSelected(menuItem);
    }
}

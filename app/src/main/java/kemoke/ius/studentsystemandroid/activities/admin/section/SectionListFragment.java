package kemoke.ius.studentsystemandroid.activities.admin.section;

import kemoke.ius.studentsystemandroid.activities.admin.ListFragment;
import kemoke.ius.studentsystemandroid.adapters.SectionListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.util.DeleteCallback;
import kemoke.ius.studentsystemandroid.util.InitCallback;

public class SectionListFragment extends ListFragment<Section> {
    public SectionListFragment(){
        super(new SectionListAdapter(), EditSectionActivity.class,
                ShowSectionActivity.class, AddSectionActivity.class);
    }
    @Override
    protected void loadItems(InitCallback<Section> callback) {
        HttpApi.SectionApi().listWithProps("course,instructor,students").enqueue(callback);
    }

    @Override
    protected void deleteItem(DeleteCallback<Section> callback, int itemId) {
        HttpApi.SectionApi().delete(itemId).enqueue(callback);
    }
}

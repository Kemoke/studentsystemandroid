package kemoke.ius.studentsystemandroid.activities.instructor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.activities.admin.section.ShowSectionActivity;
import kemoke.ius.studentsystemandroid.adapters.instructor.InstructorSectionListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.util.callback.BaseCallback;

public class SectionFragment extends Fragment implements SearchView.OnQueryTextListener {

    @BindView(R.id.section_list)
    RecyclerView sectionList;
    private BaseCallback<List<Section>> sectionCallback;
    private ProgressDialog progressDialog;
    private ArrayList<Section> sections;
    private AdapterView.OnItemClickListener onItemClickListener;
    private InstructorSectionListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading sections");
        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ShowSectionActivity.class);
                intent.putExtra("item", sections.get(position));
                startActivity(intent);
            }
        };
        sectionCallback = new BaseCallback<List<Section>>
                (context, "Failed to load sections", progressDialog) {
            @Override
            public void onSuccess(List<Section> body) {
                sections = new ArrayList<>(body);
                adapter.setItems(sections);
                adapter.notifyDataSetChanged();
                adapter.getFilter().filter("");
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        ButterKnife.bind(this, view);
        sectionList.setLayoutManager(new LinearLayoutManager(getContext()));
        sectionList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new InstructorSectionListAdapter();
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setItems(sections);
        sectionList.setAdapter(adapter);
        registerForContextMenu(sectionList);
        if(savedInstanceState != null){
            sections = savedInstanceState.getParcelableArrayList("sections");
            adapter.setItems(sections);
            adapter.notifyDataSetChanged();
            adapter.getFilter().filter("");
        } else {
            HttpApi.instructorActionsApi().sections().enqueue(sectionCallback);
            progressDialog.show();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("sections", sections);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        int pos = adapter.getPosition();
        Section section = sections.get(pos);
        Intent intent;
        switch (id){
            case R.id.students:
                intent = new Intent(getContext(), StudentListActivity.class);
                intent.putExtra("section", section);
                intent.putExtra("position", pos);
                startActivityForResult(intent, 1);
                return true;
            case R.id.grade_types:
                intent = new Intent(getContext(), GradeTypeActivity.class);
                intent.putExtra("section", section);
                intent.putExtra("position", pos);
                startActivityForResult(intent, 2);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int pos;
        Section section;
        if(resultCode == 1){
            switch (requestCode){
                case 1:
                    section = data.getParcelableExtra("section");
                    pos = data.getIntExtra("position", -1);
                    sections.get(pos).students = section.students;
                    break;
                case 2:
                    section = data.getParcelableExtra("section");
                    pos = data.getIntExtra("position", -1);
                    sections.get(pos).gradeTypes = section.gradeTypes;
                    break;
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }
}

package kemoke.ius.studentsystemandroid.activities.admin.department;

import kemoke.ius.studentsystemandroid.activities.admin.ListFragment;
import kemoke.ius.studentsystemandroid.adapters.DepartmentListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Department;
import kemoke.ius.studentsystemandroid.util.DeleteCallback;
import kemoke.ius.studentsystemandroid.util.InitCallback;

public class DepartmentListFragment extends ListFragment<Department> {

    public DepartmentListFragment() {
        super(new DepartmentListAdapter(), EditDepartmentActivity.class,
                ShowDepartmentActivity.class, AddDepartmentActivity.class);
    }

    @Override
    public void loadItems(InitCallback<Department> callback) {
        HttpApi.DepartmentApi().list().enqueue(callback);
    }

    @Override
    public void deleteItem(DeleteCallback<Department> callback, int itemId) {
        HttpApi.DepartmentApi().delete(itemId).enqueue(callback);
    }
}

/*public class DepartmentListFragment extends Fragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.department_list)
    RecyclerView departmentList;
    @BindView(R.id.add_button)
    FloatingActionButton addButton;

    ProgressDialog progressDialog;
    List<Department> departments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);
        departmentList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        departmentList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        departments = new ArrayList<>();
        departmentList.setAdapter(new DepartmentListAdapter(departments));
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading departments");
        progressDialog.show();
        registerForContextMenu(departmentList);
        departmentList.setClickable(true);
        ((DepartmentListAdapter)departmentList.getAdapter()).setOnItemClickListener(this);
        DepartmentApi().list().enqueue(new InitCallback<>(getContext(), departments, departmentList, progressDialog));
        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int deletePos = ((DepartmentListAdapter) departmentList.getAdapter()).getPosition();
        Department department = departments.get(deletePos);
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_edit:
                intent = new Intent(getContext(), EditDepartmentActivity.class);
                intent.putExtra("department", department);
                startActivityForResult(intent, 2);
                return true;
            case R.id.menu_delete:
                progressDialog.setMessage("Deleting");
                progressDialog.show();
                DepartmentApi().delete(department.id).enqueue(new DeleteCallback<>(departments,
                        departmentList, progressDialog, getContext(), deletePos));
                return true;
            case R.id.menu_programs:
                return true;
            case R.id.menu_instructors:
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @OnClick(R.id.add_button)
    public void onAddClick() {
        Intent intent = new Intent(getContext(), AddDepartmentActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DepartmentListAdapter adapter = (DepartmentListAdapter)departmentList.getAdapter();
        if(data == null) return;
        Department department = data.getParcelableExtra("department");
        if(requestCode == 1){
            departments.add(department);
            adapter.notifyItemInserted(departments.size()-1);
        } else if(requestCode == 2){
            for (int i = 0; i < departments.size(); i++) {
                if(departments.get(i).id == resultCode){
                    departments.set(i, department);
                    adapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getContext(), ShowDepartmentActivity.class);
        Department department = departments.get(i);
        intent.putExtra("department", department);
        startActivity(intent);
    }
}*/

package kemoke.ius.studentsystemandroid.activities.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import butterknife.OnClick;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.CrudAdapter;
import kemoke.ius.studentsystemandroid.models.BaseModel;
import kemoke.ius.studentsystemandroid.util.DeleteCallback;
import kemoke.ius.studentsystemandroid.util.InitCallback;

public abstract class ListFragment<T extends BaseModel> extends Fragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.add_button)
    FloatingActionButton addButton;
    protected ProgressDialog progressDialog;
    final List<T> items;
    final CrudAdapter<? extends RecyclerView.ViewHolder, T> adapter;
    final Class editActivity;
    final Class showActivity;
    final Class addActivity;

    protected ListFragment(@NonNull CrudAdapter<? extends RecyclerView.ViewHolder, T> adapter, @NonNull Class editActivity,
                           @NonNull Class showActivity, @NonNull Class addActivity) {
        this.adapter = adapter;
        this.editActivity = editActivity;
        this.showActivity = showActivity;
        this.addActivity = addActivity;
        this.items = new ArrayList<>();
        this.adapter.setItems(items);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);
        listView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(adapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        registerForContextMenu(listView);
        listView.setClickable(true);
        adapter.setOnItemClickListener(this);
        loadItems(new InitCallback<>(getContext(), items, listView, progressDialog));
        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        int deletePos = adapter.getPosition();
        T item = items.get(deletePos);
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.menu_edit:
                intent = new Intent(getContext(), editActivity);
                intent.putExtra("item", item);
                startActivityForResult(intent, 2);
                return true;
            case R.id.menu_delete:
                progressDialog.setMessage("Deleting");
                progressDialog.show();
                deleteItem(new DeleteCallback<>(items,
                        listView, progressDialog, getContext(), deletePos),item.id);
                return true;
        }
        return super.onContextItemSelected(menuItem);
    }

    @OnClick(R.id.add_button)
    public void onAddClick() {
        Intent intent = new Intent(getContext(), addActivity);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        T item = data.getParcelableExtra("item");
        switch (requestCode) {
            case 1:
                adapter.add(item);
                break;
            case 2:
                adapter.edit(item);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getContext(), showActivity);
        T item = items.get(i);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    protected abstract void loadItems(InitCallback<T> callback);

    protected abstract void deleteItem(DeleteCallback<T> callback, int itemId);

    @Override
    public boolean onQueryTextChange(String input) {
        adapter.getFilter().filter(input);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
package kemoke.ius.studentsystemandroid.adapters;

import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.models.Department;
import kemoke.ius.studentsystemandroid.util.ThisApplication;

/**
 * Created by Kemoke on 24.11.2016..
 */

public class DepartmentListAdapter extends CrudAdapter<DepartmentListAdapter.ViewHolder, Department> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Department department = displayItems.get(position);
        holder.nameText.setText(department.name);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(null, view, holder.getAdapterPosition(), 0);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        @BindView(R.id.dept_list_name)
        TextView nameText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            new SupportMenuInflater(ThisApplication.getThisApplication()).inflate(R.menu.department_context_menu, contextMenu);
        }
    }

    private class Filter extends android.widget.Filter{
        @Override
        protected FilterResults performFiltering(CharSequence input) {
            FilterResults results = new FilterResults();
            ArrayList<Department> filtered = new ArrayList<>();
            if(input.length() == 0){
                filtered.addAll(items);
            } else {
                String filter = input.toString().toLowerCase().trim();
                for (Department item : items) {
                    if(item.name.toLowerCase().contains(filter) || getAbbr(item.name).toLowerCase().contains(filter)) {
                        filtered.add(item);
                    }
                }
            }
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence input, FilterResults results) {
            DepartmentListAdapter.this.displayItems.clear();
            DepartmentListAdapter.this.displayItems.addAll((List<Department>) results.values);
            DepartmentListAdapter.this.notifyDataSetChanged();
        }
    }
}

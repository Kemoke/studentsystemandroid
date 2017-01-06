package kemoke.ius.studentsystemandroid.adapters.crud;

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
import kemoke.ius.studentsystemandroid.models.Instructor;
import kemoke.ius.studentsystemandroid.util.ThisApplication;

public class InstructorListAdapter extends CrudAdapter<InstructorListAdapter.ViewHolder, Instructor> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_instructor, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Instructor instructor = displayItems.get(position);
        holder.name.setText(instructor.firstName + " " + instructor.lastName);
        holder.instructorId.setText(instructor.instructorId);
        holder.department.setText(instructor.department.name);
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
    public android.widget.Filter getFilter() {
        return new Filter();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.instructorId)
        TextView instructorId;
        @BindView(R.id.department)
        TextView department;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            new SupportMenuInflater(ThisApplication.getThisApplication()).inflate(R.menu.instructor_context_menu, contextMenu);
        }
    }

    public class Filter extends android.widget.Filter{
        @Override
        protected FilterResults performFiltering(CharSequence input) {
            FilterResults results = new FilterResults();
            ArrayList<Instructor> filtered = new ArrayList<>();
            if(input.length() == 0){
                filtered.addAll(items);
            } else {
                String filter = input.toString().toLowerCase().trim();
                for (Instructor item : items) {
                    if(item.firstName.toLowerCase().contains(filter) || item.lastName.toLowerCase().contains(filter)
                            || item.instructorId.toLowerCase().contains(filter) || item.email.toLowerCase().contains(filter)){
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
            InstructorListAdapter.this.displayItems.clear();
            //noinspection unchecked
            InstructorListAdapter.this.displayItems.addAll((List<Instructor>) results.values);
            InstructorListAdapter.this.notifyDataSetChanged();
        }
    }
}

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
import kemoke.ius.studentsystemandroid.models.Course;
import kemoke.ius.studentsystemandroid.util.ThisApplication;

public class CourseListAdapter extends CrudAdapter<CourseListAdapter.ViewHolder, Course> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Course course = displayItems.get(position);
        holder.code.setText(course.code);
        holder.name.setText(course.name);
        holder.program.setText(course.program.name);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        @BindView(R.id.code)
        TextView code;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.program)
        TextView program;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            new SupportMenuInflater(ThisApplication.getThisApplication()).inflate(R.menu.course_context_menu, contextMenu);
        }
    }

    public class Filter extends android.widget.Filter{

        @Override
        protected FilterResults performFiltering(CharSequence input) {
            FilterResults results = new FilterResults();
            ArrayList<Course> filtered = new ArrayList<>();
            if(input.length() == 0){
                filtered.addAll(items);
            } else {
                String filter = input.toString().toLowerCase().trim();
                for (Course item : items) {
                    if(item.name.toLowerCase().contains(filter) || item.code.toLowerCase().contains(filter)
                            || getAbbr(item.name).toLowerCase().contains(filter)){
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
            CourseListAdapter.this.displayItems.clear();
            //noinspection unchecked
            CourseListAdapter.this.displayItems.addAll((List<Course>) results.values);
            CourseListAdapter.this.notifyDataSetChanged();
        }
    }
}

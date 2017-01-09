package kemoke.ius.studentsystemandroid.adapters.instructor;

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
import kemoke.ius.studentsystemandroid.adapters.crud.CrudAdapter;
import kemoke.ius.studentsystemandroid.models.Section;
import kemoke.ius.studentsystemandroid.util.ThisApplication;

public class InstructorSectionListAdapter extends CrudAdapter<InstructorSectionListAdapter.ViewHolder, Section> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Section section = displayItems.get(position);
        holder.number.setText(String.valueOf(section.number));
        holder.course.setText(section.course.name);
        holder.instructor.setText(section.instructor.firstName + " " + section.instructor.lastName);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.course)
        TextView course;
        @BindView(R.id.instructor)
        TextView instructor;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            new SupportMenuInflater(ThisApplication.getThisApplication()).inflate(R.menu.instructor_section_context_menu, contextMenu);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public class Filter extends android.widget.Filter{
        @Override
        protected FilterResults performFiltering(CharSequence input) {
            FilterResults results = new FilterResults();
            ArrayList<Section> filtered = new ArrayList<>();
            if(input.length() == 0){
                filtered.addAll(items);
            } else {
                String filter = input.toString().toLowerCase().trim();
                for (Section item : items) {
                    if(item.course.name.toLowerCase().contains(filter) || item.course.code.toLowerCase().contains(filter)){
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
            InstructorSectionListAdapter.this.displayItems.clear();
            //noinspection unchecked
            InstructorSectionListAdapter.this.displayItems.addAll((List<Section>) results.values);
            InstructorSectionListAdapter.this.notifyDataSetChanged();
        }
    }
}

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
import kemoke.ius.studentsystemandroid.models.Program;
import kemoke.ius.studentsystemandroid.util.ThisApplication;

public class ProgramListAdapter extends CrudAdapter<ProgramListAdapter.ViewHolder, Program> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_program, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Program program = displayItems.get(position);
        holder.nameText.setText(program.name);
        holder.deptName.setText(program.department.name);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        @BindView(R.id.name)
        TextView nameText;
        @BindView(R.id.deptName)
        TextView deptName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            new SupportMenuInflater(ThisApplication.getThisApplication()).inflate(R.menu.program_context_menu, contextMenu);
        }
    }

    public class Filter extends android.widget.Filter{
        @Override
        protected FilterResults performFiltering(CharSequence input) {
            FilterResults results = new FilterResults();
            ArrayList<Program> filtered = new ArrayList<>();
            if(input.length() == 0){
                filtered.addAll(items);
            } else {
                String filter = input.toString().toLowerCase().trim();
                for (Program item : items) {
                    if(item.name.toLowerCase().contains(filter) || getAbbr(item.name).toLowerCase().contains(filter)){
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
            ProgramListAdapter.this.displayItems.clear();
            //noinspection unchecked
            ProgramListAdapter.this.displayItems.addAll((List<Program>) results.values);
            ProgramListAdapter.this.notifyDataSetChanged();
        }
    }
}

package kemoke.ius.studentsystemandroid.adapters.crud;

import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import kemoke.ius.studentsystemandroid.models.BaseModel;

/**
 * Generic adapter for showing list of CRUD models
 * @param <VH> Viewholder for views of crud models
 * @param <T> Model for crud operations
 */
public abstract class CrudAdapter<VH extends RecyclerView.ViewHolder, T extends BaseModel> extends RecyclerView.Adapter<VH> implements Filterable {
    private int position;

    public void setItems(List<T> items) {
        this.items = items;
    }

    List<T> items;

    public void delete(int position) {
        T item = displayItems.remove(position);
        items.remove(item);
        notifyItemRemoved(position);
    }

    public void add(T item) {
        items.add(item);
        displayItems.add(item);
        notifyItemInserted(displayItems.size() - 1);
    }

    public void edit(T item) {
        items.set(items.indexOf(item), item);
        int index = displayItems.indexOf(item);
        displayItems.set(index, item);
        notifyItemChanged(index);
    }

    List<T> displayItems;

    public int getPosition() {
        return position;
    }

    AdapterView.OnItemClickListener onItemClickListener;

    public CrudAdapter() {
        displayItems = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    String getAbbr(String input) {
        String abbr = "";
        for (int i = 0; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))) {
                abbr += input.charAt(i);
            }
        }
        return abbr;
    }

}

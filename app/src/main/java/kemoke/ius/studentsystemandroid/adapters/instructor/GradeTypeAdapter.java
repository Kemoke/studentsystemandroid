package kemoke.ius.studentsystemandroid.adapters.instructor;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.models.GradeType;

public class GradeTypeAdapter extends RecyclerView.Adapter<GradeTypeAdapter.ViewHolder> {
    private List<GradeType> gradeTypes;
    private int position;
    private View.OnClickListener onDeleteListener;
    private View.OnClickListener onEditListener;

    public GradeTypeAdapter(List<GradeType> gradeTypes) {
        this.gradeTypes = gradeTypes;
    }

    public void add(GradeType gradeType) {
        gradeTypes.add(0, gradeType);
        notifyItemInserted(0);
    }

    public void remove(int index) {
        gradeTypes.remove(index);
        notifyItemRemoved(index);
    }

    public void edit(GradeType gradeType) {
        int index = gradeTypes.indexOf(gradeType);
        gradeTypes.set(index, gradeType);
        notifyItemChanged(index);
    }

    public int getPosition() {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_grade_type, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {
        GradeType gradeType = gradeTypes.get(pos);
        holder.name.setText(gradeType.name);
        holder.weight.setText(String.valueOf(gradeType.value));
        holder.editBtn.setOnClickListener(onEditListener);
        holder.deleteBtn.setOnClickListener(onDeleteListener);
    }

    @Override
    public int getItemCount() {
        return gradeTypes.size();
    }

    public View.OnClickListener getOnEditListener() {
        return onEditListener;
    }

    public void setOnEditListener(View.OnClickListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    public View.OnClickListener getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(View.OnClickListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.weight)
        TextView weight;
        @BindView(R.id.delete_btn)
        AppCompatImageButton deleteBtn;
        @BindView(R.id.edit_btn)
        AppCompatImageButton editBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}

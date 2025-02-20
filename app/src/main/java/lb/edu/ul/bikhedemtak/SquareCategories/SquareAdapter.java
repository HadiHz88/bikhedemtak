package lb.edu.ul.bikhedemtak.SquareCategories;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;

public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.SquareViewHolder> {

    private List<SquareItem> squareItems;

    public SquareAdapter(List<SquareItem> squareItems) {
        this.squareItems = squareItems;
    }

    @NonNull
    @Override
    public SquareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_square, parent, false);
        return new SquareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SquareViewHolder holder, int position) {
        SquareItem item = squareItems.get(position);
        holder.squareIcon.setImageResource(item.getIconResId());
        holder.squareText.setText(item.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return squareItems.size();
    }

    static class SquareViewHolder extends RecyclerView.ViewHolder {
        ImageView squareIcon;
        TextView squareText;

        public SquareViewHolder(@NonNull View itemView) {
            super(itemView);
            squareIcon = itemView.findViewById(R.id.square_icon);
            squareText = itemView.findViewById(R.id.square_text);
        }
    }
}
package lb.edu.ul.bikhedemtak;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.PromoViewHolder> {
    private List<Promo> promoList;
    private int selectedPosition = -1;
    private OnPromoSelectedListener listener;

    public interface OnPromoSelectedListener {
        void onPromoSelected(String promoCode);
    }

    public PromoAdapter(List<Promo> promoList, OnPromoSelectedListener listener) {
        this.promoList = promoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo, parent, false);
        return new PromoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        Promo promo = promoList.get(position);
        holder.radioPromo.setText(promo.getCode());
        holder.promoDescription.setText(promo.getDescription());
        holder.radioPromo.setChecked(position == selectedPosition);

        holder.radioPromo.setOnClickListener(v -> {
            selectedPosition = position;
            listener.onPromoSelected(promo.getCode());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }

    static class PromoViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioPromo;
        TextView promoDescription;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            radioPromo = itemView.findViewById(R.id.radioPromo);
            promoDescription = itemView.findViewById(R.id.promoDescription);
        }
    }
}

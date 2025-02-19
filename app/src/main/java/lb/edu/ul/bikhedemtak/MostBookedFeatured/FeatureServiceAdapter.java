package lb.edu.ul.bikhedemtak.MostBookedFeatured;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;

public class FeatureServiceAdapter extends RecyclerView.Adapter<FeatureServiceAdapter.FeatureServiceViewHolder> {

    private List<FeatureService> featureServices;

    public FeatureServiceAdapter(List<FeatureService> featureServices) {
        this.featureServices = featureServices;
    }

    @NonNull
    @Override
    public FeatureServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feature_service, parent, false);
        return new FeatureServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureServiceViewHolder holder, int position) {
        FeatureService service = featureServices.get(position);
        holder.serviceName.setText(service.getName());
    }

    @Override
    public int getItemCount() {
        return featureServices.size();
    }

    static class FeatureServiceViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName;

        public FeatureServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.service_name);
        }
    }
}

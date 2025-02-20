package lb.edu.ul.bikhedemtak.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.models.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> serviceList;

    public ServiceAdapter(List<Service> serviceList) {
        this.serviceList = serviceList != null ? serviceList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        Log.d("RecyclerView", "Binding service: " + service.getServiceName());

        // Set service name correctly
        holder.textViewServiceName.setText(service.getServiceName());

        // Set the same image for all items (default image)
        holder.imageViewServiceIcon.setImageResource(R.drawable.job_icon); // Default image resource
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewServiceIcon;
        TextView textViewServiceName;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewServiceIcon = itemView.findViewById(R.id.imageViewServiceIcon);
            textViewServiceName = itemView.findViewById(R.id.textViewServiceName);
        }
    }
}
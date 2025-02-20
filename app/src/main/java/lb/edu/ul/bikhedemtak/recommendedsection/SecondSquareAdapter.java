package lb.edu.ul.bikhedemtak.recommendedsection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.recommendedsection.SecondSquareItem;

public class SecondSquareAdapter extends RecyclerView.Adapter<SecondSquareAdapter.SecondSquareViewHolder> {

    private List<SecondSquareItem> secondSquareItems;

    public SecondSquareAdapter(List<SecondSquareItem> secondSquareItems) {
        this.secondSquareItems = secondSquareItems;
    }

    @NonNull
    @Override
    public SecondSquareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second_square, parent, false);
        return new SecondSquareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondSquareViewHolder holder, int position) {
        SecondSquareItem item = secondSquareItems.get(position);

        // Load profile picture using Glide
        Glide.with(holder.itemView.getContext())
                .load(item.getProfilePictureUrl())
                .placeholder(R.drawable.profile_icon) // Default icon if the URL is invalid
                .into(holder.profileIcon);

        holder.name.setText(item.getName());
        holder.rate.setText("$" + item.getHourlyRate() + "/hr"); // Format hourly rate
        holder.rating.setText(String.valueOf(item.getRating())); // Display rating
        holder.waitingJobs.setText(item.getWaitingJobs()); // Display availability status
    }

    @Override
    public int getItemCount() {
        return secondSquareItems.size();
    }

    // Method to update the data in the adapter
    public void updateData(List<SecondSquareItem> newItems) {
        secondSquareItems.clear();
        secondSquareItems.addAll(newItems);
        notifyDataSetChanged(); // Notify the adapter that data has changed
    }

    static class SecondSquareViewHolder extends RecyclerView.ViewHolder {
        ImageView profileIcon;
        TextView name, rate, rating, waitingJobs;

        public SecondSquareViewHolder(@NonNull View itemView) {
            super(itemView);
            profileIcon = itemView.findViewById(R.id.profile_icon);
            name = itemView.findViewById(R.id.name);
            rate = itemView.findViewById(R.id.rate);
            rating = itemView.findViewById(R.id.rating);
            waitingJobs = itemView.findViewById(R.id.waiting_jobs);
        }
    }
}
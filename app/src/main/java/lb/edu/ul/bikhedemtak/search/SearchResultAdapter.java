package lb.edu.ul.bikhedemtak.search;

import android.content.Context;
import android.content.Intent;
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
import lb.edu.ul.bikhedemtak.activities.TaskerProfileActivity;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<SearchResult> searchResultList;
    private Context context;

    public SearchResultAdapter(List<SearchResult> searchResultList, Context context) {
        this.searchResultList = searchResultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tasker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchResult searchResult = searchResultList.get(position);


        // Load profile picture using Glide or Picasso
        Glide.with(holder.itemView.getContext())
                .load(searchResult.getProfilePicture())
                .placeholder(R.drawable.profile_icon) // Default icon if the URL is invalid
                .into(holder.profileIconImageView);

        // Bind data to views
        holder.nameTextView.setText(searchResult.getName());
        holder.rateTextView.setText("$" + searchResult.getHourlyRate() + "/hr");
        holder.ratingTextView.setText("★ " + searchResult.getRating());
        holder.waitingJobsTextView.setText(searchResult.getWaitingJobs()); // Display availability status
        holder.descriptionTextView.setText(searchResult.getDescription());

        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the profile activity
                Intent intent = new Intent(context, TaskerProfileActivity.class);

                // Pass the tasker_id to the profile activity
                intent.putExtra("tasker_id", searchResult.getTaskerId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileIconImageView;
        TextView nameTextView, rateTextView, ratingTextView, waitingJobsTextView, descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileIconImageView = itemView.findViewById(R.id.profile_icon);
            nameTextView = itemView.findViewById(R.id.name);
            rateTextView = itemView.findViewById(R.id.rate);
            ratingTextView = itemView.findViewById(R.id.rating);
            waitingJobsTextView = itemView.findViewById(R.id.waiting_jobs);
            descriptionTextView = itemView.findViewById(R.id.description);
        }
    }
}
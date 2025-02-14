package lb.edu.ul.bikhedemtak.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.models.Review;

/**
 * Adapter class for displaying reviews in a RecyclerView.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    // List of reviews to be displayed
    private List<Review> reviewList;

    /**
     * Constructor for ReviewAdapter.
     *
     * @param reviewList List of reviews to be displayed.
     */
    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    /**
     *
     */
    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView reviewerName;
        TextView reviewTime;
        TextView reviewContent;
        RatingBar ratingBar;

        /**
         * Constructor for ReviewViewHolder.
         *
         * @param itemView The view of the review item.
         */
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            reviewerName = itemView.findViewById(R.id.reviewerName);
            reviewTime = itemView.findViewById(R.id.reviewTime);
            reviewContent = itemView.findViewById(R.id.reviewContent);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ReviewViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_row, parent, false);
        return new ReviewViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.reviewerName.setText(review.getReviewerName());
        holder.reviewContent.setText(review.getReviewContent());
        holder.ratingBar.setRating(review.getRating());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.adapters.ReviewAdapter;
import lb.edu.ul.bikhedemtak.models.Review;

/**
 * Activity to display all reviews.
 */
public class ViewAllReviewsActivity extends AppCompatActivity {

    // RecyclerView to display the list of reviews
    RecyclerView allReviewsRecyclerView;
    // List to hold the reviews
    private List<Review> reviews;
    // Adapter for the RecyclerView
    ReviewAdapter reviewAdapter;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_reviews);

        // Set up the Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Enable the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Initialize the RecyclerView and set its layout manager
        allReviewsRecyclerView = findViewById(R.id.allReviewsRecyclerView);
        allReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get sample reviews and set the adapter
        reviews = getSampleReviews();
        reviewAdapter = new ReviewAdapter(reviews);
        allReviewsRecyclerView.setAdapter(reviewAdapter);
    }

    /**
     * Temporary method to create sample reviews. Replace with your actual data source.
     *
     * @return A list of sample reviews.
     */
    private List<Review> getSampleReviews() {
        List<Review> reviews = new ArrayList<>();

        // Add sample reviews
        reviews.add(new Review("John Doe", "Great service!", "2021-07-15", 4.5f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Jane Smith", "Very professional and friendly.", "2021-07-10", 5.0f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Alice Johnson", "Was not fast enough!", "2021-07-05", 2.5f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Bob Brown", "Good job!", "2021-07-01", 4.0f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Eve White", "Excellent work!", "2021-06-25", 5.0f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Charlie Green", "Very satisfied with the service.", "2021-06-20", 4.5f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Grace Black", "Not happy with the service.", "2021-06-15", 2.0f, "https://via.placeholder.com/150"));

        // Add more sample reviews as needed

        return reviews;
    }
}
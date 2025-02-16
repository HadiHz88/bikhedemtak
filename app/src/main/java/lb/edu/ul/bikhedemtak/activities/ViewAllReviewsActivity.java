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
    // Adapter for the RecyclerView
    ReviewAdapter reviewAdapter;

    List<Review> reviews;

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

        reviews = (ArrayList<Review>) getIntent().getSerializableExtra("reviews_list");
        if (reviews == null) {
            reviews = new ArrayList<>(); // Fallback if no data
        }

        reviewAdapter = new ReviewAdapter(reviews);
        allReviewsRecyclerView.setAdapter(reviewAdapter);
    }

}
package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.adapters.ReviewAdapter;
import lb.edu.ul.bikhedemtak.models.Review;

public class ViewAllReviewsActivity extends AppCompatActivity {

    RecyclerView allReviewsRecyclerView;
    private List<Review> reviews;

    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_reviews);

        // Set up the Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("All Reviews");

        // Enable the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        allReviewsRecyclerView = findViewById(R.id.allReviewsRecyclerView);
        allReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviews = getSampleReviews();
        reviewAdapter = new ReviewAdapter(reviews);
        allReviewsRecyclerView.setAdapter(reviewAdapter);

    }
    // Temporary method to create sample reviews (replace with your actual data source)
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
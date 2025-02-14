package lb.edu.ul.bikhedemtak.activities;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.google.android.material.appbar.MaterialToolbar;
    import com.google.android.material.bottomsheet.BottomSheetBehavior;
    import java.util.ArrayList;
    import java.util.List;

    import lb.edu.ul.bikhedemtak.R;
    import lb.edu.ul.bikhedemtak.adapters.ReviewAdapter;
    import lb.edu.ul.bikhedemtak.models.Review;
    import lb.edu.ul.bikhedemtak.ui.DateTimeBottomSheet;

    /**
     * Activity to display the profile of a tasker.
     */
    public class TaskerProfileActivity extends AppCompatActivity {

        private BottomSheetBehavior<View> bottomSheetBehavior;
        private Button selectTasker, viewAllReviews;
        private RecyclerView reviewsRecyclerView;
        private ReviewAdapter reviewAdapter;
        private MaterialToolbar toolbar;

        /**
         * Called when the activity is first created.
         * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_tasker_profile);

            // Set up toolbar
            toolbar = findViewById(R.id.taskerProfileToolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Tasker Profile");

            // Initialize views
            selectTasker = findViewById(R.id.btn_select_tasker);
            reviewsRecyclerView = findViewById(R.id.rvRecentReviews);
            viewAllReviews = findViewById(R.id.btn_view_all_reviews);

            setupRecyclerView();

            // Set up button click listener
            selectTasker.setOnClickListener(v -> {
                DateTimeBottomSheet bottomSheet = new DateTimeBottomSheet();
                bottomSheet.setListener((date, time) -> {
                    // Handle date and time selection
                });
                bottomSheet.show(getSupportFragmentManager(), "dateTimePicker");
            });

            viewAllReviews.setOnClickListener(v -> {
                // Open reviews activity
                Intent intent = new Intent(this, ViewAllReviewsActivity.class);
                startActivity(intent);
            });
        }

        /**
         * Set up the RecyclerView with a layout manager and adapter.
         */
        private void setupRecyclerView() {
            // Set layout manager
            reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Create sample data (replace this with your actual data)
            List<Review> reviews = getSampleReviews();

            // Initialize and set adapter
            reviewAdapter = new ReviewAdapter(reviews.subList(0, 2));
            reviewsRecyclerView.setAdapter(reviewAdapter);
        }

        /**
         * Temporary method to create sample reviews (replace with your actual data source).
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
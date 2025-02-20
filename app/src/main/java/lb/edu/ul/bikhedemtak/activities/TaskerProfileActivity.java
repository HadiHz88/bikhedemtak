package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.adapters.ReviewAdapter;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.models.Review;
import lb.edu.ul.bikhedemtak.ui.DateTimeBottomSheet;

/**
 * Activity to display the profile of a tasker.
 */
public class TaskerProfileActivity extends AppCompatActivity {

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private MaterialButton selectTasker, viewAllReviews;
    private RecyclerView reviewsRecyclerView;
    private ReviewAdapter reviewAdapter;
    private MaterialToolbar toolbar;

    private ImageView taskerProfilePicture;
    private TextView taskerName, taskerSkill, taskerRating, taskerAvailability, taskerDescription, taskerHourlyRate, taskerProjectsCount;
    private RatingBar taskerRatingBar;
    List<Review> allReviews = new ArrayList<>();
    boolean isDataLoaded = false;

    private int tasker_id;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasker_profile);

        Intent intent = getIntent();
        if(intent == null) {
            Log.d("TaskerProfileActivity", "Intent is null");
            return;
        }
        tasker_id = intent.getIntExtra("tasker_id", 2);

        initializeViews();
        setupToolbar();
        fetchTaskerDetails();
        setupRecyclerView();

        // Set up button click listeners
        selectTasker.setOnClickListener(v -> openDateTimePicker());
        viewAllReviews.setOnClickListener(v -> openViewAllReviewsActivity());

        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchTaskerDetails();
            setupRecyclerView();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    /**
     * Initialize views.
     */
    private void initializeViews() {
        toolbar = findViewById(R.id.taskerProfileToolbar);
        selectTasker = findViewById(R.id.btn_select_tasker);
        reviewsRecyclerView = findViewById(R.id.rvRecentReviews);
        viewAllReviews = findViewById(R.id.btn_view_all_reviews);
        taskerName = findViewById(R.id.tvTaskerName);
        taskerSkill = findViewById(R.id.tvTaskerSkill);
        taskerRating = findViewById(R.id.tvTaskerRating);
        taskerRatingBar = findViewById(R.id.rbTaskerRating);
        taskerDescription = findViewById(R.id.tvTaskerDescription);
        taskerProfilePicture = findViewById(R.id.ivTaskerProfilePic);
        taskerHourlyRate = findViewById(R.id.tvTaskerHourlyRate);
        taskerProjectsCount = findViewById(R.id.tvTaskerProjects);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    /**
     * Set up the toolbar.
     */
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tasker Profile");
        }
    }

    /**
     * Fetch tasker details from the API.
     */
    private void fetchTaskerDetails() {
        String taskerDetailsEndpoint = "getTaskerDetails.php?tasker_id=" + tasker_id;

        ApiRequest.getInstance().makeGetObjectRequest(this, taskerDetailsEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
//                Log.d("TaskerProfileActivity", "Response: " + response);
                try {
                    JSONObject data = response.getJSONObject("data");

                    allReviews = new ArrayList<>();
                    String name = data.getString("name");
                    String profilePicture = data.optString("profile_picture", "");
                    String skill = data.getString("skill");
                    String availabilityStatus = data.getBoolean("availability_status") ? "Available" : "Unavailable";
                    String rating = String.valueOf(data.getInt("rating"));
                    String description = data.getString("description");
                    String hourlyRate = String.valueOf(data.getInt("hourly_rate"));
                    String projectsCount = data.optString("completed_tasks_count", "0");

                    updateTaskerDetails(name, profilePicture, skill, availabilityStatus, rating, description, hourlyRate, projectsCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("TaskerProfileActivity", "Error: " + error);
            }
        });
    }

    /**
     * Update the tasker details in the UI.
     */
    private void updateTaskerDetails(String name, String profilePicture, String skill, String availabilityStatus, String rating, String description, String hourlyRate, String projectsCount) {
        taskerName.setText(name);
        taskerSkill.setText(skill);
        taskerRating.setText(rating);
        taskerRatingBar.setRating(Float.parseFloat(rating));
        taskerDescription.setText(description);
        taskerHourlyRate.setText("$" + hourlyRate + "/hr");
        taskerProjectsCount.setText(projectsCount);

        if (profilePicture.isEmpty()) {
            Glide.with(this).load(R.drawable.default_pp).into(taskerProfilePicture);
        }
    }

    /**
     * Set up the RecyclerView with a layout manager and adapter.
     */
    private void setupRecyclerView() {
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initially set adapter with empty list to avoid no adapter attached error
        reviewAdapter = new ReviewAdapter(new ArrayList<>());
        reviewsRecyclerView.setAdapter(reviewAdapter);

        String reviewsEndpoint = "getTaskerReviews.php?tasker_id=" + tasker_id; // Temporary tasker ID

        ApiRequest.getInstance().makeGetObjectRequest(this, reviewsEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        JSONArray reviewsArray = response.getJSONArray("reviews");

                        allReviews = new ArrayList<>();
                        for (int i = 0; i < reviewsArray.length(); i++) {
                            JSONObject reviewObject = reviewsArray.getJSONObject(i);
                            String reviewerName = reviewObject.getString("reviewer_name");
                            String reviewContent = reviewObject.getString("review_content");
                            String reviewDate = reviewObject.getString("created_at");
                            float rating = (float) reviewObject.getDouble("rating");
                            String profilePicture = reviewObject.optString("reviewer_profile_picture", "");

                            allReviews.add(new Review(reviewerName, reviewContent, reviewDate, rating, profilePicture));
                        }

                        // Limit the reviews to 4
                        List<Review> limitedReviews = allReviews.size() > 4 ? allReviews.subList(0, 4) : allReviews;

                        // Update the adapter with limited reviews
                        reviewAdapter.updateReviews(limitedReviews);
                    } else {
                        Log.d("TaskerProfileActivity", "No reviews found");
                        // Optionally handle empty reviews (e.g., show a message)
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("TaskerProfileActivity", "Error: " + error);
                // Fallback to sample reviews in case of failure
                reviewAdapter.updateReviews(getSampleReviews());
            }
        });
    }



    /**
     * Temporary method to create sample reviews (replace with actual data source).
     */
    private List<Review> getSampleReviews() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("John Doe", "Great service!", "2021-07-15", 4.5f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Jane Smith", "Very professional and friendly.", "2021-07-10", 5.0f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Alice Johnson", "Was not fast enough!", "2021-07-05", 2.5f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Bob Brown", "Good job!", "2021-07-01", 4.0f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Eve White", "Excellent work!", "2021-06-25", 5.0f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Charlie Green", "Very satisfied with the service.", "2021-06-20", 4.5f, "https://via.placeholder.com/150"));
        reviews.add(new Review("Grace Black", "Not happy with the service.", "2021-06-15", 2.0f, "https://via.placeholder.com/150"));
        return reviews;
    }

    /**
     * Open the date and time picker bottom sheet.
     */
    private void openDateTimePicker() {
        Bundle bundle = new Bundle();
        bundle.putInt("tasker_id", 1); // Temporary tasker ID

        DateTimeBottomSheet bottomSheet = new DateTimeBottomSheet();
        bottomSheet.setArguments(bundle);
        bottomSheet.show(getSupportFragmentManager(), "dateTimePicker");
    }


    /**
     * Open the "View All Reviews" activity.
     */
    private void openViewAllReviewsActivity() {
        Intent intent = new Intent(TaskerProfileActivity.this, ViewAllReviewsActivity.class);
        intent.putExtra("reviews_list", (ArrayList<Review>) allReviews);
        startActivity(intent);
    }
}

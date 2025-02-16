package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

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
    private Button selectTasker, viewAllReviews;
    private RecyclerView reviewsRecyclerView;
    private ReviewAdapter reviewAdapter;
    private MaterialToolbar toolbar;

    private ImageView taskerProfilePicture;
    private TextView taskerName, taskerSkill, taskerRating, taskerAvailability, taskerDescription;
    private RatingBar taskerRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasker_profile);

        initializeViews();
        setupToolbar();
        fetchTaskerDetails();
        setupRecyclerView();

        // Set up button click listeners
        selectTasker.setOnClickListener(v -> openDateTimePicker());
        viewAllReviews.setOnClickListener(v -> openViewAllReviewsActivity());
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
        int taskerId = 1; // Temporary tasker ID
        String endpoint = "getTaskerDetails.php?tasker_id=" + taskerId;

        ApiRequest.getInstance().makeGetObjectRequest(this, endpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
//                Log.d("TaskerProfileActivity", "Response: " + response);
                try {
                    JSONObject data = response.getJSONObject("data");

                    String name = data.getString("name");
                    String profilePicture = data.optString("profile_picture", "");
                    String skill = data.getString("skill");
                    String availabilityStatus = data.getBoolean("availability_status") ? "Available" : "Unavailable";
                    String rating = String.valueOf(data.getInt("rating"));
                    String description = data.getString("description");

                    updateTaskerDetails(name, profilePicture, skill, availabilityStatus, rating, description);
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
    private void updateTaskerDetails(String name, String profilePicture, String skill, String availabilityStatus, String rating, String description) {
        taskerName.setText(name);
        taskerSkill.setText(skill);
        taskerRating.setText(rating);
        taskerRatingBar.setRating(Float.parseFloat(rating));
        taskerDescription.setText(description);

        if (profilePicture.isEmpty()) {
            Glide.with(this).load(R.drawable.default_pp).into(taskerProfilePicture);
        }
    }

    /**
     * Set up the RecyclerView with a layout manager and adapter.
     */
    private void setupRecyclerView() {
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new ReviewAdapter(getSampleReviews().subList(0, 2));
        reviewsRecyclerView.setAdapter(reviewAdapter);
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
        DateTimeBottomSheet bottomSheet = new DateTimeBottomSheet();
        bottomSheet.setListener((date, time) -> {
            // Handle date and time selection
        });
        bottomSheet.show(getSupportFragmentManager(), "dateTimePicker");
    }

    /**
     * Open the "View All Reviews" activity.
     */
    private void openViewAllReviewsActivity() {
        Intent intent = new Intent(this, ViewAllReviewsActivity.class);
        startActivity(intent);
    }
}

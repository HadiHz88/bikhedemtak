package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.adapters.ReviewAdapter;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.models.Review;

/**
 * Activity to display all reviews.
 */
public class ViewAllReviewsActivity extends AppCompatActivity {

    // RecyclerView to display the list of reviews
    RecyclerView allReviewsRecyclerView;
    // Adapter for the RecyclerView
    ReviewAdapter reviewAdapter;

    List<Review> reviews = new ArrayList<>();
    int tasker_id;


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_reviews);

        tasker_id = getIntent().getIntExtra("tasker_id", 2);

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

        String reviewsEndpoint = "getTaskerReviews.php?tasker_id=" + tasker_id; // Temporary tasker ID

        // Initialize the adapter with an empty list and set it to the RecyclerView
        reviewAdapter = new ReviewAdapter(new ArrayList<>());
        allReviewsRecyclerView.setAdapter(reviewAdapter);

        ApiRequest.getInstance().makeGetObjectRequest(this, reviewsEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        JSONArray reviewsArray = response.getJSONArray("reviews");
                        reviews.clear(); // Clear any old data before adding new data

                        for (int i = 0; i < reviewsArray.length(); i++) {
                            JSONObject reviewObject = reviewsArray.getJSONObject(i);
                            String reviewerName = reviewObject.getString("reviewer_name");
                            String reviewContent = reviewObject.getString("review_content");
                            String reviewDate = reviewObject.getString("created_at");
                            float rating = (float) reviewObject.getDouble("rating");
                            String profilePicture = reviewObject.optString("reviewer_profile_picture", "");

                            reviews.add(new Review(reviewerName, reviewContent, reviewDate, rating, profilePicture));
                        }

                        if (reviews.size() > 0) {
                            // Update reviews and show RecyclerView
                            reviewAdapter.updateReviews(reviews);
                            allReviewsRecyclerView.setVisibility(View.VISIBLE);
                            findViewById(R.id.noReviewsCard).setVisibility(View.GONE);
                        } else {
                            // Show the no reviews card
                            findViewById(R.id.noReviewsCard).setVisibility(View.VISIBLE);
                            allReviewsRecyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d("TaskerProfileActivity", "No reviews found");
                        findViewById(R.id.noReviewsCard).setVisibility(View.VISIBLE);
                        allReviewsRecyclerView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("TaskerProfileActivity", "Error: " + error);
                findViewById(R.id.noReviewsCard).setVisibility(View.VISIBLE);
                allReviewsRecyclerView.setVisibility(View.GONE);
            }
        });

    }

}
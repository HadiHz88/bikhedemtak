package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;


public class FeedbackActivity extends AppCompatActivity {

    // Declare views
    private ImageView taskerImg;
    private TextView rateTaskerTv;
    private RatingBar taskerRatingBar;
    private Button friendlyButton, supportiveButton, superTaskerButton, fastWorkerButton;
    private EditText leaveFeedbackEd;
    private Button submitFeedbackButton;

    // Variables to store selected buttons
    private boolean isFriendlySelected = false;
    private boolean isSupportiveSelected = false;
    private boolean isSuperTaskerSelected = false;
    private boolean isFastWorkerSelected = false;



    private String tasker_name;
    private int tasker_id, task_id;
    private int reviewer_id;

    // Volley RequestQueue
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback); // Replace with your layout file name

        // Initialize views
        taskerImg = findViewById(R.id.taskerImg);
        rateTaskerTv = findViewById(R.id.rateTaskerTv);
        taskerRatingBar = findViewById(R.id.taskerRatingBar);
        friendlyButton = findViewById(R.id.friendlyButton);
        supportiveButton = findViewById(R.id.supportiveButton);
        superTaskerButton = findViewById(R.id.superTaskerButton);
        fastWorkerButton = findViewById(R.id.fastWorkerButton);
        leaveFeedbackEd = findViewById(R.id.leaveFeedbackEd);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);

//        reviewer_id = SharedPrefsManager.getUserId(this);
        reviewer_id = 2; // Replace with the actual reviewer ID
        // Retrieve the tasker name from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            tasker_name = intent.getStringExtra("tasker_name");
            tasker_id = intent.getIntExtra("tasker_id", -1);
            task_id = intent.getIntExtra("task_id", -1);
            rateTaskerTv.setText("Rate " + tasker_name); // Set the tasker name in the TextView
        }
        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Set RatingBar color to yellow/mustard
        taskerRatingBar.setProgressTintList(getResources().getColorStateList(R.color.mustard_yellow));

        // Handle button clicks (Friendly, Supportive, etc.)
        friendlyButton.setOnClickListener(v -> toggleButtonColor(friendlyButton, "friendly"));
        supportiveButton.setOnClickListener(v -> toggleButtonColor(supportiveButton, "supportive"));
        superTaskerButton.setOnClickListener(v -> toggleButtonColor(superTaskerButton, "superTasker"));
        fastWorkerButton.setOnClickListener(v -> toggleButtonColor(fastWorkerButton, "fastWorker"));

        // Handle Submit Button click
        submitFeedbackButton.setOnClickListener(v -> submitFeedback());
    }

    // Method to toggle button color and selection state
    private void toggleButtonColor(Button button, String buttonType) {
        switch (buttonType) {
            case "friendly":
                isFriendlySelected = !isFriendlySelected;
                button.setBackgroundColor(isFriendlySelected ? Color.parseColor("#CFA8E8") : Color.parseColor("#00000000"));
                break;
            case "supportive":
                isSupportiveSelected = !isSupportiveSelected;
                button.setBackgroundColor(isSupportiveSelected ? Color.parseColor("#CFA8E8") : Color.parseColor("#00000000"));
                break;
            case "superTasker":
                isSuperTaskerSelected = !isSuperTaskerSelected;
                button.setBackgroundColor(isSuperTaskerSelected ? Color.parseColor("#CFA8E8") : Color.parseColor("#00000000"));
                break;
            case "fastWorker":
                isFastWorkerSelected = !isFastWorkerSelected;
                button.setBackgroundColor(isFastWorkerSelected ? Color.parseColor("#CFA8E8") : Color.parseColor("#00000000"));
                break;
        }
    }

    // Method to submit feedback
    private void submitFeedback() {
        // Get the rating
        float rating = taskerRatingBar.getRating();

        // Get the feedback text
        String feedback = leaveFeedbackEd.getText().toString().trim() +
                ". He was " + (isFriendlySelected ? "friendly, " : "") +
                (isSupportiveSelected ? "supportive, " : "") +
                (isSuperTaskerSelected ? "a super tasker, " : "") +
                (isFastWorkerSelected ? "a fast worker, " : "");

        // Remove the last comma and space
        feedback = feedback.substring(0, feedback.length() - 2);

        String postReviewEndpoint = "postReview.php";

        JSONObject reviewParams = new JSONObject();
        try {
            reviewParams.put("task_id", task_id);
            reviewParams.put("reviewer_id", reviewer_id); // Replace with the actual reviewer ID
            reviewParams.put("tasker_id", tasker_id);
            reviewParams.put("rating", rating);
            reviewParams.put("review_content", feedback);

            Log.d("test", "task_id: " + task_id);
            Log.d("test", "reviewer_id: " + reviewer_id);
            Log.d("test", "tasker_id: " + tasker_id);
            Log.d("test", "rating: " + rating);
            Log.d("test", "review_content: " + feedback);

            // Make a POST request to submit the feedback
            ApiRequest.getInstance().makePostRequest(this, postReviewEndpoint, reviewParams, new ApiRequest.ResponseListener<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        if (response.getString("status").equals("success")) {
                            Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(FeedbackActivity.this, "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                            resetForm();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String error) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    // Method to reset the form after submission
    private void resetForm() {
        // Reset RatingBar
        taskerRatingBar.setRating(0);

        // Reset buttons
        friendlyButton.setBackgroundColor(Color.parseColor("#00000000"));
        supportiveButton.setBackgroundColor(Color.parseColor("#00000000"));
        superTaskerButton.setBackgroundColor(Color.parseColor("#00000000"));
        fastWorkerButton.setBackgroundColor(Color.parseColor("#00000000"));
        isFriendlySelected = false;
        isSupportiveSelected = false;
        isSuperTaskerSelected = false;
        isFastWorkerSelected = false;

        // Clear feedback EditText
        leaveFeedbackEd.setText("");
    }
}
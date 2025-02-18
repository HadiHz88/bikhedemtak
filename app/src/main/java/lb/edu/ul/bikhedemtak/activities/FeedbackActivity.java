package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

        // Retrieve the tasker name from the Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("taskerName")) {
            String taskerName = intent.getStringExtra("taskerName");
            rateTaskerTv.setText("Rate " + taskerName); // Set the tasker name in the TextView
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
        String feedback = leaveFeedbackEd.getText().toString().trim();

        // Prepare selected buttons data
        JSONObject selectedButtons = new JSONObject();
        try {
            selectedButtons.put("friendly", isFriendlySelected);
            selectedButtons.put("supportive", isSupportiveSelected);
            selectedButtons.put("superTasker", isSuperTaskerSelected);
            selectedButtons.put("fastWorker", isFastWorkerSelected);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JSON object to send to the server
        JSONObject feedbackData = new JSONObject();
        try {
            feedbackData.put("rating", rating);
            feedbackData.put("feedback", feedback);
            feedbackData.put("selectedButtons", selectedButtons);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Replace with the actual API URL
        String url = "https://your-api-url.com/submit-feedback";

        // Send feedback data to the server using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, feedbackData,
                response -> {
                    // Handle successful submission
                    Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                    resetForm();
                },
                error -> {
                    // Handle error
                    Toast.makeText(FeedbackActivity.this, "Failed to submit feedback. Please try again.", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
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
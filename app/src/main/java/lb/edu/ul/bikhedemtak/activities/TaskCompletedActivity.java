package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

import lb.edu.ul.bikhedemtak.R;


public class TaskCompletedActivity extends AppCompatActivity {

    // Declare TextViews, ImageView, and Button
    private TextView taskerNameTv, statusInfoTv, bookingDateTv, bookingTimeTv, hourlyPriceTv, supportFeePriceTv, totalRatePriceTv, locationTaskerTv, taskerNotesTv;
    private ImageView taskerProfilePic;
    private Button rateAndTipsButton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_completed); // Replace with your layout file name

        // Initialize TextViews
        taskerNameTv = findViewById(R.id.taskerNameTv);
        statusInfoTv = findViewById(R.id.statusInfoTv);
        bookingDateTv = findViewById(R.id.bookingDateTv);
        bookingTimeTv = findViewById(R.id.bookingTimeTv);
        hourlyPriceTv = findViewById(R.id.hourlyPriceTv);
        supportFeePriceTv = findViewById(R.id.supportFeePriceTv);
        totalRatePriceTv = findViewById(R.id.totalRatePriceTv);
        locationTaskerTv = findViewById(R.id.locationTaskerTv);
        taskerNotesTv = findViewById(R.id.taskerNotesTv);

        // Initialize ImageView
        taskerProfilePic = findViewById(R.id.taskerProfilePic);

        // Initialize Button
        rateAndTipsButton = findViewById(R.id.rateAndTipsButton);

        // Set click listener for the "Rate and Tips" button
        rateAndTipsButton.setOnClickListener(v -> {
            // Navigate to the FeedbackActivity
            Intent intent = new Intent(TaskCompletedActivity.this, FeedbackActivity.class);
            intent.putExtra("taskerName", taskerNameTv.getText().toString()); // Pass the tasker name
            startActivity(intent);
        });

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Fetch data from the database
        fetchTaskCompletedInfo();
    }

    private void fetchTaskCompletedInfo() {
        // Replace with your actual API URL
        String url = "https://your-api-url.com/task-completed-info";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Parse the JSON response and update the TextViews
                        taskerNameTv.setText(response.getString("taskerName"));
                        statusInfoTv.setText(response.getString("taskerStatus"));
                        bookingDateTv.setText(response.getString("bookingDate"));
                        bookingTimeTv.setText(response.getString("bookingTime"));
                        hourlyPriceTv.setText(response.getString("hourlyPrice"));
                        supportFeePriceTv.setText(response.getString("supportFee"));
                        totalRatePriceTv.setText(response.getString("totalRate"));
                        locationTaskerTv.setText(response.getString("location"));
                        taskerNotesTv.setText(response.getString("taskNotes"));

                        // Load the profile picture using a library like Glide or Picasso
                        // Example with Glide:
                        // Glide.with(this).load(response.getString("profilePictureUrl")).into(taskerProfilePic);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle the error
                    error.printStackTrace();
                    Toast.makeText(TaskCompletedActivity.this, "Failed to fetch task details", Toast.LENGTH_SHORT).show();
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
}
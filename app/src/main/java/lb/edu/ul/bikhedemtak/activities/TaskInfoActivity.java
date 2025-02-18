package lb.edu.ul.bikhedemtak.activities;

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


public class TaskInfoActivity extends AppCompatActivity {

    private TextView taskerNameBookedTv, taskerStatusInfoTv, dateBookingTv, timeBookingTv, pricePerHourTv, supportFeeTv, totalRateTv, locationTv, taskNotesTv;
    private ImageView taskerProfilePicture;
    private Button findNewTaskerButton, cancelTaskButton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        // Initialize TextViews
        taskerNameBookedTv = findViewById(R.id.taskerNameBookedTv);
        taskerStatusInfoTv = findViewById(R.id.taskerStatusInfoTv);
        dateBookingTv = findViewById(R.id.dateBookingTv);
        timeBookingTv = findViewById(R.id.timeBookingTv);
        pricePerHourTv = findViewById(R.id.pricePerHourTv);
        supportFeeTv = findViewById(R.id.supportFeeTv);
        totalRateTv = findViewById(R.id.totalRateTv);
        locationTv = findViewById(R.id.locationTv);
        taskNotesTv = findViewById(R.id.taskNotesTv);

        // Initialize ImageView
        taskerProfilePicture = findViewById(R.id.TaskerProfilePicture);

        // Initialize Buttons
        findNewTaskerButton = findViewById(R.id.findNewTaskerButton);
        cancelTaskButton = findViewById(R.id.cancelTaskButton);

        // Set click listeners for buttons
        findNewTaskerButton.setOnClickListener(v -> {
            // Handle "Find A New Tasker" button click
            Toast.makeText(TaskInfoActivity.this, "Find A New Tasker Clicked", Toast.LENGTH_SHORT).show();
            // Add your logic here, e.g., navigate to a new activity or fetch a new tasker
        });

        cancelTaskButton.setOnClickListener(v -> {
            // Handle "Cancel Task" button click
            Toast.makeText(TaskInfoActivity.this, "Cancel Task Clicked", Toast.LENGTH_SHORT).show();
            // Add your logic here, e.g., cancel the task and update the database
        });

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Fetch data from the database
        fetchTaskInfo();
    }

    private void fetchTaskInfo() {
        // Replace with the actual API URL
        String url = "https://your-api-url.com/task-info";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Parse the JSON response and update the TextViews
                        taskerNameBookedTv.setText(response.getString("taskerName"));
                        taskerStatusInfoTv.setText(response.getString("taskerStatus"));
                        dateBookingTv.setText(response.getString("bookingDate"));
                        timeBookingTv.setText(response.getString("bookingTime"));
                        pricePerHourTv.setText(response.getString("pricePerHour"));
                        supportFeeTv.setText(response.getString("supportFee"));
                        totalRateTv.setText(response.getString("totalRate"));
                        locationTv.setText(response.getString("location"));
                        taskNotesTv.setText(response.getString("taskNotes"));

                        // You can also load the profile picture using a library like Glide or Picasso
                        // Glide.with(this).load(response.getString("profilePictureUrl")).into(taskerProfilePicture);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle the error
                    error.printStackTrace();
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
}

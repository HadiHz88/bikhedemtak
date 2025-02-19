package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;


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

        int task_id = 1; // sample

        // int task_id = Integer.parseInt(String.valueOf(getIntent().getIntExtra("tasker_id", 0)));

        String getTaskInfoEndpoint = "getTaskInfo.php?task_id=" + task_id;

        ApiRequest.getInstance().makeGetObjectRequest(this, getTaskInfoEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getString("status").equals("success")) {
                        JSONObject taskInfo = response.getJSONObject("data");

                        String taskerName = taskInfo.getString("tasker_name");
                        boolean taskerStatus = taskInfo.getBoolean("tasker_availability_status");
                        String tasker_picture = taskInfo.getString("tasker_profile_picture");
                        String dateTimeBooking = taskInfo.getString("booking_time");
                        String [] parts = dateTimeBooking.split(" ");
                        String dateBooking = parts[0];
                        String timeBooking = parts[1];
                        String TaskPricePerHour = taskInfo.getString("tasker_rate");
                        String taskDescription = taskInfo.getString("task_description");

                        taskerNameBookedTv.setText(taskerName);
                        taskerStatusInfoTv.setText(taskerStatus ? "Available" : "Not Available");
                        dateBookingTv.setText(dateBooking);
                        timeBookingTv.setText(timeBooking);
                        pricePerHourTv.setText(TaskPricePerHour);
                        taskNotesTv.setText(taskDescription);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                // Handle error
                Toast.makeText(TaskInfoActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        // Set click listeners for buttons
        findNewTaskerButton.setOnClickListener(v -> {
            // Handle "Find A New Tasker" button click
            Toast.makeText(TaskInfoActivity.this, "Find A New Tasker Clicked", Toast.LENGTH_SHORT).show();
            // Add your logic here, e.g., navigate to a new activity or fetch a new tasker
            // startActivity(new Intent(TaskInfoActivity.this, SearchTasker.class));
        });

        cancelTaskButton.setOnClickListener(v -> {
            // Handle "Cancel Task" button click
            // Toast.makeText(TaskInfoActivity.this, "Cancel Task Clicked", Toast.LENGTH_SHORT).show();
            // Intent cancelTaskIntent = new Intent(TaskInfoActivity.this, TaskerProfileActivity.class);
            // cancelTaskIntent.putExtra("tasker_id", );
        });
    }

}

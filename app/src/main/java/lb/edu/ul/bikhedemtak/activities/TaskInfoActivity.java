package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;


public class TaskInfoActivity extends AppCompatActivity {

    private MaterialTextView taskerNameBookedTv, taskerStatusInfoTv, dateBookingTv, timeBookingTv, pricePerHourTv, supportFeeTv, totalRateTv, taskNotesTv;
    private ImageView taskerProfilePicture;
    private MaterialButton findNewTaskerButton;
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
        taskNotesTv = findViewById(R.id.taskNotesTv);

        // Initialize ImageView
        taskerProfilePicture = findViewById(R.id.TaskerProfilePicture);

        // Initialize Buttons
        findNewTaskerButton = findViewById(R.id.findNewTaskerButton);

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
                        String[] parts = dateTimeBooking.split(" ");
                        String dateBooking = parts[0];
                        String timeBooking = parts[1];
                        int TaskPricePerHour = taskInfo.getInt("tasker_rate");
                        String taskDescription = taskInfo.getString("task_description");

                        taskerNameBookedTv.setText(taskerName);
                        taskerStatusInfoTv.setText(taskerStatus ? "Available" : "Not Available");
                        dateBookingTv.setText(dateBooking);
                        timeBookingTv.setText(timeBooking);
                        pricePerHourTv.setText("$" + TaskPricePerHour + "/hr");
                        taskNotesTv.setText(taskDescription);
                        totalRateTv.setText(String.valueOf(TaskPricePerHour + 3));

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
    }

}

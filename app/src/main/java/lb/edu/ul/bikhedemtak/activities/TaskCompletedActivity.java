package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;


public class TaskCompletedActivity extends AppCompatActivity {

    // Declare TextViews, ImageView, and Button
    private MaterialTextView taskerNameTv, statusInfoTv, bookingDateTv, bookingTimeTv, hourlyPriceTv, supportFeePriceTv, totalRatePriceTv, taskerNotesTv;
    private ImageView taskerProfilePic;
    private MaterialButton rateAndTipsButton;
    int tasker_id, task_id;
    String tasker_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_completed);

        task_id = getIntent().getIntExtra("task_id", 1);

        // Initialize TextViews
        taskerNameTv = findViewById(R.id.taskerNameTv);
        statusInfoTv = findViewById(R.id.statusInfoTv);
        bookingDateTv = findViewById(R.id.bookingDateTv);
        bookingTimeTv = findViewById(R.id.bookingTimeTv);
        hourlyPriceTv = findViewById(R.id.hourlyPriceTv);
        supportFeePriceTv = findViewById(R.id.supportFeePriceTv);
        totalRatePriceTv = findViewById(R.id.totalRatePriceTv);
        taskerNotesTv = findViewById(R.id.taskerNotesTv);

        // Initialize ImageView
        taskerProfilePic = findViewById(R.id.taskerProfilePic);

        // Initialize Buttons
        rateAndTipsButton = findViewById(R.id.rateAndTipsButton);

        rateAndTipsButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskCompletedActivity.this, FeedbackActivity.class);
            intent.putExtra("tasker_id", tasker_id);
            intent.putExtra("tasker_name", tasker_name);
            intent.putExtra("task_id", task_id);
            startActivity(intent);
        });

        int task_id = 1; // sample

        // int task_id = Integer.parseInt(String.valueOf(getIntent().getIntExtra("tasker_id", 0)));

        String getTaskInfoEndpoint = "getTaskInfo.php?task_id=" + task_id;

        ApiRequest.getInstance().makeGetObjectRequest(this, getTaskInfoEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getString("status").equals("success")) {
                        JSONObject taskInfo = response.getJSONObject("data");

                        tasker_name = taskInfo.getString("tasker_name");
                        tasker_id = taskInfo.getInt("tasker_id");
                        boolean taskerStatus = taskInfo.getBoolean("tasker_availability_status");
                        String tasker_picture = taskInfo.getString("tasker_profile_picture");
                        String dateTimeBooking = taskInfo.getString("booking_time");
                        String [] parts = dateTimeBooking.split(" ");
                        String dateBooking = parts[0];
                        String timeBooking = parts[1];
                        int TaskPricePerHour = taskInfo.getInt("tasker_rate");
                        String taskDescription = taskInfo.getString("task_description");

                        taskerNameTv.setText(tasker_name);
                        statusInfoTv.setText(taskerStatus ? "Available" : "Not Available");
                        bookingDateTv.setText(dateBooking);
                        bookingTimeTv.setText(timeBooking);
                        hourlyPriceTv.setText("$" + TaskPricePerHour + "/hr");
                        taskerNotesTv.setText(taskDescription);
                        totalRatePriceTv.setText(TaskPricePerHour + 3);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                // Handle error
                Toast.makeText(TaskCompletedActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
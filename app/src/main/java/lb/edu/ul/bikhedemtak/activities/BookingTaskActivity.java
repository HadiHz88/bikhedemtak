package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.api.ApiRequest;

public class BookingTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve intent data
        Intent intent = getIntent();
        if (intent != null) {
            int taskerId = intent.getIntExtra("tasker_id", -1);
            String bookingTime = intent.getStringExtra("booking_time");

            String[] parts = bookingTime != null ? bookingTime.split(" ") : null;
            String bookingDate = parts != null ? parts[0] : "";
            String bookingTimeOnly = parts != null ? parts[1] : "";

            // Fetch tasker details
            getTaskerDetails(taskerId, bookingDate, bookingTimeOnly);
        }
    }

    /**
     * Fetch tasker details from API.
     */
    private void getTaskerDetails(int taskerId, String bookingDate, String bookingTimeOnly) {
        String taskerDetailsEndpoint = "getTaskerDetails.php?tasker_id=" + taskerId;

        ApiRequest.getInstance().makeGetObjectRequest(this, taskerDetailsEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");

                    String name = data.getString("name");
                    String profilePicture = data.optString("profile_picture", "");
                    String hourlyRate = String.valueOf(data.getInt("hourly_rate"));

                    // Start TaskDetailsActivity with received data
                    startTaskDetailsActivity(taskerId, name, profilePicture, hourlyRate, bookingDate, bookingTimeOnly);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(BookingTaskActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Start TaskDetailsActivity with tasker details.
     */
    private void startTaskDetailsActivity(int taskerId, String name, String profilePicture, String hourlyRate, String bookingDate, String bookingTime) {
        Intent intent = new Intent(this, TaskDetailsActivity.class);
        intent.putExtra("tasker_id", taskerId);
        intent.putExtra("name", name);
        intent.putExtra("profile_picture", profilePicture);
        intent.putExtra("hourly_rate", hourlyRate);
        intent.putExtra("booking_date", bookingDate);
        intent.putExtra("booking_time", bookingTime);
        startActivity(intent);
    }
}

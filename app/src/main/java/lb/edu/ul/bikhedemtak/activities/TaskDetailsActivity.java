package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.databinding.ActivityTaskDetailsBinding;

public class TaskDetailsActivity extends AppCompatActivity {

    private ActivityTaskDetailsBinding binding;
    private String userInput = "";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back when the back button is clicked
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the Toolbar as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Task Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back button
        }

        // Retrieve intent data
        Intent intent = getIntent();
        if (intent != null) {
            int taskerId = intent.getIntExtra("tasker_id", -1);
            String bookingTime = intent.getStringExtra("booking_time");

            String[] parts = bookingTime != null ? bookingTime.split(" ") : null;
            String bookingDate = parts != null ? parts[0] : "";
            String bookingTimeOnly = parts != null ? parts[1] : "";

            // Fetch tasker details and set UI
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

                    // Set data to UI
                    binding.UserName.setText(name);
                    Glide.with(TaskDetailsActivity.this)
                            .load(profilePicture)
                            .placeholder(R.drawable.default_pp)
                            .into(binding.ProfileImage);

                    setupUserInputListener();
                    setupReviewButton(name, profilePicture, hourlyRate, bookingDate, bookingTimeOnly);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(TaskDetailsActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Set up text watcher for user input.
     */
    private void setupUserInputListener() {
        binding.ParagED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                userInput = s.toString();
            }
        });
    }

    /**
     * Set up button click listener to navigate to ReviewAndConfirmActivity.
     */
    private void setupReviewButton(String name, String profilePicture, String hourlyRate, String bookingDate, String bookingTimeOnly) {
        binding.ReviewTaskButton.setOnClickListener(v -> {
            startReviewAndConfirmActivity(name, profilePicture, hourlyRate, bookingDate, bookingTimeOnly, userInput);
        });
    }

    /**
     * Start ReviewAndConfirmActivity with collected data.
     */
    private void startReviewAndConfirmActivity(String name, String profilePicture, String hourlyRate, String bookingDate, String bookingTimeOnly, String userInput) {
        Intent intent = new Intent(this, ReviewAndConfirmActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("profile_picture", profilePicture);
        intent.putExtra("hourly_rate", hourlyRate);
        intent.putExtra("booking_date", bookingDate);
        intent.putExtra("booking_time", bookingTimeOnly);
        intent.putExtra("user_input", userInput);
        startActivity(intent);
    }
}

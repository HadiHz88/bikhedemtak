package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.databinding.ActivityBookingTaskBinding;
import lb.edu.ul.bikhedemtak.fragments.ReviewAndConfirmFragment;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;


public class BookingTaskActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBookingTaskBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityBookingTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_task_details);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Retrieve intent data
        Intent intent = getIntent();
        if (intent != null) {
            int taskerId = intent.getIntExtra("tasker_id", -1);
            String bookingDate = intent.getStringExtra("booking_date");
            String bookingTime = intent.getStringExtra("booking_time");


            // Call API to get tasker details
            getTaskerDetails(taskerId,bookingDate, bookingTime);
        }

    }

    /**
     * Fetch tasker details from API.
     */
    private void getTaskerDetails(int taskerId, String bookingDate, String bookingTime) {
        String taskerDetailsEndpoint = "getTaskerDetails.php?tasker_id=" + taskerId;

        ApiRequest.getInstance().makeGetObjectRequest(this, taskerDetailsEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");

                    String name = data.getString("name");
                    String profilePicture = data.optString("profile_picture", "");
                    String hourlyRate = String.valueOf(data.getInt("hourly_rate"));

                    // Pass data to the fragment
                    passDataToFragment(taskerId, name, profilePicture, hourlyRate, bookingDate, bookingTime);

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
     * Pass data to ReviewAndConfirmFragment.
     */
    private void passDataToFragment(int taskerId, String name, String profilePicture, String hourlyRate, String bookingTime) {
        ReviewAndConfirmFragment fragment = new ReviewAndConfirmFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tasker_id", taskerId);
        bundle.putString("name", name);
        bundle.putString("profile_picture", profilePicture);
        bundle.putString("hourly_rate", hourlyRate);
        bundle.putString("booking_time", bookingTime);
        fragment.setArguments(bundle);

        // Load the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_task_details, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_task_details);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

/**
 * Update the UI with tasker details.
 */
private void updateUI(int taskerID ,String name, String profilePicture, String hourlyRate, String bookingDate, String bookingTime) {
    // Set tasker's name
    TextView userNameTextView = findViewById(R.id.UserName);
    userNameTextView.setText(name);

    // Set hourly rate
    TextView hourlyRateTextView = findViewById(R.id.textView_below_image);
    hourlyRateTextView.setText("Hourly Rate: $" + hourlyRate);

    // Load profile picture using Glide
    ImageView profileImageView = findViewById(R.id.ProfileImage);
    Glide.with(this)
            .load(profilePicture)
            .placeholder(R.drawable.img)
            .into(profileImageView);

    // Pass data correctly to fragment
    passDataToFragment(taskerID, name, profilePicture, hourlyRate, bookingDate, bookingTime);
}


/**
 * Pass data to ReviewAndConfirmFragment.
 */
private void passDataToFragment(int taskerId, String name, String profilePicture, String hourlyRate, String bookingDate, String bookingTime) {
    ReviewAndConfirmFragment fragment = new ReviewAndConfirmFragment();
    Bundle bundle = new Bundle();
    bundle.putInt("tasker_id", taskerId);
    bundle.putString("name", name);
    bundle.putString("profile_picture", profilePicture);
    bundle.putString("hourly_rate", hourlyRate);
    bundle.putString("booking_date", bookingDate);
    bundle.putString("booking_time", bookingTime);
    fragment.setArguments(bundle);

    getSupportFragmentManager().beginTransaction()
            .replace(R.id.nav_host_fragment_task_details, fragment)
            .addToBackStack(null)
            .commit();
}

}


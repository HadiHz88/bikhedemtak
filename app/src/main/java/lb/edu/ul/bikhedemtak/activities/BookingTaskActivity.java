package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
        Intent intent = getIntent();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_task_details);

        if(navHostFragment != null){
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }
        // Retrieve intent data

        if (intent != null) {
            int taskerId = intent.getIntExtra("tasker_id", -1);
            String bookingTime = intent.getStringExtra("booking_time");

            String[] parts = null;
            if (bookingTime != null) {
                parts = bookingTime.split(" ");
            }

            String bookingDate = null;
            String bookingTimeOnly = null;
            if (parts != null) {
                bookingDate = parts[0];
                bookingTimeOnly = parts[1];
            }


            // Call API to get tasker details
            getTaskerDetails(taskerId,bookingDate, bookingTimeOnly);
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

                    // Pass data to the fragment
                    passDataToFragment(taskerId, name, profilePicture, hourlyRate, bookingDate, bookingTimeOnly);

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
        TextView hourlyRateTextView = findViewById(R.id.hourlyRate);
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
    private void passDataToFragment(int taskerId, String name, String profilePicture, String hourlyRate, String bookingDate, String bookingTimeOnly) {
        ReviewAndConfirmFragment fragment = new ReviewAndConfirmFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tasker_id", taskerId);
        bundle.putString("name", name);
        bundle.putString("profile_picture", profilePicture);
        bundle.putString("hourly_rate", hourlyRate);
        bundle.putString("booking_date", bookingDate);
        bundle.putString("booking_time", bookingTimeOnly);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_task_details, fragment)
                .addToBackStack(null)
                .commit();
    }

}
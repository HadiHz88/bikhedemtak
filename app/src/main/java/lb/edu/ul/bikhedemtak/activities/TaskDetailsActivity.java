package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.databinding.ActivityTaskDetailsBinding;

public class TaskDetailsActivity extends AppCompatActivity {

    private ActivityTaskDetailsBinding binding;
    private String userInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Task Details");
        }

        // Retrieve data from intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String profilePicture = intent.getStringExtra("profile_picture");
            String hourlyRate = intent.getStringExtra("hourly_rate");
            String bookingDate = intent.getStringExtra("booking_date");
            String bookingTime = intent.getStringExtra("booking_time");

            // Set data to UI
            binding.UserName.setText(name);
            Glide.with(this)
                    .load(profilePicture)
                    .placeholder(R.drawable.img)
                    .into(binding.ProfileImage);

            binding.ParagED.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    userInput = s.toString();
                }
            });


            // Button click to navigate to ReviewConfirmActivity
            binding.ReviewTaskButton.setOnClickListener(v -> {
                startReviewAndConfirmActivity(name, profilePicture, hourlyRate, bookingDate, bookingTime, userInput);
            });
        }
    }
    private void startReviewAndConfirmActivity(String name, String profilePicture, String hourlyRate, String bookingDate, String bookingTime, String userInput) {
        Intent intent = new Intent(this, ReviewAndConfirmActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("profile_picture", profilePicture);
        intent.putExtra("hourly_rate", hourlyRate);
        intent.putExtra("booking_date", bookingDate);
        intent.putExtra("booking_time", bookingTime);
        intent.putExtra("user_input", userInput);
        startActivity(intent);
    }
}
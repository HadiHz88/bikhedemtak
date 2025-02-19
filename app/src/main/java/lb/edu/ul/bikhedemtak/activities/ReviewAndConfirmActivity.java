package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.databinding.ActivityReviewAndConfirmBinding;

public class ReviewAndConfirmActivity extends AppCompatActivity {

    private ActivityReviewAndConfirmBinding binding;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewAndConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Task Details");
        }

        // Retrieve data from intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String profilePicture = intent.getStringExtra("profile_picture");
            String hourlyRate = "$" + intent.getStringExtra("hourly_rate") + "/hr";
            String totalRate = "$" + intent.getStringExtra("hourly_rate");
            String bookingDate = intent.getStringExtra("booking_date");
            String bookingTime = intent.getStringExtra("booking_time");
            String userInput = intent.getStringExtra("user_input");

            // Set data to UI
            binding.UserName.setText(name);
            Glide.with(this).load(profilePicture).into(binding.ProfileImage);
            binding.hourlyRate.setText(hourlyRate);
            binding.totalRate.setText(totalRate);
            binding.bookingDate.setText(bookingDate);
            binding.bookingTime.setText(bookingTime);
            binding.ParagED.setText(userInput);

            binding.showDialogButton.setOnClickListener(v -> ShowDialog(name));
        }
    }

    public void ShowDialog(String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        TextView dialogText1 = dialogView.findViewById(R.id.dialog_text1);
        dialogText1.setText("You are booked: " + username);

        Button continueButton = dialogView.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(v -> dialog.dismiss());

        dialog = builder.create();
        dialog.show();
    }
}


package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.databinding.ActivityReviewAndConfirmBinding;
import lb.edu.ul.bikhedemtak.homepageActivity;
import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;

public class ReviewAndConfirmActivity extends AppCompatActivity {

    private ActivityReviewAndConfirmBinding binding;
    private AlertDialog dialog;
    private AlertDialog progressDialog;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewAndConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();
        populateData();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Review And Confirm");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void populateData() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String profilePicture = intent.getStringExtra("profile_picture");
            String hourlyRate = "$" + intent.getStringExtra("hourly_rate") + "/hr";
            String totalRate = "$" + intent.getStringExtra("hourly_rate");
            String bookingDate = intent.getStringExtra("booking_date");
            String bookingTime = intent.getStringExtra("booking_time");
            String userInput = intent.getStringExtra("user_input");

            binding.UserName.setText(name);
            Glide.with(this)
                    .load(profilePicture)
                    .placeholder(R.drawable.default_pp)
                    .error(R.drawable.default_pp)
                    .into(binding.ProfileImage);
            binding.hourlyRate.setText(hourlyRate);
            binding.totalRate.setText(totalRate);
            binding.bookingDate.setText(bookingDate);
            binding.bookingTime.setText(bookingTime);
            binding.ParagED.setText(userInput);

            binding.showDialogButton.setOnClickListener(v -> showConfirmationDialog(name));
        }
    }

    private void showConfirmationDialog(String username) {
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        TextView dialogText1 = dialogView.findViewById(R.id.dialog_text1);
        dialogText1.setText("You are booking: " + username);

        Button continueButton = dialogView.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(v -> createTask());

        dialog = builder.create();
        dialog.show();
    }

    private void showProgressDialog() {
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void createTask() {
        showProgressDialog();

        JSONObject newTask = new JSONObject();
        try {
            newTask.put("requester_id", SharedPrefsManager.getUserId(this));
            newTask.put("tasker_id", getIntent().getIntExtra("tasker_id", -1));
            newTask.put("task_description", getIntent().getStringExtra("user_input"));
            newTask.put("category_id", getIntent().getIntExtra("category_id", 1));
            newTask.put("booking_date", getIntent().getStringExtra("booking_date") + " " + getIntent().getStringExtra("booking_time"));

            // Log the request for debugging
            Log.d("TaskCreation", "Request body: " + newTask.toString());

        } catch (Exception e) {
            e.printStackTrace();
            hideProgressDialog();
            showError("Error creating task. Please try again.");
            return;
        }

        String postTaskEndpoint = "postTask.php"; // or whatever the correct endpoint is

        ApiRequest.getInstance().makePostRequest(this, postTaskEndpoint, newTask, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgressDialog();
                if (dialog != null) {
                    dialog.dismiss();
                }
                handleTaskCreationSuccess();
            }

            @Override
            public void onFailure(String error) {
                hideProgressDialog();
                Log.e("TaskCreation", "Error creating task: " + error);
                showError("Failed to create task: " + error);
            }
        });
    }

    private void handleTaskCreationSuccess() {
        // Show success message
        Toast.makeText(this, "Task created successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to appropriate screen (e.g., TaskListActivity or HomeActivity)
        Intent intent = new Intent(this, homepageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showError(String message) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        hideProgressDialog();
    }
}
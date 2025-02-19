package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.BookingTaskActivity;
import lb.edu.ul.bikhedemtak.databinding.ReviewConfirmFragmentBinding;
import com.google.android.material.button.MaterialButton;

public class ReviewAndConfirmFragment extends Fragment {

    private ReviewConfirmFragmentBinding binding;
    private AlertDialog dialog;  // Reference to store the dialog instance


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using View Binding
        binding = ReviewConfirmFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Retrieve arguments
        Bundle args = getArguments();
        if (args != null) {
            String bookingDate = args.getString("booking_date", "N/A");
            String bookingTime = args.getString("booking_time", "N/A");
            String hourlyRate = "$" + args.getString("hourly_rate", "N/A") + "/hr";
            String totalRate = "$" + args.getString("hourly_rate", "N/A");
            String name = args.getString("name", "N/A");
            String profilePicture = args.getString("profile_picture", "N/A");

            // Update TextViews
            binding.bookingDate.setText(bookingDate);
            binding.bookingTime.setText(bookingTime);
            binding.hourlyRate.setText(hourlyRate);
            binding.totalRate.setText(totalRate); //temp
            binding.UserName.setText(name);
            binding.ProfileImage.setTag(profilePicture);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the toolbar title
        if (getActivity() != null) {
            ((BookingTaskActivity) getActivity()).getSupportActionBar().setTitle("Task Details");
        }


        // Assuming you have a TextView with the ID `usernameTextView` that holds the username
        TextView usernameTextView = view.findViewById(R.id.UserName);
        String username = usernameTextView.getText().toString(); // Get the username from the TextView

        // Find the button and set the click listener
        MaterialButton showDialogButton = view.findViewById(R.id.show_dialog_button);
        showDialogButton.setOnClickListener(v -> {
            // Call ShowDialog with the username
            ShowDialog(v, username);
        });
    }



    // Function to show the dialog
    public void ShowDialog(View view, String username) {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        // Set the username text inside the dialog
        TextView dialogText1 = dialogView.findViewById(R.id.dialog_text1);
        dialogText1.setText("You are booked: " + username);

        // Set up the continue button action
        Button continueButton = dialogView.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(v -> {
            // Dismiss the dialog when continue is clicked
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss(); // Dismiss the dialog using the reference
            }
        });

        // Create and show the dialog
        dialog = builder.create();  // Store the dialog instance
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding to avoid memory leaks
    }
}
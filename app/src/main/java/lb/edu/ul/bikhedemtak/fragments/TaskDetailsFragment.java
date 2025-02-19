package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.BookingTaskActivity;
import lb.edu.ul.bikhedemtak.databinding.TaskDetailsFragmentBinding;


public class TaskDetailsFragment extends Fragment {

    private TaskDetailsFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = TaskDetailsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Safely set Toolbar title (if needed)
        if (getActivity() != null && ((BookingTaskActivity) getActivity()).getSupportActionBar() != null) {
            ((BookingTaskActivity) getActivity()).getSupportActionBar().setTitle("Task Details");
        }

        // Get arguments passed to the fragment
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name", "N/A");
            String profilePicture = args.getString("profile_picture", "N/A");
            
            // Set the tasker's name and image
            binding.UserName.setText(name);
            Glide.with(this)
                    .load(profilePicture)
                    .placeholder(R.drawable.img) // Replace with a placeholder image
                    .into(binding.ProfileImage);

        }
        
        EditText paragED = binding.ParagED;
        paragED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(args != null){
                    args.putString("user_input", s.toString());
                }
            }
        });

        binding.ReviewTaskButton.setOnClickListener(v -> {
            // Pass the data and user input to ReviewAndConfirmFragment
            passDataToFragment();
        });
    }

    private void passDataToFragment() {
        Bundle args = getArguments();
        if (args != null) {
            String userInput = binding.ParagED.getText().toString();
            args.putString("user_input", userInput); // Add user input to arguments

            ReviewAndConfirmFragment fragment = new ReviewAndConfirmFragment();
            fragment.setArguments(args);

            // Navigate to the ReviewAndConfirmFragment
            NavController navController = NavHostFragment.findNavController(this);
            if (navController != null) {
                navController.navigate(R.id.action_taskDetailsFragment_to_reviewAndConfirmFragment);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
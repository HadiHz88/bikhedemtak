package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

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

        // Handle button click to navigate
        if (getView() != null) {
            binding.ReviewTaskButton.setOnClickListener(v -> {
                // Ensure the NavController is properly obtained
                NavController navController = NavHostFragment.findNavController(TaskDetailsFragment.this);
                if (navController != null) {
                    navController.navigate(R.id.action_taskDetailsFragment_to_reviewAndConfirmFragment);
                }
            });
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
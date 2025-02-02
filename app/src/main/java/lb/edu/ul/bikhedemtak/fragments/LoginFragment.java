package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.appbar.MaterialToolbar;
import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.AuthActivity;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupNavigation(view);
        updateToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolbar(); // Update the toolbar whenever the fragment is visible
    }

    private void setupNavigation(View view) {
        view.findViewById(R.id.btn_GoToRegister).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    private void updateToolbar() {
        MaterialToolbar toolbar = ((AuthActivity) requireActivity()).getToolBar();
        toolbar.setTitle("Login"); // Update the title

        // Ensure the "Skip" button is visible
        MenuItem skipItem = toolbar.getMenu().findItem(R.id.action_skip);
        if (skipItem != null) {
            skipItem.setVisible(true); // Show the "Skip" button
        }


    }
}
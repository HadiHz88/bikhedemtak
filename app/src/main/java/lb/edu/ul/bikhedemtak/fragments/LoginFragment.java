package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.appbar.MaterialToolbar;
import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.AuthActivity;

/**
 * Fragment handling user login functionality.
 * This fragment is part of the authentication flow and provides
 * a login interface with navigation to registration and skip options.
 */
public class LoginFragment extends Fragment {

    /**
     * Required empty constructor for fragments.
     * Do not perform any initialization here.
     */
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
        setupToolbar();
    }

    /**
     * Sets up navigation for the fragment, including the register button click handler
     * @param view The root view of the fragment
     */
    private void setupNavigation(View view) {
        view.findViewById(R.id.btn_GoToRegister).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    /**
     * Configures the toolbar with title, menu items, and click handlers.
     * The toolbar is shared with the parent AuthActivity.
     */
    private void setupToolbar() {
        MaterialToolbar toolbar = ((AuthActivity) requireActivity()).getToolBar();

        // Configure toolbar title and menu
        toolbar.setTitle("Login");
        setupToolbarMenu(toolbar);
    }

    /**
     * Sets up the toolbar menu items and their click handlers
     * @param toolbar The MaterialToolbar instance to configure
     */
    private void setupToolbarMenu(MaterialToolbar toolbar) {
        // Clear existing menu items and inflate auth menu
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_auth);

        // Set up menu item click listener
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_skip) {
                handleSkipAction();
                return true;
            }
            return false;
        });
    }

    /**
     * Handles the skip action from the toolbar menu
     * TODO: Implement actual skip functionality
     */
    private void handleSkipAction() {
        Toast.makeText(getContext(), "Skipped", Toast.LENGTH_SHORT).show();
        // TODO: Implement navigation to main app flow for guest users
    }
}
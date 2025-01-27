package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.AuthActivity;

// Login Fragment
public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    // Inflate the layout for this fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    // This method is called when the fragment's view has been created
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set click listener for the "Go to Register" button
        view.findViewById(R.id.btn_GoToRegister).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });

        // Access the toolbar from AuthActivity
        MaterialToolbar toolbar = ((AuthActivity) requireActivity()).getToolBar();

        // Set the title
        toolbar.setTitle("Login");

        // Add a skip button
        toolbar.getMenu().clear(); // Clear any previous menu items
        toolbar.inflateMenu(R.menu.menu_auth);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_skip) {
                // Handle skip action
                Toast.makeText(getContext(), "Skipped", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }
}
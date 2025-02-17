package lb.edu.ul.bikhedemtak;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class UserProfileFragment extends Fragment {
    private TextView txtUserName, txtUserEmail;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Initialize views
        txtUserName = view.findViewById(R.id.txtUserName);
        txtUserEmail = view.findViewById(R.id.txtUserEmail);

        // Save default user data for testing
        SharedPrefsManager.saveDefaultUserData(getContext());

        // Fetch user profile from SharedPreferences
        fetchUserProfile();

        // Set unique titles for each option
        TextView txtChangePassword = view.findViewById(R.id.txtChangePassword).findViewById(R.id.txtOptionTitle);
        TextView txtPayment = view.findViewById(R.id.txtPayment).findViewById(R.id.txtOptionTitle);
        TextView txtPromos = view.findViewById(R.id.txtPromos).findViewById(R.id.txtOptionTitle);

        txtChangePassword.setText("Change Password");
        txtPayment.setText("Payment");
        txtPromos.setText("Promos");

        // Set click listeners for each option
        view.findViewById(R.id.txtChangePassword).setOnClickListener(v -> {
            navigateToFragment(new ChangePasswordFragment());
        });

        view.findViewById(R.id.txtPayment).setOnClickListener(v -> {
            navigateToFragment(new PaymentFragment());
        });

        view.findViewById(R.id.txtPromos).setOnClickListener(v -> {
            navigateToFragment(new PromosFragment());
        });

        // Logout button
        view.findViewById(R.id.txtLogout).setOnClickListener(v -> {
            // Perform logout logic here
            SharedPrefsManager.clearUserData(getContext()); // Clear user data
            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
            // Navigate to the login screen or clear session
        });

        // Buy a Gift Card button
        view.findViewById(R.id.btnGiftCard).setOnClickListener(v -> {
            navigateToFragment(new BuyGiftCardFragment());
        });

        // Become a Tasker button
        view.findViewById(R.id.txtBecomeTasker).setOnClickListener(v -> {
            navigateToFragment(new BecomeTaskerFragment());
        });

        return view;
    }

    private void navigateToFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void fetchUserProfile() {
        // Fetch user data from SharedPreferences
        Context context = getContext();
        if (context != null) {
            String name = SharedPrefsManager.getUserName(context);
            String email = SharedPrefsManager.getUserEmail(context);

            // Update UI with user data
            txtUserName.setText(name);
            txtUserEmail.setText(email);
        } else {
            Toast.makeText(getContext(), "Failed to fetch user profile", Toast.LENGTH_SHORT).show();
        }
    }
}
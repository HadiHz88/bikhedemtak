package lb.edu.ul.bikhedemtak.fragments;

import android.content.Intent;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.AuthActivity;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;

public class LoginFragment extends Fragment {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private MaterialButton buttonLogin;

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
        initializeViews(view);
        setupListeners();
        ((AuthActivity) requireActivity()).updateToolbar("Login");
    }

    private void initializeViews(View view) {
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);

        view.findViewById(R.id.btn_GoToRegister).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    private void setupListeners() {
        buttonLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading state
        buttonLogin.setEnabled(false);
        buttonLogin.setText("Logging in...");

        try {
            JSONObject loginParams = new JSONObject();
            loginParams.put("email", email);
            loginParams.put("password", password);

            ApiRequest.getInstance().makePostRequest(
                    requireContext(),
                    "auth_login.php",
                    loginParams,
                    new ApiRequest.ResponseListener<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                if (response.getString("status").equals("success")) {
                                    JSONObject userData = response.getJSONObject("data");

                                    // Save user data
                                    SharedPrefsManager.saveUserData(requireContext(), userData);

                                    // Navigate to main activity
                                    requireActivity().finish();
                                    startActivity(new Intent(requireContext(), MainActivity.class));
                                } else {
                                    showError(response.optString("message", "Login failed"));
                                }
                            } catch (Exception e) {
                                showError("Error processing response");
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            showError(error);
                        }
                    }
            );
        } catch (Exception e) {
            showError("Error creating request");
        }
    }

    private void showError(String message) {
        requireActivity().runOnUiThread(() -> {
            buttonLogin.setEnabled(true);
            buttonLogin.setText(R.string.login);
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });
    }
}
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
    import lb.edu.ul.bikhedemtak.homepageActivity;
    import lb.edu.ul.bikhedemtak.testing.TestActivity;
    import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;

    /**
     * Fragment for handling user login.
     */
    public class LoginFragment extends Fragment {
        // EditText for user email input
        private TextInputEditText editTextEmail;
        private TextInputEditText editTextPassword;
        // Button to trigger login action
        private MaterialButton buttonLogin;

        /**
         * Default constructor for LoginFragment.
         * Required empty public constructor.
         */
        public LoginFragment() {
            // Required empty public constructor
        }

        /**
         * Called to have the fragment instantiate its user interface view.
         * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
         * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
         * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
         * @return Return the View for the fragment's UI, or null.
         */
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_login, container, false);
        }

        /**
         * Called immediately after onCreateView has returned, but before any saved state has been restored in to the view.
         * @param view The View returned by onCreateView.
         * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
         */
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            initializeViews(view);
            setupListeners();
            ((AuthActivity) requireActivity()).updateToolbar("Login");
        }

        /**
         * Initialize the views in the fragment.
         * @param view The root view of the fragment.
         */
        private void initializeViews(View view) {
            editTextEmail = view.findViewById(R.id.editTextEmail);
            editTextPassword = view.findViewById(R.id.editTextPassword);
            buttonLogin = view.findViewById(R.id.buttonLogin);

            view.findViewById(R.id.btn_GoToRegister).setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            });
        }

        /**
         * Set up listeners for the views.
         */
        private void setupListeners() {
            buttonLogin.setOnClickListener(v -> attemptLogin());
        }

        /**
         * Attempt to log in the user with the provided credentials.
         */
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
                        "login.php",
                        loginParams,
                        new ApiRequest.ResponseListener<JSONObject>() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                try {
                                    if (response.getString("status").equals("success")) {
                                        JSONObject userData = response.getJSONObject("data");

                                        // Save user data
                                        SharedPrefsManager.saveUserData(requireContext(), userData);

                                        requireActivity().finish();
                                        startActivity(new Intent(requireContext(), homepageActivity.class));
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

        /**
         * Show an error message to the user.
         * @param message The error message to be displayed.
         */
        private void showError(String message) {
            requireActivity().runOnUiThread(() -> {
                buttonLogin.setEnabled(true);
                buttonLogin.setText(R.string.login);
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            });
        }
    }
package lb.edu.ul.bikhedemtak.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.AuthActivity;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;

/**
 * Fragment handling user registration functionality.
 * Provides registration interface with navigation to login, clickable policy links,
 * and skip options. Includes styled text with clickable Terms of Service and Privacy Policy.
 */
public class RegisterFragment extends Fragment {

    private EditText etFullName, etEmail, etPhone, etPassword, etConfirmPassword;
    private MaterialButton buttonRegister;

    /**
     * Required empty constructor for fragments.
     * Do not perform any initialization here.
     */
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupListeners();
        setupNavigation(view);
        setupPolicyText(view);

        ((AuthActivity) requireActivity()).updateToolbar("Register");
    }

    private void initializeViews(View view) {
        etFullName = view.findViewById(R.id.editTextFullName);
        etEmail = view.findViewById(R.id.editTextEmail);
        etPhone = view.findViewById(R.id.editTextPhone);
        etPassword = view.findViewById(R.id.editTextPassword);
        etConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        buttonRegister = view.findViewById(R.id.buttonRegister);
    }

    private void setupListeners() {
        buttonRegister.setOnClickListener(v -> attemptRegistration());
    }

    private void attemptRegistration() {
        // Get input values
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // Validate input
        if (!validateInput(fullName, email, password, confirmPassword)) {
            return;
        }

        // Show loading state
        buttonRegister.setEnabled(false);
        buttonRegister.setText("Registering...");

        try {
            JSONObject registrationParams = new JSONObject();
            registrationParams.put("name", fullName);
            registrationParams.put("email", email);
            registrationParams.put("password", password);
            if (!TextUtils.isEmpty(phone)) {
                registrationParams.put("phone", phone);
            }

            ApiRequest.getInstance().makePostRequest(
                    requireContext(),
                    "register.php",
                    registrationParams,
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
                                    showError(response.optString("message", "Registration failed"));
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

    private boolean validateInput(String fullName, String email, String password, String confirmPassword) {
        boolean isValid = true;

        if (TextUtils.isEmpty(fullName)) {
            ((TextInputLayout) etFullName.getParent().getParent()).setError("Name is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(email)) {
            ((TextInputLayout) etEmail.getParent().getParent()).setError("Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ((TextInputLayout) etEmail.getParent().getParent()).setError("Invalid email format");
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            ((TextInputLayout) etPassword.getParent().getParent()).setError("Password is required");
            isValid = false;
        } else if (password.length() < 8) {
            ((TextInputLayout) etPassword.getParent().getParent()).setError("Password must be at least 8 characters");
            isValid = false;
        }

        if (!password.equals(confirmPassword)) {
            ((TextInputLayout) etConfirmPassword.getParent().getParent()).setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    private void showError(String message) {
        requireActivity().runOnUiThread(() -> {
            buttonRegister.setEnabled(true);
            buttonRegister.setText(R.string.sign_up);
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });
    }



    /**
     * Sets up navigation for the fragment, including the login button click handler
     * @param view The root view of the fragment
     */
    private void setupNavigation(View view) {
        view.findViewById(R.id.btn_GoToLogin).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }

    /**
     * Sets up the policy agreement text with clickable spans for Terms of Service
     * and Privacy Policy
     * @param view The root view of the fragment
     */
    private void setupPolicyText(View view) {
        TextView textView = view.findViewById(R.id.policy_agreement);
        textView.setHighlightColor(Color.TRANSPARENT);

        String fullText = getString(R.string.policy_agreement);
        String termsText = getString(R.string.terms_of_service);
        String privacyText = getString(R.string.privacy_policy);

        SpannableString spannableString = getSpannableString(fullText, termsText, privacyText);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Creates a SpannableString with clickable and styled spans for Terms and Privacy Policy
     * @param fullText The complete text string
     * @param firstHighlight Terms of Service text to highlight
     * @param secondHighlight Privacy Policy text to highlight
     * @return Configured SpannableString with clickable spans
     */
    private SpannableString getSpannableString(String fullText, String firstHighlight, String secondHighlight) {
        SpannableString spannableString = new SpannableString(fullText);

        int startIndexFirst = fullText.indexOf(firstHighlight);
        int endIndexFirst = startIndexFirst + firstHighlight.length();
        int startIndexSecond = fullText.indexOf(secondHighlight);
        int endIndexSecond = startIndexSecond + secondHighlight.length();

        // Configure Terms of Service span
        spannableString.setSpan(createClickableSpan("Terms of Service"),
                startIndexFirst,
                endIndexFirst,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Configure Privacy Policy span
        spannableString.setSpan(createClickableSpan("Privacy Policy"),
                startIndexSecond,
                endIndexSecond,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * Creates a ClickableSpan with custom styling and click behavior
     * @param title The title for the dialog to show when clicked
     * @return Configured ClickableSpan
     */
    private ClickableSpan createClickableSpan(String title) {
        return new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showDialog(title, "Here are the " + title + " details...");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.soft_pink));
                ds.setUnderlineText(false);
            }
        };
    }

    /**
     * Shows a Material dialog with the specified title and message
     * @param title Dialog title
     * @param message Dialog content message
     */
    private void showDialog(String title, String message) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
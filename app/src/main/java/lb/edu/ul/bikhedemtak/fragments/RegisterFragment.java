package lb.edu.ul.bikhedemtak.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.AuthActivity;

/**
 * Fragment handling user registration functionality.
 * Provides registration interface with navigation to login, clickable policy links,
 * and skip options. Includes styled text with clickable Terms of Service and Privacy Policy.
 */
public class RegisterFragment extends Fragment {

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

        setupNavigation(view);
        setupPolicyText(view);

        ((AuthActivity) requireActivity()).updateToolbar("Register");
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
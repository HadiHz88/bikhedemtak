package lb.edu.ul.bikhedemtak.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.AuthActivity;

// Register Fragment
public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    // Inflate the layout for this fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    // This method is called when the fragment's view has been created
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set click listener for the "Go to Login" button
        view.findViewById(R.id.btn_GoToLogin).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        });


        // Highlighting the words "Terms of Service" and "Privacy Policy"

        // Find the TextView in your fragment's layout
        TextView textView = view.findViewById(R.id.policy_agreement);
        textView.setHighlightColor(Color.TRANSPARENT); // to prevent highlight

        // Retrieve the full text from resources
        String fullText = getString(R.string.policy_agreement);

        // Retrieve the words to highlight from resources
        String firstHighlight = getString(R.string.terms_of_service);
        String secondHighlight = getString(R.string.privacy_policy);

        // Create a Spannable String
        SpannableString spannableString = new SpannableString(fullText);

        // Find the start and end indices of the words to make clickable
        int startIndexFirst = fullText.indexOf(firstHighlight);
        int endIndexFirst = startIndexFirst + firstHighlight.length();

        int startIndexSecond = fullText.indexOf(secondHighlight);
        int endIndexSecond = startIndexSecond + secondHighlight.length();

        // Set a ClickableSpan for "Terms of Service"
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Show a dialog for Terms of Service
                showDialog("Terms of Service", "Here are the Terms of Service details...");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.soft_pink)); // Highlight color
                ds.setUnderlineText(false); // Remove underline
            }
        }, startIndexFirst, endIndexFirst, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set a ClickableSpan for "Privacy Policy"
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Show a dialog for Privacy Policy
                showDialog("Privacy Policy", "Here are the Privacy Policy details...");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.soft_pink)); // Highlight color
                ds.setUnderlineText(false); // Remove underline
            }
        }, startIndexSecond, endIndexSecond, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Enable clickable text in the TextView
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


        // Access the toolbar from AuthActivity
        MaterialToolbar toolbar = ((AuthActivity) requireActivity()).getToolBar();

        // Set the title
        toolbar.setTitle("Register");

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

    // Helper method to show a dialog
    private void showDialog(String title, String message) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
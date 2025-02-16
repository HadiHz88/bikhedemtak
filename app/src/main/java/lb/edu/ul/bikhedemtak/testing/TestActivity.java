package lb.edu.ul.bikhedemtak.testing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.AuthActivity;
import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;

public class TestActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);

        // Check if the user is logged in
        if (SharedPrefsManager.isLoggedIn(this)) {
            // User is logged in, display welcome message
            String welcomeMessage = "Welcome, " + SharedPrefsManager.getUserName(this) + "!";
            tvWelcomeMessage.setText(welcomeMessage);
        } else {
            // User is not logged in, redirect to AuthActivity
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
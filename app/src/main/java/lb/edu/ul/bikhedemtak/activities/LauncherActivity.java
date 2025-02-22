package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import lb.edu.ul.bikhedemtak.homepageActivity;
import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;

/**
 * LauncherActivity is the entry point of the application.
 * It checks if the user is logged in and navigates to the appropriate activity.
 */
public class LauncherActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            // Check if the user is logged in using SharedPrefsManager
        if (SharedPrefsManager.isLoggedIn(this)) {
            // If logged in, start TestActivity
            startActivity(new Intent(this, homepageActivity.class));
        } else {
            // If not logged in, start AuthActivity
            startActivity(new Intent(this, GetStartedActivity.class));
        }
    }
}
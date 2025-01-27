package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

import lb.edu.ul.bikhedemtak.R;

public class AuthActivity extends AppCompatActivity {

    private MaterialToolbar authToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // set up the toolbar
        authToolBar = findViewById(R.id.authToolBarId);
        setSupportActionBar(authToolBar);

        // set up the navController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        // This is the key part - it sets up the ActionBar with proper back button handling
        NavigationUI.setupActionBarWithNavController(this, navController);

        // Listen to fragment changes and update the toolbar as needed
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Make sure the back button is always shown except for the start destination
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (destination.getId() == R.id.loginFragment) {
                getSupportActionBar().setTitle("Login");
            } else if (destination.getId() == R.id.registerFragment) {
                getSupportActionBar().setTitle("Register");
            }
        });
    }

    public MaterialToolbar getToolBar(){
        return authToolBar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

}
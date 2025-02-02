package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.Toast;

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
import lb.edu.ul.bikhedemtak.R;

/**
 * Authentication Activity that handles user login and registration screens.
 * This activity uses the Navigation component to manage fragment transitions
 * and implements Material Design through MaterialToolbar.
 */
public class AuthActivity extends AppCompatActivity {

    // The Material Design toolbar for the authentication screens
    private MaterialToolbar authToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display for better visual experience
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        // Configure window insets to handle system bars properly
        setupWindowInsets();

        // Initialize and configure the toolbar
        setupToolbar();

        // Set up navigation with the Navigation component
        setupNavigation();
    }

    /**
     * Configures window insets to handle system bars (status bar, navigation bar)
     * properly in edge-to-edge mode
     */
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Initializes and sets up the MaterialToolbar
     */
    private void setupToolbar() {
        authToolBar = findViewById(R.id.authToolBarId);
        setSupportActionBar(authToolBar);

        // Setup default menu
        authToolBar.inflateMenu(R.menu.menu_auth);


        authToolBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_skip) {
                handleSkipAction();
                return true;
            }
            return false;
        });
    }

    private void handleSkipAction() {
        Toast.makeText(this, "Skipped", Toast.LENGTH_SHORT).show();
        // TODO: Implement navigation to main app flow for guest users
    }

    /**
     * Configures navigation using the Navigation component, including:
     * - Setting up the NavController
     * - Configuring the ActionBar with navigation
     * - Setting up destination change listeners for toolbar updates
     */
    private void setupNavigation() {
        // Get NavController from the NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        // Configure ActionBar with NavController for proper back button handling
        NavigationUI.setupActionBarWithNavController(this, navController);

        // Update toolbar title based on current destination
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Enable back button for all destinations
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Set appropriate title based on current fragment
            if (destination.getId() == R.id.loginFragment) {
                getSupportActionBar().setTitle("Login");
            } else if (destination.getId() == R.id.registerFragment) {
                getSupportActionBar().setTitle("Register");
            }
        });
    }

    /**
     * Provides access to the toolbar for other components
     * @return The activity's MaterialToolbar instance
     */
    public MaterialToolbar getToolBar() {
        return authToolBar;
    }

    /**
     * Handles the Up button press in the ActionBar
     * @return true if navigation was handled successfully
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
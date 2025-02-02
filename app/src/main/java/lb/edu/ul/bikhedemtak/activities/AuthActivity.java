package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
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
    private MenuProvider menuProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display for better visual experience
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        // Initialize and configure the toolbar
        setupToolbar();

        // Set up navigation with the Navigation component
        setupNavigation();
    }

    /**
     * Initializes and sets up the MaterialToolbar
     */
    private void setupToolbar() {
        authToolBar = findViewById(R.id.authToolBarId);
        setSupportActionBar(authToolBar);
        // Initialize and add the menu provider once
        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_auth, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_skip) {
                    Toast.makeText(AuthActivity.this, "Skip clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

            @Override
            public void onMenuClosed(@NonNull Menu menu) {
                MenuProvider.super.onMenuClosed(menu);
            }
        };
        addMenuProvider(menuProvider);
    }

    public void updateToolbar(String title) {
        authToolBar.setTitle(title);

        authToolBar.post(() -> {
            Menu menu = authToolBar.getMenu();
            if (menu != null) {
                MenuItem skipItem = menu.findItem(R.id.action_skip);
                if (skipItem != null) {
                    SpannableString styledTitle = new SpannableString(skipItem.getTitle());
                    styledTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.soft_pink)),
                            0, styledTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    skipItem.setTitle(styledTitle);
                    skipItem.setVisible(true);
                }
            }
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
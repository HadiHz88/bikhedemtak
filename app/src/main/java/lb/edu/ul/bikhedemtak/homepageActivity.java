package lb.edu.ul.bikhedemtak;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import lb.edu.ul.bikhedemtak.databinding.ActivityHomepageBinding;

public class homepageActivity extends AppCompatActivity {

    private ActivityHomepageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HomepageActivity", "onCreate() called");

        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_homepage);

        if (navHostFragment == null) {
            Log.e("HomepageActivity", "NavHostFragment is null. Check the ID in the layout.");
            return;
        }

        NavController navController = navHostFragment.getNavController();
        Log.d("HomepageActivity", "NavController initialized: " + navController);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HomepageActivity", "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("HomepageActivity", "onPause() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("HomepageActivity", "onDestroy() called");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_homepage);
        return super.onSupportNavigateUp();
    }
}
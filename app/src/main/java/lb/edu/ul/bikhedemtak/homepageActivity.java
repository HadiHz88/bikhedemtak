package lb.edu.ul.bikhedemtak;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // Set up NavController and AppBarConfiguration



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

}
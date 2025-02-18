package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import lb.edu.ul.bikhedemtak.R;

public class MyTasksFragment extends Fragment {
    public MyTasksFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.my_tasks_fragment_container);

        if (navHostFragment == null) {
            navHostFragment = NavHostFragment.create(R.navigation.my_tasks_nav_graph);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.my_tasks_fragment_container, navHostFragment)
                    .commitNow();
        }

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.my_tasks_nav);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
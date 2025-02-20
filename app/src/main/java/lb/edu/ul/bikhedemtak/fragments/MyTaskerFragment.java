package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.MainActivity;

public class MyTaskerFragment extends Fragment {

    public MyTaskerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tasker, container, false);

        Button btnFavoriteTasker = view.findViewById(R.id.btnFavoriteTasker);
        Button btnPastTasker = view.findViewById(R.id.btnPastTasker);

        btnFavoriteTasker.setOnClickListener(v -> {
            // Navigate to FavoriteTaskerFragment using ViewPager
            if (getActivity() instanceof MainActivity) {
                MainActivity activity = (MainActivity) getActivity();
                ViewPager viewPager = activity.findViewById(R.id.view_pager);
                viewPager.setCurrentItem(1); // Assuming FavoriteTaskerFragment is the second item
            }
        });

        btnPastTasker.setOnClickListener(v -> {
            // Navigate to PastTaskerFragment using ViewPager
            if (getActivity() instanceof MainActivity) {
                MainActivity activity = (MainActivity) getActivity();
                ViewPager viewPager = activity.findViewById(R.id.view_pager);
                viewPager.setCurrentItem(2); // Assuming PastTaskerFragment is the third item
            }
        });

        return view;
    }
}
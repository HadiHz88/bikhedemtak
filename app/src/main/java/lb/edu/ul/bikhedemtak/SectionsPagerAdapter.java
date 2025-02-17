package lb.edu.ul.bikhedemtak;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FavoriteTaskerFragment();
            case 1:
                return new PastTaskerFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2; // Number of fragments (Favorite and Past Tasker)
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Favorite Tasker";
            case 1:
                return "Past Tasker";
            default:
                return null;
        }
    }
}
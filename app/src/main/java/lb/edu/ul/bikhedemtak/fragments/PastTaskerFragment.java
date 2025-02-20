package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import org.json.JSONException;
import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;

public class PastTaskerFragment extends Fragment {

    private String taskerId;
    private boolean isFavorite = false;

    public PastTaskerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_tasker, container, false);

        // Mock data (replace with database fetch later)
        String taskerName = "John Doe";
        int taskerRating = 4; // Example rating out of 5
        String taskerJobs = "3 overall jobs";
        int taskerPhoto = R.drawable.img; // Replace with your image resource
        taskerId = "1"; // Replace with actual tasker ID from API

        // Set tasker photo
        ImageView taskerPhotoView = view.findViewById(R.id.imageTasker);
        taskerPhotoView.setImageResource(taskerPhoto);

        // Set tasker name
        TextView taskerNameView = view.findViewById(R.id.taskerName);
        taskerNameView.setText(taskerName);

        // Set tasker jobs
        TextView taskerJobsView = view.findViewById(R.id.taskerJobs);
        taskerJobsView.setText(taskerJobs);

        // Dynamically add stars based on rating
        LinearLayout ratingContainer = view.findViewById(R.id.ratingContainer);
        for (int i = 0; i < 5; i++) {
            ImageView star = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0); // Adjust margins as needed
            star.setLayoutParams(params);

            if (i < taskerRating) {
                star.setImageResource(R.drawable.ic_star_filled); // Filled star
            } else {
                star.setImageResource(R.drawable.ic_star_empty); // Empty star
            }

            ratingContainer.addView(star);
        }

        // Handle heart button click
        ImageView btnFavorite = view.findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            updateFavoriteStatus(btnFavorite, taskerId, isFavorite);
        });

        // Handle book button click
        Button btnBook = view.findViewById(R.id.btnBook);
        btnBook.setOnClickListener(v -> {
            // Navigate to TaskerProfileFragment (assume it's implemented by another team member)
            navigateToFragment(new TaskerProfileFragment());
        });

        return view;
    }

    private void updateFavoriteStatus(ImageView btnFavorite, String taskerId, boolean isFavorite) {
        String status = isFavorite ? "add" : "remove";
        String url = "http://10.0.2.2:80/bikhedemtak_mobile_api/api/update_fav_tasker.php?user_id=1&tasker_id=" + taskerId + "&status=" + status;

        ApiRequest.getInstance().makeGetObjectRequest(getContext(), url, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        btnFavorite.setImageResource(isFavorite ? R.drawable.ic_heart_filled : R.drawable.ic_heart_empty);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                // Handle error
            }
        });
    }

    private void navigateToFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
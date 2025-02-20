package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;

public class FavoriteTaskerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tasker, container, false);
        LinearLayout taskerContainer = view.findViewById(R.id.taskerContainer);
        String userId = "1"; // Get from session

        ApiRequest.getInstance().makeGetArrayRequest(getContext(), "get_favorite_taskers.php?user_id=" + userId, new ApiRequest.ResponseListener<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject tasker = response.getJSONObject(i);
                        TextView textView = new TextView(getContext());
                        textView.setText(tasker.getString("name"));
                        taskerContainer.addView(textView);

                        // Add rating stars
                        LinearLayout ratingContainer = view.findViewById(R.id.ratingContainer);
                        int rating = tasker.getInt("rating");
                        for (int j = 0; j < 5; j++) {
                            ImageView star = new ImageView(getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(4, 0, 4, 0);
                            star.setLayoutParams(params);

                            if (j < rating) {
                                star.setImageResource(R.drawable.ic_star_filled);
                            } else {
                                star.setImageResource(R.drawable.ic_star_empty);
                            }

                            ratingContainer.addView(star);
                        }
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

        return view;
    }
}
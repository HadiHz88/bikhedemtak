package lb.edu.ul.bikhedemtak.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;

public class SearchResultFragment extends Fragment {

    private SearchView searchView;
    private MaterialToolbar toolbar;
    private Spinner categorySpinner;
    private SeekBar hourlyRateSeekBar;
    private TextView hourlyRateTextView;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private List<SearchResult> searchResultList;

    private String query;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        if (getArguments() != null) {
            query = getArguments().getString("search");
            if (query != null) {
                performSearch(query); // Perform search when navigating
            }
        }

        // Initialize views
        toolbar = view.findViewById(R.id.SearchResultToolbar);
        searchView = view.findViewById(R.id.searchView_Result);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        hourlyRateSeekBar = view.findViewById(R.id.hourlyRateSeekBar);
        hourlyRateTextView = view.findViewById(R.id.hourlyRateTextView);
        recyclerView = view.findViewById(R.id.recyclerView_Result);


        // Set up the toolbar
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Search");
        }

        // Set up Navigation
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_homepage);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, appBarConfiguration);


        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResultList = new ArrayList<>();
        adapter = new SearchResultAdapter(searchResultList);
        recyclerView.setAdapter(adapter);




        // Fetch categories from the database/API
        fetchCategories();


        // Set up hourly rate seekbar
        hourlyRateSeekBar.setMax(100); // Max hourly rate is 100
        hourlyRateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hourlyRateTextView.setText("Hourly Rate: $0 - $" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Handle SearchView query submission
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                 performSearch(newText);
                return true;
            }
        });

        return view;
    }

    private void fetchCategories() {
        String endpoint = "getFeaturedCategories.php?limit=" ;

        ApiRequest.getInstance().makeGetArrayRequest(requireContext(), endpoint, new ApiRequest.ResponseListener<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    List<String> categories = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        String category = response.getString(i);
                        categories.add(category);
                    }

                    // Populate the Spinner
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            categories
                    );
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(spinnerAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("CATEGORY_ERROR", "Error parsing categories: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(String error) {
                Log.e("CATEGORY_ERROR", "Failed to fetch categories: " + error);
                Toast.makeText(getContext(), "Failed to fetch categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSearch(String query) {
        String category = categorySpinner.getSelectedItem().toString();
        int hourlyRate = hourlyRateSeekBar.getProgress();

        fetchSearchResults(query, category, hourlyRate);
    }

    private void fetchSearchResults(String searchQuery, String category, int hourlyRate) {
        String endpoint = "search?query=" + searchQuery + "&category=" + category + "&hourlyRate=" + hourlyRate;

        ApiRequest.getInstance().makeGetArrayRequest(requireContext(), endpoint, new ApiRequest.ResponseListener<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    searchResultList.clear(); // Clear previous results
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String name = jsonObject.optString("name", "N/A"); // Use "N/A" as default if the field is missing
                        String skill = jsonObject.optString("skill", "N/A");
                        double hourlyRate = jsonObject.optDouble("hourly_rate", 0.0); // Use 0.0 as default if the field is missing
                        String profilePicture = jsonObject.optString("profile_picture", ""); // Use empty string as default
                        double rating = jsonObject.optDouble("rating", 0.0);
                        String description = jsonObject.optString("description", "No description available.");
                        boolean availabilityStatus = jsonObject.optBoolean("availability_status", false); // Tasker's availability status

                        // Format waiting jobs text based on availability status
                        String waitingJobs = availabilityStatus ? "Available" : "Not Available";


                        searchResultList.add(new SearchResult(name, skill, hourlyRate, profilePicture, rating, description, waitingJobs));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_homepage);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }



}
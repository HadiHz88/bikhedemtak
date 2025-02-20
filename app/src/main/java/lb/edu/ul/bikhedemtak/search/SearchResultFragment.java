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
    private RecyclerView searchResultsRecyclerView;
    private SearchResultAdapter searchResultAdapter;
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
        searchResultsRecyclerView = view.findViewById(R.id.recyclerView_Result);


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
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResultList = new ArrayList<>();
        searchResultAdapter = new SearchResultAdapter(searchResultList);
        searchResultsRecyclerView.setAdapter(searchResultAdapter);




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

        ApiRequest.getInstance().makeGetObjectRequest(requireContext(), endpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    // Check if the API call was successful
                    if (response != null && response.getString("status").equals("success")) {
                        JSONArray categoriesArray = response.getJSONArray("data"); // Extract the "data" array

                    List<String> categories = new ArrayList<>();
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject categoryObject = categoriesArray.getJSONObject(i);
                        String categoryName = categoryObject.getString("category_name");
                        categories.add(categoryName);
                    }

                    // Populate the Spinner
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            categories
                    );
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(spinnerAdapter);
                    } else {
                        Log.e("SearchResultActivity", "API returned success=false");
                    }
                } catch (JSONException e) {
                    Log.e("SearchResultActivity", "JSON Parsing Error: ", e);
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

        ApiRequest.getInstance().makeGetObjectRequest(
                getContext(),
                endpoint,
                new ApiRequest.ResponseListener<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            // Check if the API response is successful
                            if (response != null && response.getString("status").equals("success")) {
                                JSONArray taskersArray = response.getJSONArray("data"); // Extract the "data" array

                                List<SearchResult> newSearchResults = new ArrayList<>();
                                for (int i = 0; i < taskersArray.length(); i++) {
                                    JSONObject taskerObject = taskersArray.getJSONObject(i);

                                    String name = taskerObject.optString("name", "N/A");
                                    String skill = taskerObject.optString("skill", "N/A");
                                    double hourlyRate = taskerObject.optDouble("hourly_rate", 0.0);
                                    String profilePicture = taskerObject.optString("profile_picture", "");
                                    double rating = taskerObject.optDouble("rating", 0.0);
                                    String description = taskerObject.optString("description", "No description available.");
                                    boolean availabilityStatus = taskerObject.optBoolean("availability_status", false);

                                    // Format waiting jobs text based on availability status
                                    String waitingJobs = availabilityStatus ? "Available" : "Not Available";

                                    // Add to the list
                                    newSearchResults.add(new SearchResult(name, skill, hourlyRate, profilePicture, rating, description, waitingJobs));
                                }

                                // Update the adapter with new data
                                searchResultAdapter = new SearchResultAdapter(newSearchResults);
                                searchResultsRecyclerView.setAdapter(searchResultAdapter);
                            } else {
                                Log.e("SearchFragment", "API returned success=false");
                            }
                        } catch (JSONException e) {
                            Log.e("SearchFragment", "JSON Parsing Error: ", e);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        // Handle API error
                        Log.e("SearchFragment", "Error fetching search results: " + error);
                    }
                }
        );
    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_homepage);
//        return navController.navigateUp() || super.onSupportNavigateUp();
//    }



}
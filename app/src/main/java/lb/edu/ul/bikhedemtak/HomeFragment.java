package lb.edu.ul.bikhedemtak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lb.edu.ul.bikhedemtak.Carousel.CarouselAdapter;
import lb.edu.ul.bikhedemtak.Carousel.CarouselItem;
import lb.edu.ul.bikhedemtak.MostBookedFeatured.FeatureService;
import lb.edu.ul.bikhedemtak.MostBookedFeatured.FeatureServiceAdapter;
import lb.edu.ul.bikhedemtak.recommendedsection.SecondSquareAdapter;
import lb.edu.ul.bikhedemtak.recommendedsection.SecondSquareItem;
import lb.edu.ul.bikhedemtak.SquareCategories.SquareAdapter;
import lb.edu.ul.bikhedemtak.SquareCategories.SquareItem;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;

public class HomeFragment extends Fragment {

    private TextView greetingText;

    private TextView viewAllButton1;
    private TextView viewAllButton2;

    private ViewPager2 carouselViewPager;
    private CarouselAdapter carouselAdapter;
    private RecyclerView horizontalSquaresList; // RecyclerView for categories
    private SquareAdapter squareAdapter; // Adapter for categories

    private RecyclerView secondHorizontalSquaresList;
    private SecondSquareAdapter secondSquareAdapter;

    private RecyclerView featureServicesRecyclerView;
    private FeatureServiceAdapter featureServiceAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);




        // Initialize the TextView
        greetingText = view.findViewById(R.id.greeting_text);

        String userName = SharedPrefsManager.getUserName(requireContext());
        // Set the greeting message
        String greetingMessage = "Hello, " + userName + "! 😊";
        greetingText.setText(greetingMessage);

        // Initialize the View all btn 1

        TextView viewAllButton1 = view.findViewById(R.id.viewAll_btn);
        viewAllButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the desired Activity
//                Intent intent = new Intent(getActivity(), AllServicesActivity.class);
//                startActivity(intent);
            }
        });


        // Initialize the View all btn 2

        TextView viewAllButton2 = view.findViewById(R.id.viewAll_btn2);
        viewAllButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the desired Activity
//                Intent intent = new Intent(getActivity(), AllServicesActivity.class);
//                startActivity(intent);
            }
        });



        // Initialize the SearchView
        SearchView searchView = view.findViewById(R.id.search_view);

        // Initialize the Carousel
        carouselViewPager = view.findViewById(R.id.carousel_view_pager);

        // Initialize the second horizontal RecyclerView
        secondHorizontalSquaresList = view.findViewById(R.id.second_horizontal_squares_list);
        secondHorizontalSquaresList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Initialize the first horizontal RecyclerView (for categories)
        horizontalSquaresList = view.findViewById(R.id.horizontal_squares_list);
        horizontalSquaresList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Initialize the Grid RecyclerView for feature services
        featureServicesRecyclerView = view.findViewById(R.id.feature_services_recycler);
        featureServicesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Set up the query text listener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle args = new Bundle();
                args.putString("search", query); // Corrected this line
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_SearchResultFragment_to_navigation_home, args); // Pass args here
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        // Set up the Carousel
        List<CarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(new CarouselItem(R.drawable.carousel));
        carouselItems.add(new CarouselItem(R.drawable.carousel));
        carouselItems.add(new CarouselItem(R.drawable.carousel));

        carouselAdapter = new CarouselAdapter(carouselItems);
        carouselViewPager.setAdapter(carouselAdapter);

        // Fetch categories from the API
        fetchCategories();

        // Fetch recommended profiles from the API
        fetchRecommendedProfiles();

        // Fetch  most  booked services from the API
        fetchMostBookedCategories();

        return view;
    }

    private void fetchCategories() {
        String limit = "5";
        String endpoint = "getFeaturedCategories.php?limit=" + limit;

        ApiRequest.getInstance().makeGetObjectRequest(
                getContext(),
                endpoint,
                new ApiRequest.ResponseListener<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            // Check if the API call was successful
                            if (response != null && response.getString("status").equals("success")) {
                                JSONArray categoriesArray = response.getJSONArray("data"); // Extract the "data" array

                                List<SquareItem> squareItems = new ArrayList<>();
                                for (int i = 0; i < categoriesArray.length(); i++) {
                                    JSONObject categoryObject = categoriesArray.getJSONObject(i);
                                    int categoryId = categoryObject.getInt("category_id");
                                    String categoryName = categoryObject.getString("category_name");

                                    int iconResId = getIconForCategory(categoryName); // Dynamically set icon based on category
                                    squareItems.add(new SquareItem(categoryId, iconResId, categoryName));
                                }

                                // Update the adapter and set it to the RecyclerView (or ListView)
                                squareAdapter = new SquareAdapter(squareItems);
                                horizontalSquaresList.setAdapter(squareAdapter);
                            } else {
                                Log.e("HomepageActivity", "API returned success=false");
                            }
                        } catch (JSONException e) {
                            Log.e("HomepageActivity", "JSON Parsing Error: ", e);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        // Handle API error
                        Log.d("HomepageActivity", "Error: " + error);
                    }
                }
        );
    }


    private int getIconForCategory(String categoryName) {
        switch (categoryName) {
            case "Web Development":
                return R.drawable.ic_web_development;
            case "Mobile Development":
                return R.drawable.ic_mobile_development;
            case "Plumbing":
                return R.drawable.ic_plumbing;
            case "Electrical":
                return R.drawable.ic_electrical;
            case "Carpentry":
                return R.drawable.ic_carpentry;
            case "Painting":
                return R.drawable.ic_painting;
            case "Cleaning":
                return R.drawable.ic_cleaning;
            case "Gardening":
                return R.drawable.ic_gardening;
            case "Cooking":
                return R.drawable.ic_cooking;
            case "Babysitting":
                return R.drawable.ic_babysitting;
            case "Pet Care":
                return R.drawable.ic_pet_care;
            case "Tutoring":
                return R.drawable.ic_tutoring;
            case "Fitness":
                return R.drawable.ic_fitness;
            case "Photography":
                return R.drawable.ic_photography;
            case "Music":
                return R.drawable.ic_music;
            case "Dance":
                return R.drawable.ic_dance;
            case "Event Planning":
                return R.drawable.ic_event_planning;
            default:
        return R.drawable.ic_default;
    }}


    private void fetchRecommendedProfiles() {
        String limit = "5";
        String endpoint = "getRecommendedProfiles.php?limit=" + limit;

        ApiRequest.getInstance().makeGetObjectRequest(
                requireContext(),
                endpoint,
                new ApiRequest.ResponseListener<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            // Check if the API call was successful
                            if (response != null && response.getString("status").equals("success")) {
                                JSONArray profilesArray = response.getJSONArray("data"); // Extract the "data" array

                                List<SecondSquareItem> newItems = new ArrayList<>();
                                for (int i = 0; i < profilesArray.length(); i++) {
                                    JSONObject profileObject = profilesArray.getJSONObject(i);

                                    // Parse JSON data
                                    String name = profileObject.optString("name", "Unknown"); // Tasker's name
                                    String profilePictureUrl = profileObject.optString("profile_picture", ""); // Profile picture URL
                                    double rating = profileObject.optDouble("rating", 0.0); // Tasker's rating
                                    int hourlyRate = profileObject.optInt("hourly_rate", 0); // Tasker's hourly rate
                                    boolean availabilityStatus = profileObject.optBoolean("availability_status", false); // Tasker's availability status

                                    // Format waiting jobs text based on availability status
                                    String waitingJobs = availabilityStatus ? "Available" : "Not Available";

                                    // Add to the list
                                    newItems.add(new SecondSquareItem(profilePictureUrl, name, rating, hourlyRate, waitingJobs));
                                }

                                    secondSquareAdapter = new SecondSquareAdapter(newItems);
                                    secondHorizontalSquaresList.setAdapter(secondSquareAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("HomepageActivity", "JSON parsing error: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        // Handle API error
                        Log.e("HomepageActivity", "API Error: " + error);
                    }
                }
        );
    }


    private void fetchMostBookedCategories() {
        String endpoint = "getMostBookedCategories.php";

        ApiRequest.getInstance().makeGetObjectRequest(
                getContext(),
                endpoint,
                new ApiRequest.ResponseListener<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            // Check if the API call was successful
                            if (response != null && response.getString("status").equals("success")) {
                                JSONArray categoriesArray = response.getJSONArray("data"); // Extract the "data" array

                                List<FeatureService> newServices = new ArrayList<>();
                                for (int i = 0; i < categoriesArray.length(); i++) {
                                    JSONObject categoryObject = categoriesArray.getJSONObject(i);
                                    String categoryName = categoryObject.getString("category_name");

                                    // Add to the list
                                    newServices.add(new FeatureService(categoryName));
                                }

                                // Update the adapter with new data
                                featureServiceAdapter = new FeatureServiceAdapter(newServices);
                                featureServicesRecyclerView.setAdapter(featureServiceAdapter);
                            } else {
                                Log.e("HomepageActivity", "API returned success=false");
                            }
                        } catch (JSONException e) {
                            Log.e("HomepageActivity", "JSON Parsing Error: ", e);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        // Handle API error
                        Log.e("HomepageActivity", "Error fetching most booked categories: " + error);
                    }
                }
        );
    }






}
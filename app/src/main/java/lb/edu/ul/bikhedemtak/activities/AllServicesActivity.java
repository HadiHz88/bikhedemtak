package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.models.Service;
import lb.edu.ul.bikhedemtak.adapters.ServiceAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllServicesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Service> serviceList;
    private ServiceAdapter serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back button
        getSupportActionBar().setDisplayShowHomeEnabled(true); // Show home button


        // Find the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewServices);
        serviceList = new ArrayList<>();

        // Set up GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Set up the adapter
        serviceAdapter = new ServiceAdapter(serviceList);
        recyclerView.setAdapter(serviceAdapter);
        recyclerView.setVisibility(View.VISIBLE);


        fetchAllCategories();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back to the previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchAllCategories() {
        String categoriesEndpoint = "getAllCategories.php"; // API URL

        ApiRequest.getInstance().makeGetObjectRequest(this, categoriesEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    // Assuming the response contains a "categories" array
                    JSONArray categoriesArray = response.getJSONArray("data");
                    serviceList.clear();
                    // Loop through each category in the response
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject category = categoriesArray.getJSONObject(i);
                        int id = category.getInt("category_id");  // Get the category ID
                        String name = category.getString("category_name");  // Get the category name
                        serviceList.add(new Service(id, name));  // Add to the service list
                    }
                    // Notify adapter on UI thread
                    runOnUiThread(() -> serviceAdapter.notifyDataSetChanged());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("FetchCategories", "Error: " + error);
            }
        });
    }

}
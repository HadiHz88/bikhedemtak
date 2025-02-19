package lb.edu.ul.bikhedemtak.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
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

        // Find the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewServices);
        serviceList = new ArrayList<>();

        // Set up GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Set up the adapter
        serviceAdapter = new ServiceAdapter(serviceList);
        recyclerView.setAdapter(serviceAdapter);

        fetchAllCategories();
    }
    private void fetchAllCategories() {
        String categoriesEndpoint = "getAllCategories.php"; // API URL

        ApiRequest.getInstance().makeGetObjectRequest(this, categoriesEndpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    // Assuming the response contains a "categories" array
                    JSONArray categoriesArray = response.getJSONArray("categories");
                    List<Service> serviceList = new ArrayList<>();

                    // Loop through each category in the response
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject category = categoriesArray.getJSONObject(i);
                        int id = category.getInt("id");  // Get the category ID
                        String name = category.getString("name");  // Get the category name
                        serviceList.add(new Service(id, name));  // Add to the service list
                    }

                    // Set the RecyclerView with the ServiceAdapter
                    ServiceAdapter serviceAdapter = new ServiceAdapter(serviceList);
                    recyclerView.setAdapter(serviceAdapter);

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
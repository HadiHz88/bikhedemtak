package lb.edu.ul.bikhedemtak;

import android.content.Context;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ApiRequest {
    private static final String BASE_URL = "http://10.0.2.2:80/bikhedemtak_mobile_api/api/";
    private static final int TIMEOUT_MS = 30000; // 30 seconds timeout
    private static final int MAX_RETRIES = 1;
    private static final float BACKOFF_MULTIPLIER = 1.0f;

    private static RequestQueue requestQueue;
    private static ApiRequest instance;

    private ApiRequest() {}

    public static synchronized ApiRequest getInstance() {
        if (instance == null) {
            instance = new ApiRequest();
        }
        return instance;
    }

    private synchronized RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public void makeGetArrayRequest(Context context, String endpoint, final ResponseListener<JSONArray> listener) {
        String url = BASE_URL + endpoint;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> listener.onSuccess(response),
                error -> handleError(error, listener));
        configureRequest(request);
        getRequestQueue(context).add(request);
    }

    public void makeGetObjectRequest(Context context, String endpoint, final ResponseListener<JSONObject> listener) {
        String url = BASE_URL + endpoint;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> listener.onSuccess(response),
                error -> handleError(error, listener));
        configureRequest(request);
        getRequestQueue(context).add(request);
    }

    public void makePostRequest(Context context, String endpoint, JSONObject params, final ResponseListener<JSONObject> listener) {
        String url = BASE_URL + endpoint;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                response -> listener.onSuccess(response),
                error -> handleError(error, listener)) {
            @Override
            public Map<String, String> getHeaders() {
                return createHeaders();
            }
        };
        configureRequest(request);
        getRequestQueue(context).add(request);
    }

    private void configureRequest(Request<?> request) {
        RetryPolicy policy = new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULTIPLIER);
        request.setRetryPolicy(policy);
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private void handleError(VolleyError error, ResponseListener<?> listener) {
        String errorMessage;
        if (error instanceof NetworkError) {
            errorMessage = "Network error occurred. Please check your connection.";
        } else if (error instanceof ServerError) {
            errorMessage = "Server error occurred. Please try again later.";
        } else if (error instanceof AuthFailureError) {
            errorMessage = "Authentication failed. Please login again.";
        } else if (error instanceof TimeoutError) {
            errorMessage = "Request timed out. Please try again.";
        } else {
            errorMessage = "An unexpected error occurred: " + (error.getMessage() != null ? error.getMessage() : "Unknown error");
        }
        listener.onFailure(errorMessage);
    }

    public interface ResponseListener<T> {
        void onSuccess(T response);
        void onFailure(String error);
    }

    public void cancelAllRequests() {
        if (requestQueue != null) {
            requestQueue.cancelAll(request -> true);
        }
    }
}
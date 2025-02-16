package lb.edu.ul.bikhedemtak.api;

import android.content.Context;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiRequest class handles API requests using the Volley library.
 * It supports GET and POST requests and implements a singleton pattern.
 */
public class ApiRequest {
    private static final String BASE_URL = "http://10.0.2.2:80/bikhedemtak_mobile_api/api/";
    private static final int TIMEOUT_MS = 30000; // 30 seconds timeout
    private static final int MAX_RETRIES = 1;
    private static final float BACKOFF_MULTIPLIER = 1.0f;

    private static RequestQueue requestQueue;
    private static ApiRequest instance;

    // Private constructor for singleton pattern
    private ApiRequest() {
    }

    /**
     * @return the singleton instance of ApiRequest.
     */
    public static synchronized ApiRequest getInstance() {
        if (instance == null) {
            instance = new ApiRequest();
        }
        return instance;
    }

    /**
     * Initializes the RequestQueue if it is null.
     *
     * @param context the application context.
     * @return the initialized RequestQueue.
     */
    private synchronized RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Makes a GET request that returns a JSONArray.
     *
     * @param context  the application context.
     * @param endpoint the API endpoint.
     * @param listener the response listener.
     */
    public void makeGetArrayRequest(Context context, String endpoint, final ResponseListener<JSONArray> listener) {
        String url = BASE_URL + endpoint;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> listener.onSuccess(response),
                error -> handleError(error, listener));

        configureRequest(request);
        getRequestQueue(context).add(request);
    }

    /**
     * Makes a GET request that returns a JSONObject.
     *
     * @param context  the application context.
     * @param endpoint the API endpoint.
     * @param listener the response listener.
     */
    public void makeGetObjectRequest(Context context, String endpoint, final ResponseListener<JSONObject> listener) {
        String url = BASE_URL + endpoint;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> listener.onSuccess(response),
                error -> handleError(error, listener));

        configureRequest(request);
        getRequestQueue(context).add(request);
    }


    /**
     * Makes a POST request.
     *
     * @param context  the application context.
     * @param endpoint the API endpoint.
     * @param params   the request parameters.
     * @param listener the response listener.
     */
    public void makePostRequest(Context context, String endpoint, JSONObject params,
                                final ResponseListener<JSONObject> listener) {
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

    /**
     * Configures the request timeout and retry policy.
     *
     * @param request the request to configure.
     */
    private void configureRequest(Request<?> request) {
        RetryPolicy policy = new DefaultRetryPolicy(
                TIMEOUT_MS,
                MAX_RETRIES,
                BACKOFF_MULTIPLIER
        );
        request.setRetryPolicy(policy);
    }

    /**
     * Creates headers for requests.
     *
     * @return a map of headers.
     */
    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        // Add any other headers as needed (e.g., authentication tokens)
        return headers;
    }

    /**
     * Handles various types of errors.
     *
     * @param error    the VolleyError.
     * @param listener the response listener.
     */
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
            errorMessage = "An unexpected error occurred: " +
                    (error.getMessage() != null ? error.getMessage() : "Unknown error");
        }

        listener.onFailure(errorMessage);
    }

    /**
     * ResponseListener interface for handling API responses.
     *
     * @param <T> the type of the response.
     */
    public interface ResponseListener<T> {
        void onSuccess(T response);

        void onFailure(String error);
    }

    /**
     * Cancels all pending requests.
     */
    public void cancelAllRequests() {
        if (requestQueue != null) {
            requestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }
}

/*
// Making a GET request that returns a JSONArray
ApiRequest.getInstance().makeGetArrayRequest(context, "endpoint", new ApiRequest.ResponseListener<JSONArray>() {
    @Override
    public void onSuccess(JSONArray response) {
        // Handle success
    }

    @Override
    public void onFailure(String error) {
        // Handle error
    }
});

// Making a POST request
JSONObject params = new JSONObject();
params.put("key", "value");

ApiRequest.getInstance().makePostRequest(context, "endpoint", params, new ApiRequest.ResponseListener<JSONObject>() {
    @Override
    public void onSuccess(JSONObject response) {
        // Handle success
    }

    @Override
    public void onFailure(String error) {
        // Handle error
    }
});
*/
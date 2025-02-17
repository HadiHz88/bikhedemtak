package lb.edu.ul.bikhedemtak;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONObject;

public class SharedPrefsManager {
    private static final String PREF_NAME = "BikhedmetakPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_IS_TASKER = "is_tasker";
    private static final String KEY_PASSWORD = "password"; // Store default password

    // Save default user data for testing
    public static void saveDefaultUserData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USER_ID, "1");
        editor.putString(KEY_NAME, "John Doe");
        editor.putString(KEY_EMAIL, "john.doe@example.com");
        editor.putString(KEY_TOKEN, "dummy_token");
        editor.putBoolean(KEY_IS_TASKER, false);
        editor.putString(KEY_PASSWORD, "123456"); // Default password for testing
        editor.apply();
    }

    public static void saveUserData(Context context, JSONObject userData) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USER_ID, userData.optString("user_id"));
        editor.putString(KEY_NAME, userData.optString("name"));
        editor.putString(KEY_EMAIL, userData.optString("email"));
        editor.putString(KEY_TOKEN, userData.optString("token"));
        editor.putBoolean(KEY_IS_TASKER, userData.optBoolean("is_tasker"));
        editor.apply();
    }

    public static void updatePassword(Context context, String newPassword) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PASSWORD, newPassword);
        editor.apply();
    }

    public static String getPassword(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_PASSWORD, "123456"); // Default password
    }

    public static void clearUserData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_TOKEN, null);
    }

    public static boolean isLoggedIn(Context context) {
        return getToken(context) != null;
    }

    public static String getUserName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_NAME, "User");
    }

    public static String getUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_EMAIL, "user@example.com");
    }
}

package lb.edu.ul.bikhedemtak.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPrefsManager {
    private static final String PREF_NAME = "BikhedmetakPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_IS_TASKER = "is_tasker";
    private static final String KEY_PASSWORD = "password";

    public static void saveUserData(Context context, JSONObject userData) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(KEY_USER_ID, userData.optInt("user_id")); // Save as int
        editor.putString(KEY_NAME, userData.optString("name"));
        editor.putString(KEY_EMAIL, userData.optString("email"));
        editor.putString(KEY_TOKEN, userData.optString("token"));
        editor.putBoolean(KEY_IS_TASKER, userData.optBoolean("is_tasker"));
        editor.putString(KEY_PASSWORD, userData.optString("password"));

        editor.apply();
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

    public static int getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_USER_ID, 0);
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

    public static void saveDefaultUserData(Context context) {
        JSONObject userData = new JSONObject();
        try {
            userData.put("user_id", 1);
            userData.put("name", "User");
            userData.put("email", "user@example.com");
            userData.put("password", "123456");
            userData.put("is_tasker", false);
            userData.put("token", "123456");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_EMAIL, "user@gmail.com");
    }
}
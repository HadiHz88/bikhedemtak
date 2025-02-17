package lb.edu.ul.bikhedemtak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordFragment extends Fragment {
    private EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    private Button btnChangePassword, btnBack;
    private String userId = "1"; // Replace with actual user ID from session

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        etCurrentPassword = view.findViewById(R.id.etCurrentPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        btnChangePassword.setOnClickListener(v -> {
            String currentPassword = etCurrentPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            // Get the stored password from SharedPreferences
            String storedPassword = SharedPrefsManager.getPassword(getContext());

            if (!currentPassword.equals(storedPassword)) {
                Toast.makeText(getContext(), "Current password is incorrect", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPassword.equals(confirmPassword)) {
                changePassword(newPassword);
            } else {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void changePassword(String newPassword) {
        JSONObject params = new JSONObject();
        try {
            params.put("user_id", userId);
            params.put("new_password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.getInstance().makePostRequest(getContext(), "change_password.php", params, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                // Update password in SharedPreferences
                SharedPrefsManager.updatePassword(getContext(), newPassword);
                Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), "Failed to change password: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package lb.edu.ul.bikhedemtak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import org.json.JSONException;
import org.json.JSONObject;
import lb.edu.ul.bikhedemtak.ApiRequest;
import lb.edu.ul.bikhedemtak.ApiRequest;

public class BecomeTaskerFragment extends Fragment {
    private EditText etProfession;
    private Spinner spAvailability;
    private CheckBox cbPolicy;
    private Button btnSubmit;
    private String userId = "1"; // Replace with actual user ID from session

    public BecomeTaskerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_become_tasker, container, false);

        etProfession = view.findViewById(R.id.etProfession);
        spAvailability = view.findViewById(R.id.spAvailability);
        cbPolicy = view.findViewById(R.id.cbPolicy);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String profession = etProfession.getText().toString();
            String availability = spAvailability.getSelectedItem().toString();
            boolean policyAccepted = cbPolicy.isChecked();

            if (policyAccepted) {
                becomeTasker(profession, availability);
            } else {
                Toast.makeText(getContext(), "Please accept the policy", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void becomeTasker(String profession, String availability) {
        JSONObject params = new JSONObject();
        try {
            params.put("user_id", userId);
            params.put("profession", profession);
            params.put("availability", availability);
            params.put("policy_accepted", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.getInstance().makePostRequest(getContext(), "become_tasker.php", params, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Toast.makeText(getContext(), "You are now a tasker", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), "Failed to become a tasker: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
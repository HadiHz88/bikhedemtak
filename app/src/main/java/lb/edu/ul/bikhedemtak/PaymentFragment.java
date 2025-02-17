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
import lb.edu.ul.bikhedemtak.ApiRequest;

public class PaymentFragment extends Fragment {
    private EditText etCardNumber, etExpiryDate, etCvv;
    private Button btnSavePayment , btnBack;
    private String userId = "1"; // Replace with actual user ID from session

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        etCardNumber = view.findViewById(R.id.etCardNumber);
        etExpiryDate = view.findViewById(R.id.etExpiryDate);
        etCvv = view.findViewById(R.id.etCvv);
        btnSavePayment = view.findViewById(R.id.btnSavePayment);
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        btnSavePayment.setOnClickListener(v -> {
            String cardNumber = etCardNumber.getText().toString();
            String expiryDate = etExpiryDate.getText().toString();
            String cvv = etCvv.getText().toString();

            savePaymentDetails(cardNumber, expiryDate, cvv);
        });

        return view;
    }

    private void savePaymentDetails(String cardNumber, String expiryDate, String cvv) {
        JSONObject params = new JSONObject();
        try {
            params.put("user_id", userId);
            params.put("card_number", cardNumber);
            params.put("expiry_date", expiryDate);
            params.put("cvv", cvv);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.getInstance().makePostRequest(getContext(), "save_payment.php", params, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Toast.makeText(getContext(), "Payment details saved successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), "Failed to save payment details: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
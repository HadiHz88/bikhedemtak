package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.adapters.PromoAdapter;
import lb.edu.ul.bikhedemtak.models.Promo;

public class PromosFragment extends Fragment {
    private Button btnBack, btnApplyPromo;
    private RecyclerView recyclerPromos;
    private PromoAdapter promoAdapter;
    private String selectedPromoCode = null;

    public PromosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promos, container, false);

        btnBack = view.findViewById(R.id.btnBack);
        btnApplyPromo = view.findViewById(R.id.btnApplyPromo);
        recyclerPromos = view.findViewById(R.id.recyclerPromos);

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Initialize RecyclerView
        recyclerPromos.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Promo> promoList = getPromos();
        promoAdapter = new PromoAdapter(promoList, promoCode -> {
            selectedPromoCode = promoCode;
            btnApplyPromo.setEnabled(true);
        });
        recyclerPromos.setAdapter(promoAdapter);

        // Handle Apply Promo Button Click
        btnApplyPromo.setOnClickListener(v -> {
            if (selectedPromoCode != null) {
                Toast.makeText(getContext(), "Promo " + selectedPromoCode + " applied successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please select a promo", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Function to get a list of promos
    private List<Promo> getPromos() {
        List<Promo> promos = new ArrayList<>();
        promos.add(new Promo("DISCOUNT10", "Get 10% off on your next ride"));
        promos.add(new Promo("FREERIDE", "Enjoy one free ride"));
        promos.add(new Promo("SAVE50", "Save 50% on your next trip"));
        return promos;
    }
}

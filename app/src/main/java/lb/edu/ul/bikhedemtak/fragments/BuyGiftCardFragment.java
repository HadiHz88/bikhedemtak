package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import lb.edu.ul.bikhedemtak.R;

public class BuyGiftCardFragment extends Fragment {
    private Spinner spGiftCards;
    private Button btnBuyGiftCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy_gift_card, container, false);

        // Initialize views
        spGiftCards = view.findViewById(R.id.spGiftCards);
        btnBuyGiftCard = view.findViewById(R.id.btnBuyGiftCard);

        // Back button
        view.findViewById(R.id.btnBack).setOnClickListener(v -> {
            navigateToFragment(new UserProfileFragment());
        });

        // Populate spinner with gift cards
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.gift_cards_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGiftCards.setAdapter(adapter);

        // Handle buy gift card button click
        btnBuyGiftCard.setOnClickListener(v -> {
            String selectedGiftCard = spGiftCards.getSelectedItem().toString();
            Toast.makeText(getContext(), "Purchased: " + selectedGiftCard, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void navigateToFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
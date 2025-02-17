package lb.edu.ul.bikhedemtak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class SupportFragment extends Fragment {

    public SupportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        Button btnContactSupport = view.findViewById(R.id.btnContactSupport);
        btnContactSupport.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Support contacted", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
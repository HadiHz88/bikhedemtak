package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.adapters.GetStartedSlideAdapter;
import lb.edu.ul.bikhedemtak.models.GetStartedSlide;

public class GetStartedActivity extends AppCompatActivity {

    // UI Components
    private ViewPager2 viewPager;
    private Button btnNext, btnGetStarted;
    // Adapter for the ViewPager
    private GetStartedSlideAdapter getStartedSlideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_started);


        // Initialize UI components
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        Button btnSkip = findViewById(R.id.btnSkip);
        DotsIndicator dotsIndicator = findViewById(R.id.dots_indicator);

        // List of slides to be shown in the ViewPager
        List<GetStartedSlide> getStartedSlides = new ArrayList<>();

        // Slides to be shown in the ViewPager with their respective images, titles, and descriptions
        getStartedSlides.add(new GetStartedSlide(R.drawable.fixing,
                "Discover the Service You Need",
                "Browse through a variety of services and find the perfect match for what you're looking for."));

        getStartedSlides.add(new GetStartedSlide(R.drawable.electrician,
                "Offer Your Expertise",
                "Become a service provider and start offering your skills to customers in your local area.s"));

        getStartedSlides.add(new GetStartedSlide(R.drawable.fixing,
                "Book on Your Terms",
                "Choose the time that works best for you and book your service provider with ease, all based on your availability."));

        getStartedSlides.add(new GetStartedSlide(R.drawable.map,
                "Quick Services, Right Nearby",
                "Get the job done fast with local service providers who are ready to assist you in your area, ensuring quick responses and timely solutions."));

        getStartedSlides.add(new GetStartedSlide(R.drawable.man_holding_phone,
                "Get Started Now",
                "Create an account or log in to access local service providers and start booking services instantly."));

        // Set the adapter for the ViewPager to display the slides
        getStartedSlideAdapter = new GetStartedSlideAdapter(getStartedSlides);
        viewPager.setAdapter(getStartedSlideAdapter);

        // Set the dots indicator to the ViewPager
        dotsIndicator.attachTo(viewPager);

        // Register a callback to be invoked when the page changes
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            // This method is called when the page changes
            public void onPageSelected(int position) {
                // Update the visibility of the buttons based on the current page position
                updateButtonsVisibility(position);
            }
        });

        // Set a click listener for the "Next" button to go to the next page
        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < getStartedSlides.size() - 1) {
                // Go to the next page
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        // Set a click listener for the "Get Started" button to go to the Authentication Page
        btnGetStarted.setOnClickListener(v -> {
            // Toast
            // Toast.makeText(this, "Get Started", Toast.LENGTH_SHORT).show();

            // TODO: Go to the Authentication Page
            // Go to the Authentication Page
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        });

        // Set a click listener for the "Skip" button to go to the HomePage as a Guest
        btnSkip.setOnClickListener(v -> {
            // temp Toast
            Toast.makeText(this, "Skip", Toast.LENGTH_SHORT).show();

            // TODO: Go to the HomePage as a guest
        });
    }

    // Update the visibility of the buttons based on the current page position
    private void updateButtonsVisibility(int position) {
        btnNext.setVisibility(position < getStartedSlideAdapter.getItemCount() - 1 ? Button.VISIBLE : Button.GONE);
        btnGetStarted.setVisibility(position == getStartedSlideAdapter.getItemCount() - 1 ? Button.VISIBLE : Button.GONE);
    }
}
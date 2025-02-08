package lb.edu.ul.bikhedemtak.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import java.util.ArrayList;
import java.util.List;
import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.adapters.GetStartedSlideAdapter;
import lb.edu.ul.bikhedemtak.models.GetStartedSlide;

/**
 * Activity that handles the onboarding/introduction screens shown to users
 * when they first launch the application. Uses ViewPager2 for smooth sliding
 * between screens and DotsIndicator for navigation visualization.
 */
public class GetStartedActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button btnNext;
    private Button btnGetStarted;
    private Button btnSkip;
    private GetStartedSlideAdapter getStartedSlideAdapter;
    private DotsIndicator dotsIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_started);

        initializeViews();
        setupSlides();
        setupButtonListeners();
    }

    /**
     * Initializes all UI components used in the activity
     */
    private void initializeViews() {
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        btnSkip = findViewById(R.id.btnSkip);
        dotsIndicator = findViewById(R.id.dots_indicator);
    }

    /**
     * Sets up the onboarding slides with their content and configures the ViewPager
     */
    private void setupSlides() {
        List<GetStartedSlide> getStartedSlides = createSlidesList();

        // Initialize and set adapter
        getStartedSlideAdapter = new GetStartedSlideAdapter(getStartedSlides);
        viewPager.setAdapter(getStartedSlideAdapter);

        // Attach dots indicator to ViewPager
        dotsIndicator.attachTo(viewPager);

        // Configure page change callback
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateButtonsVisibility(position);
            }
        });
    }

    /**
     * Creates and returns the list of onboarding slides with their content
     * @return List of GetStartedSlide objects
     */
    private List<GetStartedSlide> createSlidesList() {
        List<GetStartedSlide> slides = new ArrayList<>();

        slides.add(new GetStartedSlide(
                R.drawable.customer_service,
                "Discover the Service You Need",
                "Browse through a variety of services and find the perfect match for what you're looking for."
        ));

        slides.add(new GetStartedSlide(
                R.drawable.best_customer_experience,
                "Offer Your Expertise",
                "Become a service provider and start offering your skills to customers in your local area."
        ));

        slides.add(new GetStartedSlide(
                R.drawable.handshake,
                "Book on Your Terms",
                "Choose the time that works best for you and book your service provider with ease, all based on your availability."
        ));

        slides.add(new GetStartedSlide(
                R.drawable.map,
                "Quick Services, Right Nearby",
                "Get the job done fast with local service providers who are ready to assist you in your area, ensuring quick responses and timely solutions."
        ));

        slides.add(new GetStartedSlide(
                R.drawable.handshake,
                "Get Started Now",
                "Create an account or log in to access local service providers and start booking services instantly."
        ));

        return slides;
    }

    /**
     * Sets up click listeners for all buttons in the activity
     */
    private void setupButtonListeners() {
        // Next button - moves to next slide
        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < getStartedSlideAdapter.getItemCount() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        // Get Started button - launches authentication flow
        btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        });

        // Skip button - launches guest flow
        findViewById(R.id.btnSkip).setOnClickListener(v -> {
            Intent skipping = new Intent(this, TaskerProfileActivity.class);
            startActivity(skipping);
        });
    }

    /**
     * Updates the visibility of navigation buttons based on current slide position
     * @param position Current slide position in the ViewPager
     */
    private void updateButtonsVisibility(int position) {
        boolean isLastSlide = position == getStartedSlideAdapter.getItemCount() - 1;
        btnNext.setVisibility(isLastSlide ? Button.GONE : Button.VISIBLE);
        btnGetStarted.setVisibility(isLastSlide ? Button.VISIBLE : Button.GONE);
    }
}
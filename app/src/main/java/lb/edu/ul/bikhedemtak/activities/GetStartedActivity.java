package lb.edu.ul.bikhedemtak.activities;

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

    private ViewPager2 viewPager;
    private Button btnNext, btnGetStarted;
    private GetStartedSlideAdapter getStartedSlideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_started);


        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnGetStarted = findViewById(R.id.btnGetStarted);

        DotsIndicator dotsIndicator = findViewById(R.id.dots_indicator);

        List<GetStartedSlide> getStartedSlides = new ArrayList<>();

        getStartedSlides.add(new GetStartedSlide(R.drawable.ic_launcher_foreground, "Discover the Service You Need"));
        getStartedSlides.add(new GetStartedSlide(R.drawable.ic_launcher_foreground, "Offer Your Expertise"));
        getStartedSlides.add(new GetStartedSlide(R.drawable.ic_launcher_foreground, "Book on Your Terms"));
        getStartedSlides.add(new GetStartedSlide(R.drawable.ic_launcher_foreground, "Quick Services, Right Nearby"));
        getStartedSlides.add(new GetStartedSlide(R.drawable.ic_launcher_foreground, "Get Started Now"));

        getStartedSlideAdapter = new GetStartedSlideAdapter(getStartedSlides);
        viewPager.setAdapter(getStartedSlideAdapter);

        dotsIndicator.setViewPager2(viewPager);

        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < getStartedSlides.size() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        btnGetStarted.setOnClickListener(v -> {
            // Toast
            Toast.makeText(this, "Get Started", Toast.LENGTH_SHORT).show();
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateButtonsVisibility(position);
            }
        });
    }

    private void updateButtonsVisibility(int position) {
        btnNext.setVisibility(position < getStartedSlideAdapter.getItemCount() - 1 ? Button.VISIBLE : Button.GONE);
        btnGetStarted.setVisibility(position == getStartedSlideAdapter.getItemCount() - 1 ? Button.VISIBLE : Button.GONE);
    }
}
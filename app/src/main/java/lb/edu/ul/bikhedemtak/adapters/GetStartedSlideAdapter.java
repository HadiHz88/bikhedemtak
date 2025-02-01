package lb.edu.ul.bikhedemtak.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.models.GetStartedSlide;

/**
 * Adapter for managing and displaying onboarding slides in a ViewPager2.
 * Extends RecyclerView.Adapter to provide smooth scrolling and efficient
 * view recycling for the onboarding experience.
 */
public class GetStartedSlideAdapter extends RecyclerView.Adapter<GetStartedSlideAdapter.GetStartedSlideViewHolder> {

    private final List<GetStartedSlide> getStartedSlides;

    /**
     * Constructs a new adapter with the provided list of slides
     * @param getStartedSlides List of slide data to be displayed
     */
    public GetStartedSlideAdapter(List<GetStartedSlide> getStartedSlides) {
        this.getStartedSlides = getStartedSlides;
    }

    /**
     * ViewHolder class that holds references to the views for each slide.
     * Caches these references to avoid repeated findViewById calls.
     */
    static class GetStartedSlideViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageSlide;
        final TextView titleSlide;
        final TextView descSlide;

        /**
         * Initializes ViewHolder with references to all required views
         * @param itemView The root view of the slide layout
         */
        public GetStartedSlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSlide = itemView.findViewById(R.id.imageSlide);
            titleSlide = itemView.findViewById(R.id.titleSlide);
            descSlide = itemView.findViewById(R.id.descSlide);
        }

        /**
         * Binds slide data to the views
         * @param slide The slide data to be displayed
         */
        void bind(GetStartedSlide slide) {
            imageSlide.setImageResource(slide.getImageResId());
            titleSlide.setText(slide.getText());
            descSlide.setText(slide.getDescription());
        }
    }

    @NonNull
    @Override
    public GetStartedSlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.get_started_slide, parent, false);
        return new GetStartedSlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetStartedSlideViewHolder holder, int position) {
        GetStartedSlide currentSlide = getStartedSlides.get(position);
        holder.bind(currentSlide);
    }

    @Override
    public int getItemCount() {
        return getStartedSlides.size();
    }
}
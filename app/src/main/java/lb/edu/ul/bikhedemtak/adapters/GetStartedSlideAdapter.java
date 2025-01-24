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


// Adapter for the ViewPager to display the slides in the GetStartedActivity
public class GetStartedSlideAdapter extends RecyclerView.Adapter<GetStartedSlideAdapter.GetStartedSlideViewHolder> {

    // List of slides to be shown in the ViewPager
    private final List<GetStartedSlide> getStartedSlides;

    // Constructor
    public GetStartedSlideAdapter(List<GetStartedSlide> getStartedSlides) {
        this.getStartedSlides = getStartedSlides;
    }

    // ViewHolder class for each slide
    static class GetStartedSlideViewHolder extends RecyclerView.ViewHolder {
        // UI components
        ImageView imageSlide;
        TextView descSlide, titleSlide;

        // Constructor
        public GetStartedSlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSlide = itemView.findViewById(R.id.imageSlide);
            titleSlide = itemView.findViewById(R.id.titleSlide);
            descSlide = itemView.findViewById(R.id.descSlide);
        }
    }

    @NonNull
    @Override
    // Create a new ViewHolder for each slide
    public GetStartedSlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_started_slide, parent, false);
        return new GetStartedSlideViewHolder(view);
    }


    @Override
    // Bind data to the ViewHolder for each slide
    public void onBindViewHolder(@NonNull GetStartedSlideViewHolder holder, int position) {
        GetStartedSlide getStartedSlide = getStartedSlides.get(position);
        holder.imageSlide.setImageResource(getStartedSlide.getImageResId());
        holder.titleSlide.setText(getStartedSlide.getText());
        holder.descSlide.setText(getStartedSlide.getDescription());
    }

    @Override
    // Return the total number of slides
    public int getItemCount() {
        return getStartedSlides.size();
    }
}

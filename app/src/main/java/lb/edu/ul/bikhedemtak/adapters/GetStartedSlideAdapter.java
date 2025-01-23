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

public class GetStartedSlideAdapter extends RecyclerView.Adapter<GetStartedSlideAdapter.GetStartedSlideViewHolder> {

    private List<GetStartedSlide> getStartedSlides;

    public GetStartedSlideAdapter(List<GetStartedSlide> getStartedSlides) {
        this.getStartedSlides = getStartedSlides;
    }

    static class GetStartedSlideViewHolder extends RecyclerView.ViewHolder {
        ImageView imageSlide;
        TextView textSlide;

        public GetStartedSlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSlide = itemView.findViewById(R.id.imageSlide);
            textSlide = itemView.findViewById(R.id.textSlide);
        }
    }

    @NonNull
    @Override
    public GetStartedSlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_started_slide, parent, false);
        return new GetStartedSlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetStartedSlideViewHolder holder, int position) {
        GetStartedSlide getStartedSlide = getStartedSlides.get(position);
        holder.imageSlide.setImageResource(getStartedSlide.getImageResId());
        holder.textSlide.setText(getStartedSlide.getText());
    }

    @Override
    public int getItemCount() {
        return getStartedSlides.size();
    }
}

package lb.edu.ul.bikhedemtak.Carousel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<CarouselItem> carouselItems;

    public CarouselAdapter(List<CarouselItem> carouselItems) {
        this.carouselItems = carouselItems;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        CarouselItem item = carouselItems.get(position);
        holder.carouselImage.setImageResource(item.getImageResId());

    }

    @Override
    public int getItemCount() {
        return carouselItems.size();
    }

    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView carouselImage;


        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            carouselImage = itemView.findViewById(R.id.carousel_image);

        }
    }
}
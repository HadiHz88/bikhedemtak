package lb.edu.ul.bikhedemtak.models;

public class GetStartedSlide {
    private int imageResId;
    private String text;

    public GetStartedSlide(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}

package lb.edu.ul.bikhedemtak.models;

// Class representing a slide in the GetStartedActivity
public class GetStartedSlide {
    // Fields
    private final int imageResId;
    private final String title;
    private final String description;

    // Constructor
    public GetStartedSlide(int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }

    // Getters
    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

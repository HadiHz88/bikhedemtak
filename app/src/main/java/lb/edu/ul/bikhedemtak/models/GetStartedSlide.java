package lb.edu.ul.bikhedemtak.models;

import androidx.annotation.DrawableRes;

/**
 * Model class representing a single slide in the onboarding/get started flow.
 * Each slide contains an image resource, title, and description to be displayed
 * to the user during the initial app launch experience.
 */
public class GetStartedSlide {

    @DrawableRes
    private final int imageResId;
    private final String title;
    private final String description;

    /**
     * Creates a new slide with the specified image, title, and description.
     *
     * @param imageResId The resource ID of the image to display for this slide
     * @param title The title text to display for this slide
     * @param description The detailed description text for this slide
     */
    public GetStartedSlide(@DrawableRes int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }

    /**
     * Gets the resource ID of the slide's image
     * @return The drawable resource ID for the slide's image
     */
    @DrawableRes
    public int getImageResId() {
        return imageResId;
    }

    /**
     * Gets the slide's title text
     * @return The title of the slide
     */
    public String getText() {
        return title;
    }

    /**
     * Gets the slide's description text
     * @return The detailed description of the slide
     */
    public String getDescription() {
        return description;
    }
}
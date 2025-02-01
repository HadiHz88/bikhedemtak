package lb.edu.ul.bikhedemtak.models;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

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

    /**
     * Returns a string representation of the slide for debugging purposes
     * @return A string containing the slide's title and description
     */
    @NonNull
    @Override
    public String toString() {
        return "GetStartedSlide{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Checks if this slide is equal to another object
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GetStartedSlide that = (GetStartedSlide) obj;
        return imageResId == that.imageResId &&
                title.equals(that.title) &&
                description.equals(that.description);
    }

    /**
     * Generates a hash code for this slide
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        int result = imageResId;
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
package lb.edu.ul.bikhedemtak.models;

import java.io.Serializable;

/**
 * Represents a review with details such as reviewer's name, content, date, rating, and profile image URL.
 */
public class Review implements Serializable {
    private String reviewerName;
    private String reviewContent;
    private String date;
    private float rating;
    private String profileImageUrl;

    /**
     * Constructs a new Review with the specified details.
     *
     * @param reviewerName    the name of the reviewer
     * @param reviewContent   the content of the review
     * @param date            the date of the review
     * @param rating          the rating given by the reviewer
     * @param profileImageUrl the URL of the reviewer's profile image
     */
    public Review(String reviewerName, String reviewContent, String date, float rating, String profileImageUrl) {
        this.reviewerName = reviewerName;
        this.reviewContent = reviewContent;
        this.date = date;
        this.rating = rating;
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * Gets the date of the review.
     *
     * @return the date of the review
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the review.
     *
     * @param date the new date of the review
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the URL of the reviewer's profile image.
     *
     * @return the URL of the reviewer's profile image
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * Sets the URL of the reviewer's profile image.
     *
     * @param profileImageUrl the new URL of the reviewer's profile image
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * Gets the rating given by the reviewer.
     *
     * @return the rating given by the reviewer
     */
    public float getRating() {
        return rating;
    }

    /**
     * Sets the rating given by the reviewer.
     *
     * @param rating the new rating given by the reviewer
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Gets the content of the review.
     *
     * @return the content of the review
     */
    public String getReviewContent() {
        return reviewContent;
    }

    /**
     * Sets the content of the review.
     *
     * @param reviewContent the new content of the review
     */
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    /**
     * Gets the name of the reviewer.
     *
     * @return the name of the reviewer
     */
    public String getReviewerName() {
        return reviewerName;
    }

    /**
     * Sets the name of the reviewer.
     *
     * @param reviewerName the new name of the reviewer
     */
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
}
package lb.edu.ul.bikhedemtak.models;

import java.util.Date;

public class Review {
    private String reviewerName;
    private String reviewContent;
    private String date;
    private float rating;
    private String profileImageUrl;

    public Review(String reviewerName, String reviewContent, String date, float rating, String profileImageUrl) {
        this.reviewerName = reviewerName;
        this.reviewContent = reviewContent;
        this.date = date;
        this.rating = rating;
        this.profileImageUrl = profileImageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
}

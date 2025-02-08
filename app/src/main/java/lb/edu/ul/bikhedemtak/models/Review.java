package lb.edu.ul.bikhedemtak.models;

public class Review {
    private int id;
    private int userId;
    private int taskerId;
    private String review;
    private float rating;

    public Review() {
    }

    public Review(int id, int userId, int taskerId, String review, float rating) {
        this.id = id;
        this.userId = userId;
        this.taskerId = taskerId;
        this.review = review;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getTaskerId() {
        return taskerId;
    }

    public void setTaskerId(int taskerId) {
        this.taskerId = taskerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

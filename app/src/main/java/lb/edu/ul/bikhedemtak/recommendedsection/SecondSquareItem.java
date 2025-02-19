package lb.edu.ul.bikhedemtak.recommendedsection;

public class SecondSquareItem {
    private String profilePictureUrl; // URL for the profile picture
    private String name;
    private double rating;
    private int hourlyRate;
    private String waitingJobs; // Availability status

    public SecondSquareItem(String profilePictureUrl, String name, double rating, int hourlyRate, String waitingJobs) {
        this.profilePictureUrl = profilePictureUrl;
        this.name = name;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
        this.waitingJobs = waitingJobs;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public String getWaitingJobs() {
        return waitingJobs;
    }
}
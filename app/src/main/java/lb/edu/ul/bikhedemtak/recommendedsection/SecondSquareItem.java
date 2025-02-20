package lb.edu.ul.bikhedemtak.recommendedsection;

public class SecondSquareItem {
    private String profilePictureUrl;
    private String name;
    private double rating;
    private int hourlyRate;
    private String waitingJobs;
    private int taskerId;

    public SecondSquareItem(String profilePictureUrl, String name, double rating, int hourlyRate, String waitingJobs, int taskerId) {
        this.profilePictureUrl = profilePictureUrl;
        this.name = name;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
        this.waitingJobs = waitingJobs;
        this.taskerId = taskerId;
    }

    // Getters
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

    public int getTaskerId() {
        return taskerId;
    }
}
package lb.edu.ul.bikhedemtak.search;

public class SearchResult {
    private String name;
    private String skill;
    private double hourlyRate;
    private String profilePicture;
    private double rating;
    private String description;
    private String waitingJobs; // Availability status

    public SearchResult(String name, String skill, double hourlyRate, String profilePicture, double rating, String description, String waitingJobs) {
        this.name = name;
        this.skill = skill;
        this.hourlyRate = hourlyRate;
        this.profilePicture = profilePicture;
        this.rating = rating;
        this.description = description;
        this.waitingJobs = waitingJobs;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getSkill() {
        return skill;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getWaitingJobs() {
        return waitingJobs;
    }
}
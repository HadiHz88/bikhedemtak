package lb.edu.ul.bikhedemtak.search;

public class SearchResult {
    private String name;
    private String skill;
    private double hourlyRate;
    private String profilePicture;
    private double rating;
    private String description;
    private String waitingJobs;
    private int taskerId;


    public SearchResult(String name, String skill, double hourlyRate, String profilePicture, double rating, String description, String waitingJobs,int taskerId) {
        this.name = name;
        this.skill = skill;
        this.hourlyRate = hourlyRate;
        this.profilePicture = profilePicture;
        this.rating = rating;
        this.description = description;
        this.waitingJobs = waitingJobs;
        this.taskerId = taskerId;
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
    public int getTaskerId() {return taskerId;}
}
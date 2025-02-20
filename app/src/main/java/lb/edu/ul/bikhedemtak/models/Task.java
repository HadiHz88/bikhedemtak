package lb.edu.ul.bikhedemtak.models;

public class Task {
    private String dateBooking, timeBooking, taskAssigned, taskerImg;
    private int id;
    private boolean isCompleted;

    public Task(int id, String dateBooking, String timeBooking, String taskerImg, boolean isCompleted, String taskAssigned) {
        this.id = id;
        this.dateBooking = dateBooking;
        this.timeBooking = timeBooking;
        this.taskAssigned = taskAssigned;
        this.taskerImg = taskerImg;
        this.isCompleted = isCompleted;
        this.taskAssigned = taskAssigned;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public String getTaskerImg() {
        return taskerImg;
    }

    public String getTaskAssigned() {
        return taskAssigned;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setTaskerImg(String taskerImg) {
        this.taskerImg = taskerImg;
    }

    public void setCompleted(boolean Completed) {
        this.isCompleted = Completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


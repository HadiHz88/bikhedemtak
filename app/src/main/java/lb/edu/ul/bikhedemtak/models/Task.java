package lb.edu.ul.bikhedemtak.models;

public class Task {
    private String dateBooking, timeBooking, taskAssigned;
    private int id, taskerImg;
    private boolean isBooked;

    public Task(int id, String dateBooking, String timeBooking, String taskAssigned, int taskerImg) {
        this.id = id;
        this.dateBooking = dateBooking;
        this.timeBooking = timeBooking;
        this.taskAssigned = taskAssigned;
        this.taskerImg = taskerImg;
        this.isBooked = false;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public int getTaskerImg() {
        return taskerImg;
    }

    public String getTaskAssigned() {
        return taskAssigned;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setTaskerImg(int taskerImg) {
        this.taskerImg = taskerImg;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


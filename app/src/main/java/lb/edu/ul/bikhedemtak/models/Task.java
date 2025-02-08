package lb.edu.ul.bikhedemtak.models;

import java.util.Date;

public class Task {
    private int id;
    private int requesterId;
    private int taskerId;
    private String description;
    private int categoryId;
    private Date date;

    public Task(){
        // empty constructor
    }

    public Task(int id, int requesterId, int taskerId, String description, int categoryId, Date date){
        this.id = id;
        this.requesterId = requesterId;
        this.taskerId = taskerId;
        this.description = description;
        this.categoryId = categoryId;
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTaskerId() {
        return taskerId;
    }

    public void setTaskerId(int taskerId) {
        this.taskerId = taskerId;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

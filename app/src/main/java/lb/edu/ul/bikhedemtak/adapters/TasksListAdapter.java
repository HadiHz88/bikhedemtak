package lb.edu.ul.bikhedemtak.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.models.Task;


public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>{

    private List<Task> taskList;

    public TasksListAdapter(List<Task> taskList){
        this.taskList = taskList;
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public ImageView taskerImg;
        public TextView dateBooking, timeBooking, taskAssigned;
        public Button bookButton;
        public TaskViewHolder(View taskView) {
            super(taskView);
            taskerImg = taskView.findViewById(R.id.taskerProfilePicture);
            dateBooking = taskView.findViewById(R.id.dateBookingTv);
            timeBooking = taskView.findViewById(R.id.timeBookingTv);
            taskAssigned = taskView.findViewById(R.id.taskAssignedTv);
            bookButton = taskView.findViewById(R.id.booking_btn);
        }
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);
        return new TaskViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = taskList.get(position);
        holder.dateBooking.setText(currentTask.getDateBooking());
        holder.timeBooking.setText(currentTask.getTimeBooking());
        holder.taskAssigned.setText(currentTask.getTaskAssigned());
        holder.taskerImg.setImageResource(currentTask.getTaskerImg());

        holder.bookButton.setOnClickListener(v -> {
            //TODO: Implement booking logic
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

}

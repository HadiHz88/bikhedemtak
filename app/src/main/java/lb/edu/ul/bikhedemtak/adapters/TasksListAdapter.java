package lb.edu.ul.bikhedemtak.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.TaskCompletedActivity;
import lb.edu.ul.bikhedemtak.activities.TaskInfoActivity;
import lb.edu.ul.bikhedemtak.activities.TaskerProfileActivity;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.models.Task;


public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>{

    private List<Task> taskList;

    public TasksListAdapter(List<Task> taskList){
        this.taskList = taskList;
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public ImageView taskerImg;
        public TextView dateBooking, timeBooking, taskAssigned;
        public Button seeMoreButton;
        public TaskViewHolder(View taskView) {
            super(taskView);
            taskerImg = taskView.findViewById(R.id.taskerProfilePicture);
            dateBooking = taskView.findViewById(R.id.dateBookingTv);
            timeBooking = taskView.findViewById(R.id.timeBookingTv);
            taskAssigned = taskView.findViewById(R.id.taskAssignedTv);
            seeMoreButton = taskView.findViewById(R.id.seeMoreButton);
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
        holder.taskerImg.setImageResource(R.drawable.default_pp);

        holder.seeMoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), currentTask.isCompleted() ? TaskCompletedActivity.class : TaskInfoActivity.class);
            int task_id = taskList.get(position).getId();

            intent.putExtra("task_id", task_id);
            v.getContext().startActivity(intent);
        });

        holder.taskerImg.setOnClickListener(v-> {
            int task_id = taskList.get(position).getId();
            String endpoint = "getTaskerIdByTaskId.php?task_id=" + task_id;
            ApiRequest.getInstance().makeGetObjectRequest(v.getContext(), endpoint, new ApiRequest.ResponseListener<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        if (response.getString("status").equals("success")) {
                            JSONObject tasker = response.getJSONObject("data");
                            int tasker_id = tasker.getInt("tasker_id");
                            Log.d("Tasker ID", String.valueOf(tasker_id));
                            Intent intent = new Intent(v.getContext(), TaskerProfileActivity.class);
                            intent.putExtra("tasker_id", tasker_id);
                            v.getContext().startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String error) {

                }
            });
        });
    }

    public void updateTasks(List<Task> newTasks) {
        this.taskList = newTasks;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

}

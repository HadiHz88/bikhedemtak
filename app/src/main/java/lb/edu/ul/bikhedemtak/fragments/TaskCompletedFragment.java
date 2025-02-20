package lb.edu.ul.bikhedemtak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.adapters.TasksListAdapter;
import lb.edu.ul.bikhedemtak.api.ApiRequest;
import lb.edu.ul.bikhedemtak.models.Task;

public class TaskCompletedFragment extends Fragment {
    private RecyclerView completedRecView;
    private TasksListAdapter completedAdapter;

    public TaskCompletedFragment(){
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_completed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        completedRecView = view.findViewById(R.id.taskCompletedRecView);

        String endpoint = "getCompletedTasks.php?user_id=" + 2;

        ApiRequest.getInstance().makeGetObjectRequest(getContext(), endpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        JSONArray completedTasks = response.getJSONArray("tasks");
                        List<Task> allCompletedTasks = new ArrayList<>();
                        for (int i = 0; i < completedTasks.length(); i++) {
                            JSONObject task = completedTasks.getJSONObject(i);
                            int id = task.getInt("task_id");
                            int tasker_id = task.getInt("tasker_id");
                            String taskerName = task.getString("tasker_name");
                            String dateTimeBooking = task.getString("booking_date");
                            String date = dateTimeBooking.split(" ")[0];
                            String time = dateTimeBooking.split(" ")[1];
                            String taskerProfilePic = task.optString("tasker_profile_pic", "");
                            String taskStatus = task.getString("task_status");

                            allCompletedTasks.add(new Task(id, date, time, taskerProfilePic, true, taskerName));
                        }

                        completedAdapter = new TasksListAdapter(allCompletedTasks);
                        completedRecView.setAdapter(completedAdapter);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }
}

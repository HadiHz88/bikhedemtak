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
import lb.edu.ul.bikhedemtak.utils.SharedPrefsManager;

public class TaskScheduledFragment extends Fragment {
    private RecyclerView scheduledRecView;
    private TasksListAdapter scheduledAdapter;

    public TaskScheduledFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_scheduled, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scheduledRecView = view.findViewById(R.id.taskScheduledRecView);

        int userId = SharedPrefsManager.getUserId(getContext());

        String endpoint = "getScheduledTasks.php?requester_id=" + userId;

        ApiRequest.getInstance().makeGetObjectRequest(getContext(), endpoint, new ApiRequest.ResponseListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        JSONArray scheduledTasks = response.getJSONArray("tasks");
                        List<Task> allScheduledTasks = new ArrayList<>();
                        for (int i = 0; i < scheduledTasks.length(); i++) {
                            JSONObject task = scheduledTasks.getJSONObject(i);
                            int id = task.getInt("task_id");
                            int tasker_id = task.getInt("tasker_id");
                            String taskerName = task.getString("tasker_name");
                            String dateTimeBooking = task.getString("booking_date");
                            String date = dateTimeBooking.split(" ")[0];
                            String time = dateTimeBooking.split(" ")[1];
                            String taskerProfilePic = task.optString("tasker_profile_pic", "");
                            String taskStatus = task.getString("task_status");

                            allScheduledTasks.add(new Task(id, date, time, taskerProfilePic, false, taskerName));
                        }
                        scheduledAdapter = new TasksListAdapter(allScheduledTasks);
                        scheduledRecView.setAdapter(scheduledAdapter);

                        if (allScheduledTasks.isEmpty()) {
                            view.findViewById(R.id.noTask).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.taskScheduledRecView).setVisibility(View.GONE);
                        }
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